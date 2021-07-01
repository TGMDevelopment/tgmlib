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

package xyz.matthewtgm.tgmlib.core.mod;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import xyz.matthewtgm.tgmlib.TGMLib;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class TGMLibLoadingPlugin implements IFMLLoadingPlugin {

    public String[] getASMTransformerClass() {
        SimulatedTGMLibInstaller.ReturnValue tgmLibReturned = SimulatedTGMLibInstaller.initialize();
        if (tgmLibReturned != SimulatedTGMLibInstaller.ReturnValue.SUCCESSFUL) System.out.println("Failed to load TGMLib.");
        else {
            System.out.println("Loaded TGMLib successfully.");
            return new String[]{TGMLib.TRANSFORMER};
        }
        return new String[0];
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {}

    public String getAccessTransformerClass() {
        return null;
    }

}