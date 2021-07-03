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

package xyz.matthewtgm.tgmlib.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.tgmlib.TGMLib;

public abstract class BaseCloakCosmetic extends BaseCosmetic {

    private final CapeModel model = new CapeModel();

    public BaseCloakCosmetic(String name, String id) {
        super(name, id, CosmeticType.CLOAK);
    }

    public abstract ResourceLocation texture();

    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale) {
        ResourceLocation texture = texture();
        if (texture != null && player.hasPlayerInfo() && !player.isInvisible() && player.isWearing(EnumPlayerModelParts.CAPE) && show(player)) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(1, 0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            double d0 = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double) partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double) partialTicks);
            double d1 = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double) partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double) partialTicks);
            double d2 = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double) partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double) partialTicks);
            float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
            double d3 = MathHelper.sin(f * 0.017453292F);
            double d4 = -MathHelper.cos(f * 0.017453292F);
            float f1 = (float) d1 * 10.0F;

            f1 = MathHelper.clamp_float(f1, -6.0F, 32.0F);
            float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
            float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;

            if (f2 < 0.0F) f2 = 0.0F;

            float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;

            f1 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
            if (player.isSneaking()) f1 += 25.0F;

            GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            model.setRotationAngles(limbSwing, limbSwingAmount, tickAge, netHeadYaw, netHeadPitch, scale, player);
            model.render(player, limbSwing, limbSwingAmount, tickAge, netHeadYaw, netHeadPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    private boolean show(AbstractClientPlayer player) {
        boolean override = TGMLib.getManager().getConfigHandler().isOverrideCapes();

        if (override) return true;
        if (player.getLocationCape() == null) return true;
        return false;
    }

    private static class CapeModel extends ModelBase {

        private final ModelRenderer cape = new ModelRenderer(this, 0, 0);

        public CapeModel() {
            this.cape.setTextureSize(64, 32);
            this.cape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1);
        }

        public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            this.cape.render(scale);
        }

        public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
            super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
            EntityPlayer livingEntity = (EntityPlayer) entityIn;

            if (livingEntity.getCurrentArmor(2) != null) {
                if (livingEntity.isSneaking()) {
                    this.cape.rotationPointZ = 0.8F;
                    this.cape.rotationPointY = 1.85F;
                } else {
                    this.cape.rotationPointZ = -1.1F;
                    this.cape.rotationPointY = 0.0F;
                }
            } else if (livingEntity.isSneaking()) {
                this.cape.rotationPointZ = 1.0F;
                this.cape.rotationPointY = 1.2F;
            } else {
                this.cape.rotationPointZ = 0.0F;
                this.cape.rotationPointY = 0.0F;
            }

        }

    }

}