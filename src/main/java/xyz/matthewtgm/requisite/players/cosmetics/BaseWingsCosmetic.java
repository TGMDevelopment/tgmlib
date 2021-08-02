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

package xyz.matthewtgm.requisite.players.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import xyz.matthewtgm.requisite.data.ColourRGB;

public abstract class BaseWingsCosmetic extends BaseCosmetic {

    private final WingModel model = new WingModel();
    private final Minecraft mc = Minecraft.getMinecraft();

    private float prevAnimTime;
    private float animTime;

    public BaseWingsCosmetic(String name, String id) {
        super(name, id, CosmeticType.WINGS);
    }

    public abstract ResourceLocation texture();
    public abstract ColourRGB colour();

    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale) {
        prevAnimTime = animTime;
        float f10 = 0.2f / (MathHelper.sqrt_double(player.motionX * player.motionX + player.motionZ * player.motionZ) * 10f + 1f);
        if (player.motionX == 0 && player.motionZ == 0 && player.onGround)
            f10 = f10 / 2.5f;
        if (!player.onGround)
            f10 = (f10 * 2) / 2.5f;
        animTime += f10;

        if (!player.isInvisible()) {
            GL11.glPushMatrix();
            GL11.glScalef(-0.75f, -0.75f, -0.75f);
            GL11.glTranslatef(0f, -0.175f, -0.2f);
            if (player.isSneaking())
                GlStateManager.translate(0f, -0.3f, -0.075f);
            GL11.glRotatef(180f, 1f, 0f, 0f);

            float rotate = prevAnimTime + (animTime - prevAnimTime) * partialTicks;

            ColourRGB colour = colour();
            GL11.glColor3f((float) colour.getR() / 255, (float) colour.getG() / 255, (float) colour.getB() / 255);
            mc.getTextureManager().bindTexture(texture());
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(1028);
            for (int j = 0; j < 2; j++) {
                model.wing.rotateAngleX = 0.125F - (float) Math.cos(rotate) * 0.2f;
                model.wing.rotateAngleY = 0.25f;
                model.wing.rotateAngleZ = (float) (Math.sin(rotate) + 0.125D) * 0.8f;
                model.wingTip.rotateAngleZ = -((float) (Math.sin(rotate + 2f) + 0.5D)) * 0.75f;
                model.wing.render(scale);
                GL11.glScalef(-1f, 1f, 1f);

                if (j == 0)
                    GL11.glCullFace(1029);
            }
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glColor3f(255f, 255f, 255f);
            GL11.glPopMatrix();
        }
    }

    private static class WingModel extends ModelBase {
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