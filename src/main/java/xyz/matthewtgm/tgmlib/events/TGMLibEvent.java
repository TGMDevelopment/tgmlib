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

package xyz.matthewtgm.tgmlib.events;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import xyz.matthewtgm.tgmlib.TGMLib;
import xyz.matthewtgm.tgmlib.players.cosmetics.BaseCosmetic;
import xyz.matthewtgm.tgmlib.players.cosmetics.CosmeticType;
import xyz.matthewtgm.tgmlib.keybinds.KeyBind;
import xyz.matthewtgm.tgmlib.util.GuiEditor;

public class TGMLibEvent extends Event {
    public final TGMLib tgmLib;
    public TGMLibEvent(TGMLib tgmLib) {
        this.tgmLib = tgmLib;
    }
    public static class KeyEvent extends TGMLibEvent {
        public final KeyBind keyBind;
        public KeyEvent(TGMLib tgmLib, KeyBind keyBind) {
            super(tgmLib);
            this.keyBind = keyBind;
        }
        public static class KeyPressedEvent extends KeyEvent {
            public KeyPressedEvent(TGMLib tgmLib, KeyBind keyBind) {
                super(tgmLib, keyBind);
            }
        }
        public static class KeyReleasedEvent extends KeyEvent {
            public KeyReleasedEvent(TGMLib tgmLib, KeyBind keyBind) {
                super(tgmLib, keyBind);
            }
        }
        public static class KeyHeldEvent extends KeyEvent {
            public KeyHeldEvent(TGMLib tgmLib, KeyBind keyBind) {
                super(tgmLib, keyBind);
            }
        }
        @Cancelable
        public static class Pre extends KeyEvent {
            public Pre(TGMLib tgmLib, KeyBind keyBind) {
                super(tgmLib, keyBind);
            }
        }
        public static class Post extends KeyEvent {
            public Post(TGMLib tgmLib, KeyBind keyBind) {
                super(tgmLib, keyBind);
            }
        }
    }
    public static class CosmeticRenderEvent extends TGMLibEvent {
        public final AbstractClientPlayer player;
        public final float limbSwing, limbSwingAmount, partialTicks, tickAge, netHeadYaw, netHeadPitch, scale;

        public final BaseCosmetic cosmetic;
        public final CosmeticType type;
        public CosmeticRenderEvent(TGMLib tgmLib, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale, BaseCosmetic cosmetic, CosmeticType type) {
            super(tgmLib);
            this.player = player;
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.partialTicks = partialTicks;
            this.tickAge = tickAge;
            this.netHeadYaw = netHeadYaw;
            this.netHeadPitch = netHeadPitch;
            this.scale = scale;
            this.cosmetic = cosmetic;
            this.type = type;
        }
        public static class Pre extends TGMLibEvent {
            public Pre(TGMLib tgmLib) {
                super(tgmLib);
                throw new UnsupportedOperationException("CosmeticRenderEvent doesn't have a pre-event!");
            }
        }
        public static class Post extends TGMLibEvent {
            public Post(TGMLib tgmLib) {
                super(tgmLib);
                throw new UnsupportedOperationException("CosmeticRenderEvent doesn't have a post-event!");
            }
        }
    }
    public static class GuiEditEvent extends TGMLibEvent {
        public final Class<? extends GuiScreen> screenClz;
        public final GuiEditor.GuiEditRunnable guiEditRunnable;
        public GuiEditEvent(TGMLib tgmLib, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
            super(tgmLib);
            this.screenClz = screenClz;
            this.guiEditRunnable = guiEditRunnable;
        }
        public static class EditAddEvent extends GuiEditEvent {
            public EditAddEvent(TGMLib tgmLib, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
                super(tgmLib, screenClz, guiEditRunnable);
            }
        }
        public static class EditRemoveEvent extends GuiEditEvent {
            public EditRemoveEvent(TGMLib tgmLib, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
                super(tgmLib, screenClz, guiEditRunnable);
            }
        }
        @Cancelable
        public static class Pre extends GuiEditEvent {
            public Pre(TGMLib tgmLib, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
                super(tgmLib, screenClz, guiEditRunnable);
            }
        }
        public static class Post extends GuiEditEvent {
            public Post(TGMLib tgmLib, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
                super(tgmLib, screenClz, guiEditRunnable);
            }
        }
    }
    public static class CustomRegisterPacketEvent extends TGMLibEvent {
        public final NetworkManager netManager;
        public CustomRegisterPacketEvent(TGMLib tgmLib, NetworkManager netManager) {
            super(tgmLib);
            this.netManager = netManager;
        }
        @Cancelable
        public static class Pre extends CustomRegisterPacketEvent {
            public final ByteBuf byteBuf;
            public Pre(TGMLib tgmLib, NetworkManager netManager, ByteBuf byteBuf) {
                super(tgmLib, netManager);
                this.byteBuf = byteBuf;
            }
        }
        public static class Post extends CustomRegisterPacketEvent {
            public Post(TGMLib tgmLib, NetworkManager netManager) {
                super(tgmLib, netManager);
            }
        }
    }
    public static class Pre extends TGMLibEvent {
        public Pre(TGMLib tgmLib) {
            super(tgmLib);
        }
    }
    public static class Post extends TGMLibEvent {
        public Post(TGMLib tgmLib) {
            super(tgmLib);
        }
    }
}