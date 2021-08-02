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

package xyz.matthewtgm.requisite.tweaker.transformers;

import org.objectweb.asm.tree.ClassNode;
import xyz.matthewtgm.quickasm.QuickASM;
import xyz.matthewtgm.quickasm.interfaces.ITransformer;
import xyz.matthewtgm.requisite.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.requisite.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.requisite.tweaker.hooks.RequisiteGuiContainerAccessor;

import static org.objectweb.asm.Opcodes.*;

public class GuiContainerTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.GuiContainer.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        QuickASM.convertAccessor(classNode, RequisiteGuiContainerAccessor.class);
        QuickASM.createAccessorGetter(classNode, "getXSize", "()I", EnumTransformerFields.xSize.getField(EnumTransformerClasses.GuiContainer), IRETURN);
        QuickASM.createAccessorGetter(classNode, "getYSize", "()I", EnumTransformerFields.ySize.getField(EnumTransformerClasses.GuiContainer), IRETURN);
        QuickASM.createAccessorGetter(classNode, "getGuiTop", "()I", EnumTransformerFields.guiTop.getField(EnumTransformerClasses.GuiContainer), IRETURN);
        QuickASM.createAccessorGetter(classNode, "getGuiLeft", "()I", EnumTransformerFields.guiLeft.getField(EnumTransformerClasses.GuiContainer), IRETURN);
    }

}