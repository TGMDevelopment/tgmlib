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
import xyz.matthewtgm.quickasm.QuickASM;
import xyz.matthewtgm.quickasm.interfaces.ITransformer;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerClasses;
import xyz.matthewtgm.tgmlib.tweaker.enums.EnumTransformerFields;
import xyz.matthewtgm.tgmlib.tweaker.hooks.TGMLibPositionedSoundAccessor;

import static org.objectweb.asm.Opcodes.*;

public class PositionedSoundTransformer implements ITransformer {

    public String[] classes() {
        return new String[]{EnumTransformerClasses.PositionedSound.getTransformerName()};
    }

    public void transform(ClassNode classNode, String name) {
        QuickASM.convertAccessor(classNode, TGMLibPositionedSoundAccessor.class);
        QuickASM.createAccessorSetter(classNode, "setVolume", "(F)V", FLOAD, EnumTransformerFields.PositionedSound_volume.putField(EnumTransformerClasses.PositionedSound));
    }

}