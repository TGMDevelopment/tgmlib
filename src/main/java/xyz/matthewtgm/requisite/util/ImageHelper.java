/*
 * Requisite - Minecraft library mod
 * Copyright (C) 2021 MatthewTGM
 *
 * Requisite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Requisite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Requisite. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.requisite.util;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xyz.matthewtgm.json.entities.JsonArray;
import xyz.matthewtgm.json.util.JsonHelper;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ImageHelper {

    // TODO: 2021/07/16 : Javadoc!

    private ImageHelper() {}

    public static ByteArrayOutputStream toByteArrayOutputStream(RenderedImage image) throws IOException {
        ByteArrayOutputStream ret = new ByteArrayOutputStream();
        ret.write(toByteArray(image));
        return ret;
    }

    public static byte[] toByteArray(RenderedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Raster raster = image.getData();
        DataBufferByte bufferByte = (DataBufferByte) raster.getDataBuffer();
        baos.write(bufferByte.getData());
        return baos.toByteArray();
    }

    public static BufferedImage getImageFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return null;

        try {
            if (!filename.substring(filename.length() - 4).equalsIgnoreCase(".gif")) return ImageIO.read(file);

            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(ImageIO.createImageInputStream(file));

            int numImages = reader.getNumImages(true);

            IIOMetadata imageMetaData = reader.getImageMetadata(0);
            String metaFormatName = imageMetaData.getNativeMetadataFormatName();

            boolean foundBug = false;
            for (int i = 0; i < numImages && !foundBug; i++) {
                IIOMetadataNode root = (IIOMetadataNode)reader.getImageMetadata(i).getAsTree(metaFormatName);

                int nNodes = root.getLength();
                for (int j = 0; j < nNodes; j++) {
                    Node node = root.item(j);
                    if (node.getNodeName().equalsIgnoreCase("GraphicControlExtension")) {
                        String delay = ((IIOMetadataNode)node).getAttribute("delayTime");
                        if (Integer.parseInt(delay) == 0) {
                            foundBug = true;
                        }

                        break;
                    }
                }
            }

            BufferedImage image;
            if (!foundBug) {
                image = ImageIO.read(file);
            } else {
                ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
                try (ImageOutputStream ios = ImageIO.createImageOutputStream(baoStream)) {
                    ImageWriter writer = ImageIO.getImageWriter(reader);
                    writer.setOutput(ios);

                    writer.prepareWriteSequence(null);

                    for (int i = 0; i < numImages; i++) {
                        BufferedImage frameIn = reader.read(i);

                        IIOMetadataNode root = (IIOMetadataNode)reader.getImageMetadata(i).getAsTree(metaFormatName);

                        int nNodes = root.getLength();
                        for (int j = 0; j < nNodes; j++) {
                            Node node = root.item(j);
                            if (node.getNodeName().equalsIgnoreCase("GraphicControlExtension")) {
                                String delay = ((IIOMetadataNode)node).getAttribute("delayTime");

                                if (Integer.parseInt(delay) == 0) {
                                    ((IIOMetadataNode)node).setAttribute("delayTime", "10");
                                }

                                break;
                            }
                        }

                        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(frameIn), null);
                        metadata.setFromTree(metadata.getNativeMetadataFormatName(), root);

                        IIOImage frameOut = new IIOImage(frameIn, null, metadata);

                        writer.writeToSequence(frameOut, writer.getDefaultWriteParam());
                    }

                    writer.endWriteSequence();
                }
                image = ImageIO.read(new ByteArrayInputStream(baoStream.toByteArray()));
            }
            return image;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage[] getGifFrames(Object gif) {
        System.out.println(gif);
        BufferedImage[] ret;
        try {
            String[] imageatt = new String[] {"imageLeftPosition", "imageTopPosition", "imageWidth", "imageHeight"};

            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream ciis = ImageIO.createImageInputStream(gif);
            System.out.println(ciis);
            reader.setInput(ciis);
            System.out.println(reader.getInput());

            int noi;
            BufferedImage master;
            master = null;
            noi = reader.getNumImages(true);
            ret = new BufferedImage[noi];
            System.out.println("Frames: " + noi);

            for (int i = 0; i < noi; i++) {
                BufferedImage image = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node nodeItem = children.item(j);
                    if (nodeItem.getNodeName().equals("ImageDescriptor")) {
                        Map<String, Integer> imageAttr = new HashMap<>();
                        for (String s : imageatt) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(s);
                            imageAttr.put(s, Integer.valueOf(attnode.getNodeValue()));
                        }
                        if (i == 0) {
                            master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                            master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
                        }
                    }
                }
                ret[i] = image;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = new BufferedImage[256];
        }
        JsonArray returns = new JsonArray();
        for (BufferedImage image : ret) returns.add(image.toString());
        System.out.println(JsonHelper.makePretty(returns, 2));
        return ret;
    }

}