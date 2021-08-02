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

package xyz.matthewtgm.requisite.apis.draggablehud;

import lombok.Getter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import xyz.matthewtgm.requisite.data.ScreenPosition;
import xyz.matthewtgm.requisite.util.DevelopmentHelper;

import java.io.IOException;
import java.util.Optional;

public abstract class GuiDraggableHud extends GuiScreen {

    @Getter private final GuiScreen parent;
    @Getter private final IDraggableHud draggableHud;
    private boolean dragging;
    private Optional<IDraggable> selected = Optional.empty();
    private int prevX, prevY;

    public GuiDraggableHud(GuiScreen parent, IDraggableHud draggableHud) {
        this.parent = parent;
        this.draggableHud = draggableHud;
        DevelopmentHelper.markUnderHeavyDevelopment(this);
    }

    public GuiDraggableHud(IDraggableHud draggableHud) {
        this(null, draggableHud);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground();
        updatePositions(mouseX, mouseY);
        for (IDraggable draggable : draggableHud.draggables) {
            GlStateManager.color(1f, 1f, 1f, 1f);
            drawDraggable(draggable, partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        prevX = mouseX;
        prevY = mouseY;
        selected = draggableHud.draggables.stream().filter(draggable -> (mouseX >= draggable.position().getX() && mouseX <= draggable.position().getX() + draggable.size().width) && (mouseY >= draggable.position().getY() && mouseY <= draggable.position().getY() + draggable.size().height)).findFirst();
        System.out.println(selected.isPresent());
        if (selected.isPresent())
            dragging = true;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void updatePositions(int mouseX, int mouseY) {
        if (selected.isPresent() && dragging) {
            IDraggable current = selected.get();
            ScreenPosition position = current.position();
            position.setPosition(position.getX() + mouseX - prevX, position.getY() + mouseY - prevY);
        }
        prevX = mouseX;
        prevY = mouseY;
    }

    public abstract void drawBackground();
    public abstract void drawDraggable(IDraggable draggable, float partialTicks);
    public abstract void onGuiClosed();
    public abstract boolean doesGuiPauseGame();

}