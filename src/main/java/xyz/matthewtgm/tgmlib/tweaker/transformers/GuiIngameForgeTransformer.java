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

package xyz.matthewtgm.tgmlib.tweaker.transformers;

import org.objectweb.asm.tree.*;
import xyz.matthewtgm.tgmlib.tweaker.TGMLibTransformer;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerMethods;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibGuiIngameForgeAccessor;
import xyz.matthewtgm.tgmlib.util.AsmHelper;

import static org.objectweb.asm.Opcodes.*;

public class GuiIngameForgeTransformer implements TGMLibTransformer {

    public String[] classes() {
        return new String[]{"net.minecraftforge.client.GuiIngameForge"};
    }

    public void transform(ClassNode classNode, String name) {
        convertAccessorOrInvoker(classNode, TGMLibGuiIngameForgeAccessor.class);
        createAccessorGetter(classNode, "getDebugOverlay", "()Lnet/minecraft/client/gui/GuiOverlayDebug;", new FieldInsnNode(GETFIELD, "net/minecraftforge/client/GuiIngameForge", "debugOverlay", "Lnet/minecraft/client/gui/GuiOverlayDebug;"), ARETURN);
        createAccessorSetter(classNode, "setDebugOverlay", "(Lnet/minecraft/client/gui/GuiOverlayDebug;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraftforge/client/GuiIngameForge", "debugOverlay", "Lnet/minecraft/client/gui/GuiOverlayDebug;"));

        for (MethodNode method : classNode.methods) {
            if (nameMatches(method.name, EnumTransformerMethods.renderBossHealth, "j")) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/tgmlib/events/BossBarEvent$RenderEvent"));
                    list.add(new InsnNode(DUP));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/tgmlib/events/BossBarEvent$RenderEvent", "<init>", "()V", false));
                    list.add(new VarInsnNode(ASTORE, 1));

                    list.add(new VarInsnNode(ALOAD, 1));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(RETURN));
                    list.add(labelNode);
                }));
            }
            if (method.name.equals("renderRecordOverlay")) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/tgmlib/events/ActionBarEvent$RenderEvent"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(EnumTransformerFields.recordPlaying.getField(EnumTransformerClasses.GuiIngameForge));
                    list.add(new VarInsnNode(ILOAD, 1));
                    list.add(new VarInsnNode(ILOAD, 2));
                    list.add(new VarInsnNode(FLOAD, 3));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/tgmlib/events/ActionBarEvent$RenderEvent", "<init>", "(Ljava/lang/String;IIF)V", false));
                    list.add(new VarInsnNode(ASTORE, 4));

                    list.add(new VarInsnNode(ALOAD, 4));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(RETURN));
                    list.add(labelNode);


                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 4));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/ActionBarEvent$RenderEvent", "text", "Ljava/lang/String;"));
                    list.add(EnumTransformerFields.recordPlaying.putField(EnumTransformerClasses.GuiIngameForge));
                }));
            }
            if (method.name.equals("renderTitle")) {
                method.instructions.insertBefore(method.instructions.getFirst(), AsmHelper.createQuickInsnList(list -> {
                    /*

                        Objective: Call `TitleEvent` and set the parameter values to its field values after calling.

                        TitleEvent titleEvent = new TitleEvent(this.displayedTitle, this.displayedSubTitle);
                        if (ForgeHelper.postEvent(titleEvent)) {
                            return;
                        }
                        this.displayedTitle = titleEvent.title;
                        this.displayedSubTitle = titleEvent.subTitle;

                    */

                    list.add(new TypeInsnNode(NEW, "xyz/matthewtgm/tgmlib/events/TitleEvent"));
                    list.add(new InsnNode(DUP));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(EnumTransformerFields.displayedTitle.getField(EnumTransformerClasses.GuiIngameForge));
                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(EnumTransformerFields.displayedSubTitle.getField(EnumTransformerClasses.GuiIngameForge));
                    list.add(new MethodInsnNode(INVOKESPECIAL, "xyz/matthewtgm/tgmlib/events/TitleEvent", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false));
                    list.add(new VarInsnNode(ASTORE, 4));

                    list.add(new VarInsnNode(ALOAD, 4));
                    list.add(new MethodInsnNode(INVOKESTATIC, "xyz/matthewtgm/tgmlib/util/ForgeHelper", "postEvent", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    list.add(new JumpInsnNode(IFEQ, labelNode));
                    list.add(new InsnNode(RETURN));
                    list.add(labelNode);


                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 4));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/TitleEvent", "title", "Ljava/lang/String;"));
                    list.add(EnumTransformerFields.displayedTitle.putField(EnumTransformerClasses.GuiIngameForge));

                    list.add(new VarInsnNode(ALOAD, 0));
                    list.add(new VarInsnNode(ALOAD, 4));
                    list.add(new FieldInsnNode(GETFIELD, "xyz/matthewtgm/tgmlib/events/TitleEvent", "subTitle", "Ljava/lang/String;"));
                    list.add(EnumTransformerFields.displayedSubTitle.putField(EnumTransformerClasses.GuiIngameForge));
                }));
            }
        }
    }

}