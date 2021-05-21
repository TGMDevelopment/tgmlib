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

package ga.matthewtgm.lib.gui.betterguis.elements.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import ga.matthewtgm.lib.gui.betterguis.elements.ElementBase;
import ga.matthewtgm.lib.gui.betterguis.elements.other.ElementText;
import ga.matthewtgm.lib.other.ColourRGB;
import ga.matthewtgm.lib.util.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;

public class TextFieldElement extends ElementBase {

    private final Predicate<String> contentValidator = Predicates.alwaysTrue();

    @Getter @Setter private ElementText placeholderText;
    @Getter @Setter private TextFieldStyle style;
    @Getter @Setter private boolean focused;
    @Getter @Setter private long maxContentLength = 256;
    @Getter @Setter private String content;

    @Getter private long cursorPosition;
    @Getter private long scrollOffset;
    @Getter private long selectionEnd;

    public TextFieldElement(int id, int x, int y, int width, int height, TextFieldStyle style, ElementText placeholderText) {
        super(id, x, y, width, height);
        this.style = style;
        this.placeholderText = placeholderText;
        this.focused = false;
        this.content = "";
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (true)
            return;
        RenderHelper.drawHollowRect(getX() - 1, getY() - 1, getWidth() + 1, getHeight() + 1, focused ? style.getFocusedOutlineColour().getRGBA() : style.getOutlineColour().getRGBA());
        RenderHelper.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), focused ? style.getSelectedBackgroundColour().getRGBA() : style.getBackgroundColour().getRGBA());
        int pos = (int) (cursorPosition - scrollOffset);
        int posToEnd = (int) (selectionEnd - scrollOffset);
        String trimmedToWidth = mc.fontRendererObj.trimStringToWidth(content.substring((int) scrollOffset), getWidth());
        System.out.println(pos);
        System.out.println(trimmedToWidth.length());
        boolean flag = pos >= 0 && pos <= trimmedToWidth.length();
        boolean flag1 = cursorPosition < content.length() || content.length() >= maxContentLength;
        if (posToEnd > trimmedToWidth.length())
            posToEnd = trimmedToWidth.length();
        if (trimmedToWidth.length() > 0) {
            String s1 = flag ? trimmedToWidth.substring(0, pos) : trimmedToWidth;
            mc.fontRendererObj.drawStringWithShadow(s1, (float)getX() + 2, (float)getY() + (getHeight() - 8) / 2, -1);
        }
        if (trimmedToWidth.length() > 0 && flag) {
            System.out.println(trimmedToWidth.substring(pos));
            EnhancedFontRenderer.drawText(trimmedToWidth.substring(pos), getX() + 2, getY() + (getHeight() - 8) / 2, -1);
        }
        if (flag1)
            Gui.drawRect(getX() + 2, getY() + (getHeight() - 8) / 2 - 1, getX() + 2 + 1, getY() + (getHeight() - 8) / 2 + 1 + mc.fontRendererObj.FONT_HEIGHT, -3092272);
        if (focused && posToEnd != pos) {
            int l1 = getX() + 4 + mc.fontRendererObj.getStringWidth(trimmedToWidth.substring(0, posToEnd));
            this.drawCursorVertical(getX() + 2, getY() + (getHeight() - 8) / 2 - 1, l1 - 1, getY() + (getHeight() - 8) / 2 + 1 + mc.fontRendererObj.FONT_HEIGHT);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (true)
            return;
        if (!focused)
            return;
        if (KeyboardHelper.isKeyComboCtrlA()) {
            setCursorPositionEnd();
            setSelectionPos(0);
        } else if (KeyboardHelper.isKeyComboCtrlC()) {
            ComputerHelper.setClipboardString(getSelectedContent());
        } else if (KeyboardHelper.isKeyComboCtrlV()) {
            write(ComputerHelper.getClipboardString());
        } else {
            switch (keyCode) {
                case 14:
                    if (KeyboardHelper.isCtrlKeyDown())
                        delete(-1);
                    else
                        deleteFromCursor(-1);
                    break;
                case 199:
                    if (KeyboardHelper.isShiftKeyDown())
                        setSelectionPos(0);
                    else
                        setCursorPosition(0);
                    break;
                case 203:
                    if (KeyboardHelper.isShiftKeyDown()) {
                        if (KeyboardHelper.isCtrlKeyDown())
                            setSelectionPos(getWordFromPos(-1, (int) getSelectionEnd()));
                        else
                            setSelectionPos(getSelectionEnd() - 1);
                    } else if (KeyboardHelper.isCtrlKeyDown())
                        setCursorPosition(getWordFromCursor(-1));
                    else
                        moveCursor(-1);
                    break;
                case 205:
                    if (KeyboardHelper.isShiftKeyDown()) {
                        if (KeyboardHelper.isCtrlKeyDown()) {
                            setSelectionPos(getWordFromPos(1, (int) getSelectionEnd()));
                        } else {
                            setSelectionPos(getSelectionEnd() + 1);
                        }
                    } else if (KeyboardHelper.isCtrlKeyDown()) {
                        setCursorPosition(getWordFromCursor(1));
                    } else {
                        moveCursor(1);
                    }
                case 207:
                    if (KeyboardHelper.isShiftKeyDown())
                        setSelectionPos(content.length());
                    else
                        setCursorPositionEnd();
                case 211:
                    if (KeyboardHelper.isCtrlKeyDown())
                        delete(1);
                    else
                        deleteFromCursor(1);
                default:
                    System.out.println(typedChar);
                    if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
                        write(Character.toString(typedChar));
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (true)
            return;
        focused = isInBounds(mouseX, mouseY);
    }

    public void write(String input) {
        if (true)
            return;
        String temp = "";
        String filtered = ChatAllowedCharacters.filterAllowedCharacters(input);
        int i = (int) (cursorPosition < selectionEnd ? cursorPosition : selectionEnd);
        int j = (int) (cursorPosition < selectionEnd ? selectionEnd : cursorPosition);
        int k = (int) (maxContentLength - content.length() - (i - j));
        int l;
        if (content.length() > 0)
            temp = temp + content.substring(0, i);
        if (k < filtered.length()) {
            temp = temp + filtered.substring(0, k);
            l = k;
        } else {
            temp = temp + filtered;
            l = filtered.length();
        }

        if (content.length() > 0 && j < content.length())
            temp = temp + content.substring(j);

        if (contentValidator.apply(temp)) {
            content = temp;
            moveCursor((int) (i - selectionEnd + l));
        }
    }

    public void delete(int length) {
        if (true)
            return;
        if (content.length() != 0) {
            if (selectionEnd != cursorPosition)
                write("");
            else
                deleteFromCursor((int) (getWordFromCursor(length) - cursorPosition));
        }
    }

    public void deleteFromCursor(int length) {
        if (true)
            return;
        if (content.length() != 0) {
            if (selectionEnd != cursorPosition)
                write("");
            else {
                boolean flag = length < 0;
                int i = (int) (flag ? this.cursorPosition + length : this.cursorPosition);
                int j = (int) (flag ? this.cursorPosition : this.cursorPosition + length);
                String s = "";

                if (i >= 0)
                    s = content.substring(0, i);

                if (j < content.length())
                    s = s + content.substring(j);

                if (contentValidator.apply(s)) {
                    content = s;

                    if (flag)
                        this.moveCursor(length);
                }
            }
        }
    }

    public int getWordFromPos(int position, int position1) {
        if (true)
            return 0;
        return getWord(position, position1, true);
    }

    public int getWordFromCursor(int position) {
        if (true)
            return 0;
        return getWordFromPos(position, (int) getCursorPosition());
    }

    /**
     * Taken from vanilla game code.
     */
    public int getWord(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
        if (true)
            return 0;
        int i = p_146197_2_;
        boolean flag = p_146197_1_ < 0;
        int j = Math.abs(p_146197_1_);

        for (int k = 0; k < j; ++k) {
            if (!flag) {
                int l = content.length();
                i = content.indexOf(32, i);

                if (i == -1) {
                    i = l;
                } else {
                    while (p_146197_3_ && i < l && content.charAt(i) == 32) {
                        ++i;
                    }
                }
            }
            else {
                while (p_146197_3_ && i > 0 && content.charAt(i - 1) == 32) {
                    --i;
                }

                while (i > 0 && content.charAt(i - 1) != 32) {
                    --i;
                }
            }
        }
        return i;
    }

    public String getSelectedContent() {
        if (true)
            return "";
        long i = cursorPosition < selectionEnd ? cursorPosition : selectionEnd;
        long j = cursorPosition < selectionEnd ? selectionEnd : cursorPosition;
        return content.substring((int) i, (int) j);
    }

    public void moveCursor(int length) {
        if (true)
            return;
        setCursorPosition(getSelectionEnd() + length);
    }

    public void setCursorPositionEnd() {
        if (true)
            return;
        setCursorPosition(content.length());
    }

    public void setCursorPosition(long position) {
        if (true)
            return;
        cursorPosition = position;
        int length = content.length();
        cursorPosition = MathHelper.clamp_long(cursorPosition, 0, length);
        setSelectionPos(cursorPosition);
    }

    public void setSelectionPos(long position) {
        if (true)
            return;
        int contentLength = content.length();
        if (position > contentLength)
            position = contentLength;
        if (position < 0)
            position = 0;
        selectionEnd = position;
        if (scrollOffset > contentLength)
            scrollOffset = contentLength;
        int width = getWidth();
        if (position == scrollOffset)
            scrollOffset -= mc.fontRendererObj.trimStringToWidth(content, width, true).length();
        if (position > width)
            scrollOffset += position - width;
        else if (position <= scrollOffset)
            scrollOffset -= scrollOffset - position;

        scrollOffset = MathHelper.clamp_long(scrollOffset, 0, contentLength);
    }

    /**
     * Taken from vanilla game code.
     */
    private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
        if (true)
            return;
        if (p_146188_1_ < p_146188_3_)
        {
            int i = p_146188_1_;
            p_146188_1_ = p_146188_3_;
            p_146188_3_ = i;
        }

        if (p_146188_2_ < p_146188_4_)
        {
            int j = p_146188_2_;
            p_146188_2_ = p_146188_4_;
            p_146188_4_ = j;
        }

        if (p_146188_3_ > getX() + getWidth())
        {
            p_146188_3_ = getX() + getWidth();
        }

        if (p_146188_1_ > getX() + getWidth())
        {
            p_146188_1_ = getX() + getWidth();
        }

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(5387);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(p_146188_1_, p_146188_4_, 0.0D).endVertex();
        worldrenderer.pos(p_146188_3_, p_146188_4_, 0.0D).endVertex();
        worldrenderer.pos(p_146188_3_, p_146188_2_, 0.0D).endVertex();
        worldrenderer.pos(p_146188_1_, p_146188_2_, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }

    public static class TextFieldStyle {
        @Getter @Setter private ColourRGB backgroundColour;
        @Getter @Setter private ColourRGB placeholderTextColour;
        @Getter @Setter private ColourRGB selectedBackgroundColour;
        @Getter @Setter private ColourRGB outlineColour;
        @Getter @Setter private ColourRGB focusedOutlineColour;
        public TextFieldStyle(ColourRGB backgroundColour, ColourRGB placeholderTextColour, ColourRGB selectedBackgroundColour, ColourRGB outlineColour, ColourRGB focusedOutlineColour) {
            this.backgroundColour = backgroundColour;
            this.placeholderTextColour = placeholderTextColour;
            this.selectedBackgroundColour = selectedBackgroundColour;
            this.outlineColour = outlineColour;
            this.focusedOutlineColour = focusedOutlineColour;
        }
        public static class DefaultStyle extends TextFieldStyle {
            public DefaultStyle() {
                super(new ColourRGB(0, 0, 0, 80), new ColourRGB(118, 118, 118, 122), new ColourRGB(0, 0, 0, 100), new ColourRGB(255, 255, 255, 255), new ColourRGB(255, 255, 160, 255));
            }
        }
    }

}