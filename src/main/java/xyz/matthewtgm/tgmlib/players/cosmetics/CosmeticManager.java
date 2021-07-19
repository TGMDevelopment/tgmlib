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
import lombok.Setter;
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
import xyz.matthewtgm.tgmlib.players.PlayerData;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.cloaks.*;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.cloaks.contentcreators.TwitchCloakCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.cloaks.contentcreators.YouTubCloakCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.cloaks.exclusive.MatthewTgmCloakCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.cloaks.exclusive.WyvestCloakCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.cloaks.partners.DarkCheeseIglooCloakCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.wings.ChromaDragonWingsCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.wings.DragonWingsCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.impl.wings.TgmWingsCosmetic;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.tgmlib.util.PlayerRendererHelper;

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
        cosmetics.add(new BeehiveCloakCosmetic());
        cosmetics.add(new BoosterCloakCosmetic());
        cosmetics.add(new BugHunterCloakCosmetic());
        cosmetics.add(new DarkCheeseIglooCloakCosmetic());
        cosmetics.add(new DiscordCloakCosmetic());
        cosmetics.add(new DragonsEyeCloakCosmetic());
        cosmetics.add(new EnchanterCloakCosmetic());
        cosmetics.add(new DeveloperCloakCosmetic());
        cosmetics.add(new KeycapCloakCosmetic());
        cosmetics.add(new MatthewTgmCloakCosmetic());
        cosmetics.add(new MinecoinCloakCosmetic());
        cosmetics.add(new ModderCloakCosmetic());
        cosmetics.add(new PartnerCloakCosmetic());
        cosmetics.add(new SunsetSkyCloakCosmetic());
        cosmetics.add(new TwitchCloakCosmetic());
        cosmetics.add(new UwUCloakCosmetic());
        cosmetics.add(new VaporwaveCloakCosmetic());
        cosmetics.add(new WaterMeadowCloakCosmetic());
        cosmetics.add(new WinterCloak());
        cosmetics.add(new WyvestCloakCosmetic());
        cosmetics.add(new YouTubCloakCosmetic());

        cosmetics.add(new DragonWingsCosmetic());
        cosmetics.add(new ChromaDragonWingsCosmetic());
        cosmetics.add(new TgmWingsCosmetic());
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