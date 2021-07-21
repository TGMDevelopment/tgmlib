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

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import xyz.matthewtgm.tgmlib.tweaker.hooks.EntityPlayerAccessor;
import xyz.matthewtgm.tgmlib.util.global.GlobalMinecraft;

public class PlayerHelper {

    public static EntityPlayerSP getPlayer() {
        return GlobalMinecraft.getPlayer();
    }

    public static InventoryPlayer getInventory() {
        return GlobalMinecraft.getPlayer().inventory;
    }

    public static InventoryEnderChest fetchEnderChest() {
        return null;
    }

    public static Container getInventoryContainer() {
        return GlobalMinecraft.getPlayer().inventoryContainer;
    }

    public static Container getOpenContainer() {
        return GlobalMinecraft.getPlayer().openContainer;
    }

    public static FoodStats fetchFoodStats() {
        return GlobalMinecraft.getPlayer().getFoodStats();
    }

    public static BlockPos getPlayerLocation() {
        return GlobalMinecraft.getPlayer().playerLocation;
    }

    public static PlayerCapabilities getCapabilities() {
        return GlobalMinecraft.getPlayer().capabilities;
    }

    public static int getExperienceLevel() {
        return GlobalMinecraft.getPlayer().experienceLevel;
    }

    public static ItemStack getItemInUse() {
        return GlobalMinecraft.getPlayer().getItemInUse();
    }

    public static EntityFishHook getFishingHook() {
        return GlobalMinecraft.getPlayer().fishEntity;
    }

    public static boolean isBlocking() {
        return GlobalMinecraft.getPlayer().isBlocking();
    }

    public static void playSound(String name, float volume, float pitch) {
        GlobalMinecraft.getPlayer().playSound(name, volume, pitch);
    }

    public static EntityItem dropItem(boolean all) {
        return GlobalMinecraft.getPlayer().dropOneItem(all);
    }

    public static boolean canHarvestBlock(Block block) {
        return GlobalMinecraft.getPlayer().canHarvestBlock(block);
    }

    public static boolean isInBed() {
        return ((EntityPlayerAccessor) GlobalMinecraft.getPlayer()).isIsInBed();
    }

    public static boolean isSleeping() {
        return GlobalMinecraft.getPlayer().isPlayerSleeping();
    }

    public static BlockPos getBedLocation() {
        return GlobalMinecraft.getPlayer().getBedLocation();
    }

    public static ItemStack getArmour(int slot) {
        return GlobalMinecraft.getPlayer().getCurrentArmor(slot);
    }

    public static ItemStack getHeld() {
        return GlobalMinecraft.getPlayer().getHeldItem();
    }

    public static boolean isSpectator() {
        return GlobalMinecraft.getPlayer().isSpectator();
    }

    public static IChatComponent getDisplayName() {
        return GlobalMinecraft.getPlayer().getDisplayName();
    }

    public static String getFormattedDisplayName() {
        return getDisplayName().getFormattedText();
    }

    public static String getUnformattedDisplayName() {
        return getDisplayName().getUnformattedText();
    }

    public static float getDefaultEyeHeight() {
        return GlobalMinecraft.getPlayer().getDefaultEyeHeight();
    }

    public static float getTrueEyeHeight() {
        return GlobalMinecraft.getPlayer().eyeHeight;
    }

    public static float getEyeHeight() {
        return GlobalMinecraft.getPlayer().getEyeHeight();
    }

    public static void setEyeHeight(float eyeHeight) {
        GlobalMinecraft.getPlayer().eyeHeight = eyeHeight;
    }



}