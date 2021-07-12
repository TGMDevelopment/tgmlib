/*
 * Copyright (C) MatthewTGM
 * This file is part of TGMLib <https://github.com/TGMDevelopment/TGMLib>.
 *
 * TGMLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TGMLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TGMLib. If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.tgmlib.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import xyz.matthewtgm.tgmlib.TGMLib;

import javax.imageio.ImageIO;

public class ResourceHelper {

    public static ResourceLocation get(String domain, String path) {
        return new ResourceLocation(domain, path);
    }

    public static ResourceLocation get(String path) {
        return new ResourceLocation(path);
    }

    public static InputStream toInputStream(ResourceLocation rl) {
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(rl).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void download(String url, ResourceLocationCallback callback) {
        if (url != null && !url.isEmpty()) {
            String baseName = FilenameUtils.getBaseName(url);
            ResourceLocation resourceLocation = new ResourceLocation("temp/" + baseName);
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject textureObject = textureManager.getTexture(resourceLocation);

            if (textureObject instanceof EnhancedThreadDownloadImageData) {
                EnhancedThreadDownloadImageData threadDownloadImageData = (EnhancedThreadDownloadImageData) textureObject;
                if (threadDownloadImageData.bufferedImage != null && threadDownloadImageData.textureUploaded) {
                    callback.load(resourceLocation);
                    return;
                }
            }

            IImageBuffer buffer = new IImageBuffer() {
                public BufferedImage parseUserSkin(BufferedImage image) {
                    return image;
                }
                public void skinAvailable() {
                    callback.load(resourceLocation);
                }
            };
            EnhancedThreadDownloadImageData threadDownloadImageData = new EnhancedThreadDownloadImageData(null, url, null, buffer);
            threadDownloadImageData.textureUploaded = true;
            textureManager.loadTexture(resourceLocation, threadDownloadImageData);
        }
    }

    public interface ResourceLocationCallback {
        void load(ResourceLocation resourceLocation);
    }

    public static class EnhancedThreadDownloadImageData extends SimpleTexture {

        private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
        private final File cacheFile;
        private final String imageUrl;
        private final IImageBuffer imageBuffer;
        private BufferedImage bufferedImage;
        private Thread imageThread;
        private boolean textureUploaded;

        public EnhancedThreadDownloadImageData(File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, IImageBuffer imageBufferIn) {
            super(textureResourceLocation);
            this.cacheFile = cacheFileIn;
            this.imageUrl = imageUrlIn;
            this.imageBuffer = imageBufferIn;
        }

        private void checkTextureUploaded() {
            if (!textureUploaded && bufferedImage != null) {
                if (textureLocation != null)
                    deleteGlTexture();
                TextureUtil.uploadTextureImage(super.getGlTextureId(), bufferedImage);
                this.textureUploaded = true;
            }
        }

        public int getGlTextureId() {
            checkTextureUploaded();
            return super.getGlTextureId();
        }

        public void setBufferedImage(BufferedImage bufferedImageIn) {
            this.bufferedImage = bufferedImageIn;
            if (imageBuffer != null)
                imageBuffer.skinAvailable();
        }

        public void loadTexture(IResourceManager resourceManager) throws IOException {
            if (bufferedImage == null && textureLocation != null)
                super.loadTexture(resourceManager);
            if (imageThread == null) {
                if (cacheFile != null && cacheFile.isFile()) {
                    try {
                        bufferedImage = ImageIO.read(cacheFile);
                        if (imageBuffer != null)
                            setBufferedImage(imageBuffer.parseUserSkin(bufferedImage));
                    }
                    catch (IOException ioexception) {
                        loadTextureFromServer();
                    }
                } else
                    loadTextureFromServer();
            }
        }

        protected void loadTextureFromServer() {
            (this.imageThread = new Thread("Texture Downloader #" + threadDownloadCounter.incrementAndGet()) {
                @Override
                public void run() {
                    HttpURLConnection httpurlconnection = null;
                    try {
                        httpurlconnection = (HttpURLConnection) new URL(imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
                        httpurlconnection.setDoInput(true);
                        httpurlconnection.setDoOutput(false);
                        httpurlconnection.setUseCaches(true);
                        httpurlconnection.addRequestProperty("User-Agent", "Mozilla/4.76 (" + TGMLib.NAME + "/" + TGMLib.VER + ")");
                        if (httpurlconnection.getResponseCode() / 100 == 2) {
                            BufferedImage bufferedimage;
                            if (cacheFile != null) {
                                FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), cacheFile);
                                bufferedimage = ImageIO.read(cacheFile);
                            } else
                                bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
                            if (imageBuffer != null) {
                                bufferedimage = imageBuffer.parseUserSkin(bufferedimage);
                            }
                            setBufferedImage(bufferedimage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (httpurlconnection != null)
                            httpurlconnection.disconnect();
                    }
                }
            }).setDaemon(true);
            imageThread.start();
        }
    }

}