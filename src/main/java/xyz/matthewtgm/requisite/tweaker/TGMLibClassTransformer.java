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

package xyz.matthewtgm.requisite.tweaker;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.quickasm.QuickClassTransformer;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.tweaker.transformers.*;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(1001)
public class TGMLibClassTransformer extends QuickClassTransformer {

    private static boolean created;
    private static final Logger logger = LogManager.getLogger(Requisite.NAME + " (TGMLibClassTransformer)");

    public TGMLibClassTransformer() {
        super(Boolean.parseBoolean(System.getProperty("debugBytecode", "false")), logger);
        if (created) {
            logger.warn("TGMLibClassTransformer was already created, returning.");
            return;
        }
        created = true;

        addTransformer(new AbstractClientPlayerTransformer());
        addTransformer(new BossStatusTransformer());
        addTransformer(new EntityLivingBaseTransformer());
        addTransformer(new EntityPlayerSPTransformer());
        addTransformer(new EntityPlayerTransformer());
        addTransformer(new FontRendererTransformer());
        addTransformer(new GuiContainerTransformer());
        addTransformer(new GuiIngameForgeTransformer());
        addTransformer(new GuiIngameTransformer());
        addTransformer(new GuiNewChatTransformer());
        addTransformer(new GuiScreenTransformer());
        addTransformer(new MinecraftTransformer());
        addTransformer(new NBTTagCompoundTransformer());
        addTransformer(new NetHandlerPlayClientTransformer());
        addTransformer(new NetworkManagerTransformer());
        addTransformer(new PositionedSoundTransformer());
        addTransformer(new RenderTransformer());
    }

    static {
        TGMLibTransformationChecks.check();
    }

}