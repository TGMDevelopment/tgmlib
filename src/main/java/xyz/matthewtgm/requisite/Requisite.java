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
    @Getter private static RequisiteManager manager;
    @Getter private final Logger logger = LogManager.getLogger(NAME);
    @Getter @Mod.Instance(ID) private static Requisite instance;

    /**
     * Initialization method for Requisite, shouldn't be called in other mods.
     */
    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {
        if (manager == null)
            manager = new RequisiteManager();
        manager.initialize();
        start();
    }

    /**
     * Starts Requisite services, registers utilities and checks for library versions.
     */
    private void start() {
        /* Ensure that all currently used JsonTGM features are available at runtime. */
        if (!JsonVersion.CURRENT.isAtLeast(2, 8, 0))
            throw new IllegalStateException("JsonTGM is outdated! (minimum version is 2.8.0)");
        /* Ensure that all currently used TGMConfig features are available at runtime. */
        if (!ConfigVersion.CURRENT.isAtLeast(3, 2, 0))
            throw new IllegalStateException("TGMConfig is outdated! (minimum version is 3.2.0)");

        /* Allow other mods to detect Requisite, even if they don't use it. */
        Launch.blackboard.put("requisite", true);

        /* Register utilities that use Forge events, or Requisite features that need events. */
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

        /* Start the manager services, so that it can initialize things in the background. */
        manager.start();

        /* Modify options menu so Requisite can be easily accessed. */
        GuiEditor.addEdit(GuiOptions.class, new GuiEditor.GuiEditRunnable() {
            public void init(GuiScreen screen, List<GuiButton> buttonList) {
                buttonList.add(new GuiButton(IntegerHelper.getRandomNumber(234346, 342345671), screen.width / 2 - 50, screen.height - 24, 100, 20, "TGMLib") {
                    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                        if (super.mousePressed(mc, mouseX, mouseY))
                            mc.displayGuiScreen(new GuiRequisiteMain(screen));
                        return false;
                    }
                });
            }
            public void draw(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {}
        });

        /* Register the Requisite command. */
        CommandManager.register(RequisiteCommand.class);

        /* Register keybind, mostly so other mods know the custom keybinds framework exists. */
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

}