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

package xyz.matthewtgm.lib.cosmetics;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.matthewtgm.lib.TGMLib;
import xyz.matthewtgm.lib.cosmetics.impl.cloaks.DeveloperCloakCosmetic;
import xyz.matthewtgm.lib.cosmetics.impl.cloaks.MinecoinCloakCosmetic;
import xyz.matthewtgm.lib.cosmetics.impl.cloaks.PartnerCloakCosmetic;
import xyz.matthewtgm.lib.cosmetics.impl.cloaks.exclusive.JohnnyJthCloakCosmetic;
import xyz.matthewtgm.lib.cosmetics.impl.wings.ChromaDragonWingsCosmetic;
import xyz.matthewtgm.lib.cosmetics.impl.wings.DragonWingsCosmetic;
import xyz.matthewtgm.lib.socket.packets.impl.cosmetics.CosmeticsRetrievePacket;
import xyz.matthewtgm.lib.util.PlayerRendererHelper;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CosmeticManager {

    @Getter
    private final List<BaseCosmetic> cosmetics = new ArrayList<>();
    @Getter
    private final Map<String, PlayerCosmeticsHolder> cosmeticMap = new HashMap<>();
    private final Map<String, BaseCosmetic> registeredLayers = new HashMap<>();
    private final Logger logger = LogManager.getLogger(TGMLib.NAME + " (" + getClass().getSimpleName() + ")");

    public void start() {
        initCosmetics();
        MinecraftForge.EVENT_BUS.register(this);
        for (BaseCosmetic cosmetic : cosmetics) PlayerRendererHelper.addLayer(createLayer(cosmetic));

        TGMLib.getInstance().getWebSocket().send(new CosmeticsRetrievePacket(Minecraft.getMinecraft().getSession().getProfile().getId().toString()));
    }

    private void initCosmetics() {
        logger.info("Initializing cosmetics...");
        cosmetics.add(new DeveloperCloakCosmetic());
        cosmetics.add(new JohnnyJthCloakCosmetic());
        cosmetics.add(new MinecoinCloakCosmetic());
        cosmetics.add(new PartnerCloakCosmetic());

        cosmetics.add(new DragonWingsCosmetic());
        cosmetics.add(new ChromaDragonWingsCosmetic());
        logger.info("Cosmetics initialized.");
    }

    public BaseCosmetic getCosmeticFromId(String id) {
        AtomicReference<BaseCosmetic> gotten = new AtomicReference<>(null);
        cosmetics.stream().filter(cosmetic -> cosmetic.getId().equalsIgnoreCase(id)).findFirst().ifPresent(gotten::set);
        return gotten.get();
    }

    private LayerRenderer<AbstractClientPlayer> createLayer(BaseCosmetic cosmetic) {
        return new LayerRenderer<AbstractClientPlayer>() {
            public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale) {
                if (!cosmeticMap.containsKey(player.getUniqueID().toString())) TGMLib.getInstance().getWebSocket().send(new CosmeticsRetrievePacket(player.getUniqueID().toString()));
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
    public void onPlayerRendered(RenderPlayerEvent.Pre event) {
        if (registeredLayers.size() != cosmetics.size()) {
            for (BaseCosmetic cosmetic : cosmetics) {
                if (!registeredLayers.containsKey(cosmetic.getId())) {
                    event.renderer.addLayer(createLayer(cosmetic));
                    registeredLayers.putIfAbsent(cosmetic.getId(), cosmetic);
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for (BaseCosmetic cosmetic : cosmetics) cosmetic.tick();
    }

}