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

package xyz.matthewtgm.requisite.util.global;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import xyz.matthewtgm.requisite.mixins.game.MinecraftAccessor;

import java.io.File;

public class GlobalMinecraft {

    private static final Minecraft instance = Minecraft.getMinecraft();

    public static float getPartialTicks() {
        return ((MinecraftAccessor) instance).getTimer().renderPartialTicks;
    }

    public static Timer getTimer() {
        return ((MinecraftAccessor) instance).getTimer();
    }

    public static FrameTimer getFrameTimer() {
        return instance.frameTimer;
    }

    public static int getDisplayWidth() {
        return instance.displayWidth;
    }

    public static int getDisplayHeight() {
        return instance.displayHeight;
    }

    public static GuiScreen getCurrentScreen() {
        return instance.currentScreen;
    }

    public static ServerData getCurrentServerData() {
        return instance.getCurrentServerData();
    }

    public static NetHandlerPlayClient getNetHandler() {
        return instance.getNetHandler();
    }

    public static SoundHandler getSoundHandler() {
        return instance.getSoundHandler();
    }

    public static String getVersion() {
        return instance.getVersion();
    }

    public static ItemRenderer getItemRenderer() {
        return instance.getItemRenderer();
    }

    public static Session getSession() {
        return instance.getSession();
    }

    public static RenderGlobal getRenderGlobal() {
        return instance.renderGlobal;
    }

    public static boolean isSingleplayer() {
        return getWorld() != null && instance.isSingleplayer();
    }

    public static File getGameDirectory() {
        return instance.mcDataDir;
    }

    public static PlayerControllerMP getPlayerController() {
        return instance.playerController;
    }

    public static GameSettings getGameSettings() {
        return instance.gameSettings;
    }

    public static GuiIngame getHud() {
        return instance.ingameGUI;
    }

    public static EffectRenderer getEffectRenderer() {
        return instance.effectRenderer;
    }

    public static TextureManager getTextureManager() {
        return instance.getTextureManager();
    }

    public static EntityRenderer getEntityRenderer() {
        return instance.entityRenderer;
    }

    public static GuiScreen getGuiScreen() {
        return instance.currentScreen;
    }

    public static EntityPlayerSP getPlayer() {
        return instance.thePlayer;
    }

    public static FontRenderer getFontRenderer() {
        return instance.fontRendererObj;
    }

    public static WorldClient getWorld() {
        return instance.theWorld;
    }

    public static void displayGuiScreen(GuiScreen screen) {
        instance.displayGuiScreen(screen);
    }

    public static Minecraft getInstance() {
        return instance;
    }

}