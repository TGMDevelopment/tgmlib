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

package xyz.matthewtgm.tgmlib.data;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GifResourceLocation {

    @Getter
    private final Object gif;
    private final int fps;
    private final ResourceLocation[] textures;
    private int frames;
    private int currentTick = 0;
    private int currentFrame = 0;
    @Getter
    private int width;
    @Getter
    private int height;

    public GifResourceLocation(Object gif, int fpt) {
        this.gif = gif;
        this.fps = fpt;
        ResourceLocation[] newTextures;
        try {
            String[] imageatt = new String[] {"imageLeftPosition", "imageTopPosition", "imageWidth", "imageHeight"};

            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream ciis = ImageIO.createImageInputStream(gif);
            reader.setInput(ciis);

            int noi = reader.getNumImages(true);
            newTextures = new ResourceLocation[noi];
            this.frames = noi;
            BufferedImage master = null;

            for (int i = 0; i < noi; i++) {
                BufferedImage image = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node nodeItem = children.item(j);
                    if (nodeItem.getNodeName().equals("ImageDescriptor")) {
                        Map<String, Integer> imageAttr = new HashMap<>();
                        for (int k = 0; k < imageatt.length; k++) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(imageatt[k]);
                            imageAttr.put(imageatt[k], Integer.valueOf(attnode.getNodeValue()));
                        }
                        if (i == 0) {
                            master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        }
                        master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
                    }
                }
                newTextures[i] = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation(frames + ".gif", new DynamicTexture(master));
            }

        } catch (Exception e) {
            e.printStackTrace();
            newTextures = new ResourceLocation[256];
        }
        textures = newTextures;
    }

    public GifResourceLocation(Object gif) {
        this(gif, 1);
    }

    public ResourceLocation getTexture() {
        return textures[currentFrame];
    }

    public void update() {
        if (currentTick > fps) {
            currentTick = 0;
            currentFrame++;
            if (currentFrame > frames - 1) {
                currentFrame = 0;
            }
        }
        currentTick++;
    }

}