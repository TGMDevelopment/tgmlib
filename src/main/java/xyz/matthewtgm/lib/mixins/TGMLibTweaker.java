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

package xyz.matthewtgm.lib.mixins;

import net.minecraft.client.main.Main;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TGMLibTweaker implements ITweaker {

    private final Logger logger = LogManager.getLogger("TGMLibTweaker");

    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}

    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        logger.info("Initializing TGMLib mixins...");
        MixinBootstrap.init();
        MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
        Mixins.addConfiguration("mixins.tgmlib.json");
        if (environment.getObfuscationContext() == null) environment.setObfuscationContext("searge");
        environment.setSide(MixinEnvironment.Side.CLIENT);
        logger.info("TGMLib mixins initialized!");
    }

    public String getLaunchTarget() {
        return Main.class.getName();
    }

    public String[] getLaunchArguments() {
        return new String[0];
    }

}