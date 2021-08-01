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

package xyz.matthewtgm.tgmlib.players.cosmetics;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.data.ColourRGB;
import xyz.matthewtgm.tgmlib.data.GifResourceLocation;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.DragonWingsCosmetic;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.tgmlib.util.ColourHelper;
import xyz.matthewtgm.tgmlib.util.PlayerRendererHelper;
import xyz.matthewtgm.tgmlib.util.ResourceHelper;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CosmeticManager extends Thread {

    @Getter
    private static boolean loaded;
    @Getter
    private final List<BaseCosmetic> cosmetics = new ArrayList<>();
    @Getter
    private static final List<String> madeRequestsFor = new ArrayList<>();
    private TGMLibSocket tgmLibSocket;
    private final Logger logger = LogManager.getLogger(TGMLib.NAME + " (" + getClass().getSimpleName() + ")");

    public synchronized void start() {
        tgmLibSocket = TGMLib.getManager().getWebSocket();
        initialize();
        MinecraftForge.EVENT_BUS.register(this);
        for (BaseCosmetic cosmetic : cosmetics)
            PlayerRendererHelper.addLayer(new TGMLibCosmeticLayer(cosmetic));

        get(Minecraft.getMinecraft().getSession().getProfile().getId().toString());
        loaded = true;
    }

    private synchronized void initialize() {
        logger.info("Initializing cosmetics...");
        cosmetics.add(new CloakCosmetic("Beehive Cloak", "BEEHIVE_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/beehive_cloak.png")));
        cosmetics.add(new CloakCosmetic("Booster Cloak", "BOOSTER_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/booster_cloak.png")));
        cosmetics.add(new CloakCosmetic("Bug Hunter Cloak", "BUG_HUNTER_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/bug_hunter_cloak.png")));
        cosmetics.add(new AnimatedCloakCosmetic("DarkCheese's Igloo Cloak", "DARK_CHEESE_IGLOO_CLOAK", 3, new GifResourceLocation(ResourceHelper.get("tgmlib", "cosmetics/cloaks/partners/darkcheese_igloo_cloak.gif"))));
        cosmetics.add(new CloakCosmetic("Developer Cloak", "DEVELOPER_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/developer_cloak.png")));
        cosmetics.add(new AnimatedCloakCosmetic("Discord Cloak", "DISCORD_CLOAK", 1, new GifResourceLocation(ResourceHelper.get("tgmlib", "cosmetics/cloaks/discord_cloak.gif"))));
        cosmetics.add(new CloakCosmetic("Dragon's Eye Cloak", "DRAGON_EYE_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/dragons_eye_cloak.png")));
        cosmetics.add(new AnimatedCloakCosmetic("Enchanter Cloak", "ENCHANTER_CLOAK", 1, new GifResourceLocation(ResourceHelper.get("tgmlib", "cosmetics/cloaks/enchanter_cloak.gif"))));
        cosmetics.add(new CloakCosmetic("Keycap Cloak", "KEYCAP_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/keycap_cloak.png")));
        cosmetics.add(new CloakCosmetic("MatthewTGM Cloak", "MATTHEWTGM_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/exclusive/matthewtgm_cloak.png")));
        cosmetics.add(new CloakCosmetic("Minecoin Cloak", "MINECOIN_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/minecoin_cloak.png")));
        cosmetics.add(new CloakCosmetic("Modder Cloak", "MODDER_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/modder_cloak.png")));
        cosmetics.add(new CloakCosmetic("Partner Cloak", "PARTNER_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/partner_cloak.png")));
        cosmetics.add(new CloakCosmetic("Strebbypatty Cloak", "STREB_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/exclusive/streb_cloak.png")));
        cosmetics.add(new CloakCosmetic("Sunset Sky Cloak", "SUNSET_SKY_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/sunset_sky_cloak.png")));
        cosmetics.add(new CloakCosmetic("Twitch Cloak", "STANDARD_TWITCH_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/content_creators/twitch_cloak.png")));
        cosmetics.add(new CloakCosmetic("UwU Cloak", "UWU_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/uwu_cloak.png")));
        cosmetics.add(new AnimatedCloakCosmetic("Vaporwave Cloak", "VAPORWAVE_CLOAK", 1, new GifResourceLocation(ResourceHelper.get("tgmlib", "cosmetics/cloaks/vaporwave_cloak.gif"))));
        cosmetics.add(new CloakCosmetic("Watery Meadow Cloak", "WATER_MEADOW_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/water_meadow_cloak.png")));
        cosmetics.add(new CloakCosmetic("Winter Cloak", "WINTER_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/winter_cloak.png")));
        cosmetics.add(new CloakCosmetic("Wyvest Cloak", "WYVEST_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/exclusive/wyvest_cloak.png")));
        cosmetics.add(new CloakCosmetic("YouTube Cloak", "STANDARD_YOUTUBE_CLOAK", ResourceHelper.get("tgmlib", "cosmetics/cloaks/content_creators/youtube_cloak.png")));

        cosmetics.add(new DragonWingsCosmetic("Dragon Wings", "DRAGON_WINGS", new ColourRGB(255, 255, 255), ResourceHelper.get("tgmlib", "cosmetics/wings/dragon_wings.png")));
        cosmetics.add(new DragonWingsCosmetic("Chroma Dragon Wings", "CHROMA_DRAGON_WINGS", new ColourRGB(255, 255, 255), ResourceHelper.get("tgmlib", "cosmetics/wings/dragon_wings.png")) {
            public void tick() {
                this.colour = new ColourRGB(ColourHelper.timeBasedChroma()).setA_builder(255);
            }
        });
        cosmetics.add(new DragonWingsCosmetic("TGM Wings", "TGM_WINGS", new ColourRGB(255, 255, 255), ResourceHelper.get("tgmlib", "cosmetics/wings/tgm_wings.png")));
        logger.info("Cosmetics initialized.");
    }

    public BaseCosmetic getCosmeticFromId(String id) {
        AtomicReference<BaseCosmetic> gotten = new AtomicReference<>(null);
        cosmetics.stream().filter(cosmetic -> cosmetic.getId().equalsIgnoreCase(id)).findFirst().ifPresent(gotten::set);
        return gotten.get();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityJoinedWorld(EntityJoinWorldEvent event) {
        if (madeRequestsFor.size() > 200)
            madeRequestsFor.clear();

        if (event.entity instanceof EntityPlayer && !TGMLib.getManager().getDataManager().getDataMap().containsKey(event.entity.getUniqueID().toString()) && !madeRequestsFor.contains(event.entity.getUniqueID().toString()))
            get(event.entity.getUniqueID().toString());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for (BaseCosmetic cosmetic : cosmetics)
            cosmetic.tick();
    }

    public void get(String uuid) {
        if (madeRequestsFor.contains(uuid))
            return;
        tgmLibSocket.send(new CosmeticsRetrievePacket(uuid));
        madeRequestsFor.add(uuid);
    }

}