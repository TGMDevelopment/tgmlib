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

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import xyz.matthewtgm.quickasm.QuickASM;
import xyz.matthewtgm.quickasm.interfaces.ITransformer;
import xyz.matthewtgm.quickasm.types.VarInsnNodeInformation;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibGuiScreenAccessor;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibGuiScreenInvoker;

import java.util.Arrays;
import java.util.Collections;

import static org.objectweb.asm.Opcodes.*;

public class GuiScreenTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.GuiScreen.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        QuickASM.convertAccessor(classNode, TGMLibGuiScreenAccessor.class);
        QuickASM.createAccessorGetter(classNode, "getButtonList", "()Ljava/util/List;", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiScreen", "buttonList", "Ljava/util/List;"), ARETURN);
        QuickASM.createAccessorSetter(classNode, "setButtonList", "(Ljava/util/List;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiScreen", "buttonList", "Ljava/util/List;"));
        QuickASM.createAccessorGetter(classNode, "getLabelList", "()Ljava/util/List;", new FieldInsnNode(GETFIELD, "net/minecraft/client/gui/GuiScreen", "labelList", "Ljava/util/List;"), ARETURN);
        QuickASM.createAccessorSetter(classNode, "setLabelList", "(Ljava/util/List;)V", ALOAD, new FieldInsnNode(PUTFIELD, "net/minecraft/client/gui/GuiScreen", "labelList", "Ljava/util/List;"));

        QuickASM.convertInvoker(classNode, TGMLibGuiScreenInvoker.class);
        QuickASM.createInvokerMethod(classNode, "invokeMouseClicked", "(III)V", Arrays.asList(
                new VarInsnNodeInformation(ILOAD, 1),
                new VarInsnNodeInformation(ILOAD, 2),
                new VarInsnNodeInformation(ILOAD, 3)
        ), new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/client/gui/GuiScreen", "mouseClicked", "(III)V", false), RETURN);
        QuickASM.createInvokerMethod(classNode, "invokeMouseReleased", "(III)V", Arrays.asList(
                new VarInsnNodeInformation(ILOAD, 1),
                new VarInsnNodeInformation(ILOAD, 2),
                new VarInsnNodeInformation(ILOAD, 3)
        ), new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/client/gui/GuiScreen", "mouseReleased", "(III)V", false), RETURN);
        QuickASM.createInvokerMethod(classNode, "invokeKeyTyped", "(Ljava/lang/Character;I)V", Arrays.asList(
                new VarInsnNodeInformation(ALOAD, 1),
                new VarInsnNodeInformation(ILOAD, 2)
        ), new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/client/gui/GuiScreen", "keyTyped", "(Ljava/lang/Character;I)V", false), RETURN);
        QuickASM.createInvokerMethod(classNode, "invokeMouseClickMove", "(IIIJ)V", Arrays.asList(
                new VarInsnNodeInformation(ILOAD, 1),
                new VarInsnNodeInformation(ILOAD, 2),
                new VarInsnNodeInformation(ILOAD, 3),
                new VarInsnNodeInformation(LLOAD, 4)
        ), new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/client/gui/GuiScreen", "mouseClickMove", "(IIIJ)V", false), RETURN);
        QuickASM.createInvokerMethod(classNode, "invokeActionPerformed", "(Lnet/minecraft/client/gui/GuiButton;)V", Collections.singletonList(
                new VarInsnNodeInformation(ALOAD, 1)
        ), new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/client/gui/GuiScreen", "actionPerformed", "(Lnet/minecraft/client/gui/GuiButton;)V", false), RETURN);
    }

}