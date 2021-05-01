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

package ga.matthewtgm;

import ga.matthewtgm.lib.TGMLib;
import ga.matthewtgm.lib.util.Notifications;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = "TestMod", modid = "testmod", version = "1.0")
public class TestMod {

    @Mod.EventHandler
    protected void onPreInit(FMLPreInitializationEvent event) {
        TGMLib.getInstance().onForgePreInit();
    }

    @Mod.EventHandler
    protected void onPostInit(FMLPostInitializationEvent event) {
        Notifications.push("Test Notification", "Test Notification Description");
    }

}