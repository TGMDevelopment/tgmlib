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
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.matthewtgm.tgmlib.data.ColourRGB;

public abstract class BaseWingsCosmetic extends BaseCosmetic {

    private final WingModel model = new WingModel();

    public BaseWingsCosmetic(String name, String id) {
        super(name, id, CosmeticType.WINGS);
    }

    public abstract ResourceLocation texture();

    public abstract ColourRGB colour();

    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale) {
        if (!player.isInvisible()) {
            GL11.glPushMatrix();
            GL11.glScaled(-0.75f, -0.75f, 0.75f);
            GL11.glTranslated(0.0D, -1.45D, 0.0D);
            GL11.glTranslated(0.0D, 1.3D, 0.2D);
            if (player.isSneaking())
                GlStateManager.translate(0f, -0.275f, 0.075f);
            GL11.glRotated(180, 1, 0, 0);
            GL11.glRotated(180, 0, 1, 0);

            ColourRGB colour = colour();
            GL11.glColor3f((float) colour.getR() / 255, (float) colour.getG() / 255, (float) colour.getB() / 255);
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture());
            for (int j = 0; j < 2; j++) {
                GL11.glEnable(GL11.GL_CULL_FACE);
                float f11 = (float) (System.currentTimeMillis() % 1000L) / 1000f * 3.1415927f * 2f;
                if (player.isSwingInProgress || !player.onGround) f11 *= 2f;
                model.wing.rotateAngleX = (float) Math.toRadians(-80.0D) - (float) Math.cos(f11) * 0.2f;
                model.wing.rotateAngleY = (float) Math.toRadians(20.0D) + (float) Math.sin(f11) * 0.4f;
                model.wing.rotateAngleZ = (float) Math.toRadians(20.0D);
                model.wingTip.rotateAngleZ = -((float) (Math.sin((f11 + 2.0f)) + 0.5D)) * 0.75f;
                model.wing.render(0.0625f);
                GL11.glScalef(-1f, 1f, 1f);
                if (j == 0)
                    GL11.glCullFace(1028);
            }
            GL11.glCullFace(1029);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glColor3f(255f, 255f, 255f);
            GL11.glPopMatrix();
        }
    }

    private class WingModel extends ModelBase {

        private final ModelRenderer wing;
        private final ModelRenderer wingTip;

        public WingModel() {
            setTextureOffset("wing.bone", 0, 0);
            setTextureOffset("wing.skin", -10, 8);
            setTextureOffset("wingtip.bone", 0, 5);
            setTextureOffset("wingtip.skin", -10, 18);
            wing = new ModelRenderer(this, "wing");
            wing.setTextureSize(30, 30);
            wing.setRotationPoint(-2.0F, 0.0F, 0.0F);
            wing.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
            wing.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
            wingTip = new ModelRenderer(this, "wingtip");
            wingTip.setTextureSize(30, 30);
            wingTip.setRotationPoint(-10.0F, 0.0F, 0.0F);
            wingTip.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
            wingTip.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
            wing.addChild(wingTip);
        }
    }

}