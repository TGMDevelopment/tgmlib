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
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerMethods;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibEntityPlayerAccessor;

import static org.objectweb.asm.Opcodes.*;

public class EntityPlayerTransformer implements TGMLibTransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.EntityPlayer.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        convertAccessorOrInvoker(classNode, TGMLibEntityPlayerAccessor.class);
        createAccessorGetter(classNode, "isIsInBed", "()Z", new MethodInsnNode(INVOKEVIRTUAL, EnumTransformerClasses.EntityPlayer.getNameRaw(), EnumTransformerMethods.isInBed.getName(), "()Z", false), IRETURN);
    }

}