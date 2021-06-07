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

package xyz.matthewtgm.lib.startup;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.matthewtgm.lib.commands.CommandManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.lib.util.RenderHelper;

import java.awt.*;

public class StartupRegistry {

    /**
     * SHOULD ONLY BE USED IN TGMLib CLASSES.
     */
    public void init(Logger logger) {
        logger.info("Registering commands...");
        CommandManager.register(TGMLibCommand.class);
        logger.info("Commands registered!");
    }

    @SubscribeEvent
    protected void onWorldRendered(RenderLivingEvent.Post event) {
        if (TGMLibCommand.drawThreeDimText && !(event.entity instanceof EntityPlayer)) {
            RenderHelper.drawThreeDimensionalText(event.entity.getDisplayName().getFormattedText(), event.x, event.y + 3, event.z, true, true, new Color(255, 255, 255));
            RenderHelper.drawThreeDimensionalText(round(event.entity.posX) + " / " + round(event.entity.posY) + " / " + round(event.entity.posZ), event.x, event.y + 2, event.z, false, true, new Color(255, 255, 255));
        }
    }

    private String round(double integer) {
        return String.valueOf(Math.round(integer));
    }

}