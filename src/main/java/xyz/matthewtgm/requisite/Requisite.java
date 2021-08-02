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

package xyz.matthewtgm.requisite;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import xyz.matthewtgm.json.JsonVersion;
import xyz.matthewtgm.requisite.gui.menus.GuiRequisiteMain;
import xyz.matthewtgm.tgmconfig.ConfigVersion;
import xyz.matthewtgm.requisite.commands.CommandManager;
import xyz.matthewtgm.requisite.core.RequisiteManager;
import xyz.matthewtgm.requisite.gui.menus.GuiRequisiteLogging;
import xyz.matthewtgm.requisite.keybinds.KeyBind;
import xyz.matthewtgm.requisite.keybinds.KeyBindManager;
import xyz.matthewtgm.requisite.util.*;
import xyz.matthewtgm.requisite.util.global.GlobalMinecraft;

import java.util.List;

@Mod(
        name = Requisite.NAME,
        modid = Requisite.ID,
        version = Requisite.VER,
        clientSideOnly = true,
        acceptedMinecraftVersions = "[1.8.9]"
)
public final class Requisite {

    public static final String NAME = "@NAME@", ID = "@ID@", VER = "@VER@";
    @Getter private static final RequisiteManager manager = new RequisiteManager();
    @Getter private final Logger logger = LogManager.getLogger(NAME);
    @Getter @Mod.Instance(ID) private static Requisite instance;

    /**
     * Initialization method for Requisite, shouldn't be called in other mods.
     */
    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {
        manager.initialize();
        start();
    }

    /**
     * Starts Requisite services, registers utilities and checks for library versions.
     */
    private void start() {
        if (!JsonVersion.CURRENT.isAtLeast(2, 8, 0))
            throw new IllegalStateException("JsonTGM is outdated! (minimum version is 2.8.0)");
        if (!ConfigVersion.CURRENT.isAtLeast(3, 2, 0))
            throw new IllegalStateException("TGMConfig is outdated! (minimum version is 3.2.0)");

        /* Allow other mods to detect Requisite, even if they don't use it. */
        Launch.blackboard.put("requisite", true);

        ForgeHelper.registerEventListeners(
                this,
                new CommandQueue(),
                new GuiHelper(),
                new GuiEditor(),
                new HypixelHelper(),
                new MessageQueue(),
                new Notifications(),
                new PlayerHelper(),
                new ScreenHelper(),
                new RequisiteImprovedEventsListener()
        );
        manager.start();
        GuiEditor.addEdit(GuiOptions.class, new GuiEditor.GuiEditRunnable() {
            public void init(GuiScreen screen, List<GuiButton> buttonList) {
                buttonList.add(new GuiButton(IntegerHelper.getRandomNumber(2346, 345671), screen.width / 2 - 50, screen.height - 24, 100, 20, "TGMLib") {
                    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                        if (super.mousePressed(mc, mouseX, mouseY))
                            mc.displayGuiScreen(new GuiRequisiteMain(screen));
                        return false;
                    }
                });
            }
            public void draw(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {}
        });
        CommandManager.register(RequisiteCommand.class);
        manager.getKeyBindManager().register(new KeyBind(Keyboard.KEY_H) {
            public String name() {
                return "Requisite";
            }
            public String category() {
                return "Requisite";
            }
            public void pressed() {
                GuiHelper.open(new GuiRequisiteMain());
            }
            public void held() {}
            public void released() {}
        });
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiOpen(GuiScreenEvent.InitGuiEvent event) {
        if (event.gui instanceof GuiMainMenu && !manager.getDataHandler().isReceivedPrompt())
            GlobalMinecraft.displayGuiScreen(new GuiRequisiteLogging(event.gui));
    }

}