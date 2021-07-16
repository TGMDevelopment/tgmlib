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

package xyz.matthewtgm.tgmlib.util;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.events.TGMLibEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuiEditor {

    // TODO: 2021/07/16 : Javadoc! 

    @Getter
    private static final Map<Class<? extends GuiScreen>, List<GuiEditRunnable>> editMap = new ConcurrentHashMap<>();

    public static void addEdit(Class<? extends GuiScreen> screenClz, GuiEditRunnable edit) {
        if (ForgeHelper.postEvent(new TGMLibEvent.GuiEditEvent.EditAddEvent.Pre(TGMLib.getInstance(), screenClz, edit)))
            return;
        if (screenClz == null)
            return;
        if (edit == null)
            return;
        editMap.putIfAbsent(screenClz, new ArrayList<>());
        editMap.get(screenClz).add(edit);
        ForgeHelper.postEvent(new TGMLibEvent.GuiEditEvent.EditAddEvent.Post(TGMLib.getInstance(), screenClz, edit));
    }

    public static void addEdits(Class<? extends GuiScreen> screenClz, GuiEditRunnable... edits) {
        if (screenClz == null)
            return;
        if (edits == null)
            return;
        for (GuiEditRunnable runnable : edits)
            addEdit(screenClz, runnable);
    }

    public static void removeEdit(Class<? extends GuiScreen> screenClz, GuiEditRunnable edit) {
        if (ForgeHelper.postEvent(new TGMLibEvent.GuiEditEvent.EditRemoveEvent.Pre(TGMLib.getInstance(), screenClz, edit)))
            return;
        if (screenClz == null)
            return;
        if (edit == null)
            return;
        if (editMap.containsKey(screenClz)) {
            List<GuiEditRunnable> edits = editMap.get(screenClz);
            if (edits != null && !edits.isEmpty())
                edits.remove(edit);
        }
        ForgeHelper.postEvent(new TGMLibEvent.GuiEditEvent.EditRemoveEvent.Post(TGMLib.getInstance(), screenClz, edit));
    }

    public static void removeEdits(Class<? extends GuiScreen> screenClz, GuiEditRunnable... edits) {
        if (screenClz == null)
            return;
        if (edits == null)
            return;
        for (GuiEditRunnable runnable : edits)
            removeEdit(screenClz, runnable);
    }

    @SubscribeEvent
    protected void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
        if (edits != null && !edits.isEmpty())
            for (GuiEditRunnable runnable : edits)
                runnable.init(event.gui, event.buttonList);
    }

    @SubscribeEvent
    protected void onGuiActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
        if (edits != null && !edits.isEmpty())
            for (GuiEditRunnable runnable : edits)
                runnable.actionPerformed(event.gui, event.buttonList, event.button);
    }

    @SubscribeEvent
    protected void onGuiDrawn(GuiScreenEvent.DrawScreenEvent.Post event) {
        List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
        if (edits != null && !edits.isEmpty())
            for (GuiEditRunnable runnable : edits)
                runnable.draw(event.gui, event.mouseX, event.mouseY, event.renderPartialTicks);
    }

    @SubscribeEvent
    protected void onGuiInit(GuiScreenEvent.KeyboardInputEvent.Post event) {
        List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
        if (edits != null && !edits.isEmpty())
            for (GuiEditRunnable runnable : edits)
                runnable.keyTyped(event.gui, Keyboard.getEventCharacter(), Keyboard.getEventKey());
    }

    @SubscribeEvent
    protected void onGuiInit(GuiScreenEvent.MouseInputEvent.Post event) {
        List<GuiEditRunnable> edits = editMap.get(event.gui.getClass());
        if (edits != null && !edits.isEmpty())
            for (GuiEditRunnable runnable : edits)
                runnable.mouseClicked(event.gui, Mouse.getEventButton(), MouseHelper.getMouseX(), MouseHelper.getMouseY(), Mouse.getEventDWheel());
    }

    public interface GuiEditRunnable {
        Minecraft mc = Minecraft.getMinecraft();
        void init(GuiScreen screen, List<GuiButton> buttonList);
        default void actionPerformed(GuiScreen screen, List<GuiButton> buttonList, GuiButton clicked) {}
        void draw(GuiScreen screen, int mouseX, int mouseY, float partialTicks);
        default void keyTyped(GuiScreen screen, char typedChar, int keyCode) {}
        default void mouseClicked(GuiScreen screen, int button, int mouseX, int mouseY, int wheel) {}
    }

}