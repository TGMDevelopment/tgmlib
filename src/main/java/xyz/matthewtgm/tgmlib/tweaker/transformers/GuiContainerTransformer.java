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
import xyz.matthewtgm.tgmlib.tweaker.TGMLibTransformer;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibGuiContainerAccessor;

import static org.objectweb.asm.Opcodes.*;

public class GuiContainerTransformer implements TGMLibTransformer {

    public String[] getClassNames() {
        return new String[]{EnumTransformerClasses.GuiContainer.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        convertAccessor(classNode, TGMLibGuiContainerAccessor.class);
        createAccessorGetter(classNode, "getXSize", "()I", EnumTransformerFields.xSize.getField(EnumTransformerClasses.GuiContainer), IRETURN);
        createAccessorGetter(classNode, "getYSize", "()I", EnumTransformerFields.ySize.getField(EnumTransformerClasses.GuiContainer), IRETURN);
        createAccessorGetter(classNode, "getGuiTop", "()I;", EnumTransformerFields.guiTop.getField(EnumTransformerClasses.GuiContainer), IRETURN);
        createAccessorGetter(classNode, "getGuiLeft", "()I;", EnumTransformerFields.guiLeft.getField(EnumTransformerClasses.GuiContainer), IRETURN);
    }

}