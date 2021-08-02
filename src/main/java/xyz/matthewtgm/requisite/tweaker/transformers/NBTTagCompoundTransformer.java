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
import xyz.matthewtgm.requisite.tweaker.hooks.RequisiteNBTTagCompoundAccessor;

import static org.objectweb.asm.Opcodes.*;

public class NBTTagCompoundTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.NBTTagCompound.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        QuickASM.convertAccessor(classNode, RequisiteNBTTagCompoundAccessor.class);
        QuickASM.createAccessorGetter(classNode, "getTagMap", "()Ljava/util/Map;", EnumTransformerFields.tagMap.getField(EnumTransformerClasses.NBTTagCompound), ARETURN);
    }

}