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

package xyz.matthewtgm.requisite.players.indicators;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import xyz.matthewtgm.json.entities.JsonArray;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.networking.packets.impl.indication.RetrieveIndicationsPacket;
import xyz.matthewtgm.requisite.util.*;
import xyz.matthewtgm.requisite.util.global.GlobalMinecraft;

import javax.imageio.ImageIO;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class IndicatorManager extends Thread {

    @Getter @Setter private int width = 12, height = 12;

    @Getter private final JsonArray indicatorArray = new JsonArray();
    @Getter private ResourceLocation indicatorLocation;

    private final Logger logger = LogManager.getLogger(Requisite.NAME + " (IndicatorManager)");

    public synchronized void start() {
        logger.info("Initializing indicators.");
        ForgeHelper.registerEventListener(this);
        updateIndicator();
        fetch();
        Multithreading.schedule(this::fetch, 45, TimeUnit.SECONDS);
        logger.info("Indicators initialized.");
    }

    public synchronized void fetch() {
        Requisite.getManager().getWebSocket().send(new RetrieveIndicationsPacket());
    }

    public synchronized void updateIndicator() {
        try {
            indicatorLocation = GlobalMinecraft.getInstance().getTextureManager().getDynamicTextureLocation("indicator.png", new DynamicTexture(ImageIO.read(URI.create("https://raw.githubusercontent.com/TGMDevelopment/TGMLib-Data/main/indicator.png").toURL())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(Entity entity, String name, double x, double y, double z, int max) {
        if (Requisite.getManager().getConfigHandler().isShowIndicators() && renderable(entity)) {
            EntityPlayer player = (EntityPlayer) entity;
            if (indicatorArray.has(player.getUniqueID().toString()))
                render(name, (float) x, (float) y, (float) z, max, player);
        }
    }

    private boolean renderable(Entity entity) {
        return entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP);
    }

    private void render(String str, float x, float y, float z, int maxDistance, EntityPlayer player) {
        double distance = player.getDistanceSqToEntity(GlobalMinecraft.getPlayer());
        if (distance <= (maxDistance * maxDistance)) {
            if (!str.equals(player.getDisplayName().getFormattedText()))
                return;

            RenderManager renderManager = GlobalMinecraft.getInstance().getRenderManager();
            float f = 1.6f;
            float f1 = 0.016666668f * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.0F, y + player.height + 0.5f, z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-renderManager.playerViewY, 0f, 1f, 0f);
            GlStateManager.rotate(renderManager.playerViewX, 1f, 0f, 0f);
            GlStateManager.scale(-f1, -f1, 1);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

            double indicatorX = EnhancedFontRenderer.getWidth(player.getDisplayName().getFormattedText()) / 2 + 2;
            double indicatorY = y / 2 - height / 4;

            GlStateManager.color(0.5f, 0.5f, 0.5f, 0.5f);
            renderIndicator(indicatorX, indicatorY);

            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);

            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.color(1f, 1f, 1f);
            renderIndicator(indicatorX, indicatorY);

            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    private void renderIndicator(double x, double y) {
        RenderHelper.bindTexture(indicatorLocation);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        Gui.drawScaledCustomSizeModalRect((int) Math.round(x), (int) Math.round(y), 0, 0, 64, 64, width, height, 64, 64);
    }

}