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

package xyz.matthewtgm.tgmlib.cosmetics;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.cosmetics.impl.cloaks.*;
import xyz.matthewtgm.tgmlib.cosmetics.impl.cloaks.exclusive.MatthewTgmCloakCosmetic;
import xyz.matthewtgm.tgmlib.cosmetics.impl.cloaks.exclusive.WyvestCloakCosmetic;
import xyz.matthewtgm.tgmlib.cosmetics.impl.cloaks.partners.DarkCheeseIglooCloakCosmetic;
import xyz.matthewtgm.tgmlib.cosmetics.impl.wings.ChromaDragonWingsCosmetic;
import xyz.matthewtgm.tgmlib.cosmetics.impl.wings.DragonWingsCosmetic;
import xyz.matthewtgm.tgmlib.cosmetics.impl.wings.TgmWingsCosmetic;
import xyz.matthewtgm.tgmlib.socket.TGMLibSocket;
import xyz.matthewtgm.tgmlib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.tgmlib.util.PlayerRendererHelper;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CosmeticManager {

    @Getter
    private static boolean loaded;
    @Getter
    private final List<BaseCosmetic> cosmetics = new ArrayList<>();
    @Getter
    private final Map<String, PlayerCosmeticsHolder> cosmeticMap = new HashMap<>();
    @Getter
    private static final List<String> madeRequestsFor = new ArrayList<>();
    private final Logger logger = LogManager.getLogger(TGMLib.NAME + " (" + getClass().getSimpleName() + ")");

    public void start() {
        initialize();
        MinecraftForge.EVENT_BUS.register(this);
        for (BaseCosmetic cosmetic : cosmetics) PlayerRendererHelper.addLayer(createLayer(cosmetic));

        TGMLib.getManager().getWebSocket().send(new CosmeticsRetrievePacket(Minecraft.getMinecraft().getSession().getProfile().getId().toString()));
        loaded = true;
    }

    private void initialize() {
        logger.info("Initializing cosmetics...");
        cosmetics.add(new BoosterCloakCosmetic());
        cosmetics.add(new BugHunterCloakCosmetic());
        cosmetics.add(new DarkCheeseIglooCloakCosmetic());
        cosmetics.add(new DeveloperCloakCosmetic());
        cosmetics.add(new KeycapCloakCosmetic());
        cosmetics.add(new MatthewTgmCloakCosmetic());
        cosmetics.add(new MinecoinCloakCosmetic());
        cosmetics.add(new ModderCloakCosmetic());
        cosmetics.add(new PartnerCloakCosmetic());
        cosmetics.add(new UwUCloakCosmetic());
        cosmetics.add(new WyvestCloakCosmetic());

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

    private LayerRenderer<AbstractClientPlayer> createLayer(BaseCosmetic cosmetic) {
        TGMLibSocket socket = TGMLib.getManager().getWebSocket();
        return new LayerRenderer<AbstractClientPlayer>() {
            public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale) {
                if (!TGMLib.getManager().getConfigHandler().isShowCosmetics()) return;
                if (!socket.isOpen() && socket.isClosed() || !socket.isOpen() && socket.isClosing()) socket.reconnect();
                if (!cosmeticMap.containsKey(player.getUniqueID().toString()) && !madeRequestsFor.contains(player.getUniqueID().toString())) {
                    socket.send(new CosmeticsRetrievePacket(player.getUniqueID().toString()));
                    madeRequestsFor.add(player.getUniqueID().toString());
                }
                if (cosmeticMap.containsKey(player.getUniqueID().toString())) {
                    List<BaseCosmetic> cosmetics = cosmeticMap.get(player.getUniqueID().toString()).getEnabledCosmetics();
                    if (cosmetics.contains(cosmetic)) cosmetic.render(player, limbSwing, limbSwingAmount, partialTicks, tickAge, netHeadYaw, netHeadPitch, scale);
                }
            }
            public boolean shouldCombineTextures() {
                return false;
            }
        };
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for (BaseCosmetic cosmetic : cosmetics) cosmetic.tick();
    }

}