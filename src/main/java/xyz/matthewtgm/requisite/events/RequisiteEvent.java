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

package xyz.matthewtgm.requisite.events;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import xyz.matthewtgm.requisite.Requisite;
import xyz.matthewtgm.requisite.players.cosmetics.BaseCosmetic;
import xyz.matthewtgm.requisite.players.cosmetics.CosmeticType;
import xyz.matthewtgm.requisite.keybinds.KeyBind;
import xyz.matthewtgm.requisite.util.GuiEditor;

public class RequisiteEvent extends Event {
    public final Requisite requisite;
    public RequisiteEvent(Requisite requisite) {
        this.requisite = requisite;
    }
    public static class KeyEvent extends RequisiteEvent {
        public final KeyBind keyBind;
        public KeyEvent(Requisite requisite, KeyBind keyBind) {
            super(requisite);
            this.keyBind = keyBind;
        }
        public static class KeyPressedEvent extends KeyEvent {
            public KeyPressedEvent(Requisite requisite, KeyBind keyBind) {
                super(requisite, keyBind);
            }
        }
        public static class KeyReleasedEvent extends KeyEvent {
            public KeyReleasedEvent(Requisite requisite, KeyBind keyBind) {
                super(requisite, keyBind);
            }
        }
        public static class KeyHeldEvent extends KeyEvent {
            public KeyHeldEvent(Requisite requisite, KeyBind keyBind) {
                super(requisite, keyBind);
            }
        }
        @Cancelable
        public static class Pre extends KeyEvent {
            public Pre(Requisite requisite, KeyBind keyBind) {
                super(requisite, keyBind);
            }
        }
        public static class Post extends KeyEvent {
            public Post(Requisite requisite, KeyBind keyBind) {
                super(requisite, keyBind);
            }
        }
    }
    public static class CosmeticRenderEvent extends RequisiteEvent {
        public final AbstractClientPlayer player;
        public final float limbSwing, limbSwingAmount, partialTicks, tickAge, netHeadYaw, netHeadPitch, scale;

        public final BaseCosmetic cosmetic;
        public final CosmeticType type;
        public CosmeticRenderEvent(Requisite requisite, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale, BaseCosmetic cosmetic, CosmeticType type) {
            super(requisite);
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
        public static class Pre extends RequisiteEvent {
            public Pre(Requisite requisite) {
                super(requisite);
                throw new UnsupportedOperationException("CosmeticRenderEvent doesn't have a pre-event!");
            }
        }
        public static class Post extends RequisiteEvent {
            public Post(Requisite requisite) {
                super(requisite);
                throw new UnsupportedOperationException("CosmeticRenderEvent doesn't have a post-event!");
            }
        }
    }
    public static class GuiEditEvent extends RequisiteEvent {
        public final Class<? extends GuiScreen> screenClz;
        public final GuiEditor.GuiEditRunnable guiEditRunnable;
        public GuiEditEvent(Requisite requisite, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
            super(requisite);
            this.screenClz = screenClz;
            this.guiEditRunnable = guiEditRunnable;
        }
        public static class EditAddEvent extends GuiEditEvent {
            public EditAddEvent(Requisite requisite, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
                super(requisite, screenClz, guiEditRunnable);
            }
        }
        public static class EditRemoveEvent extends GuiEditEvent {
            public EditRemoveEvent(Requisite requisite, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
                super(requisite, screenClz, guiEditRunnable);
            }
        }
        @Cancelable
        public static class Pre extends GuiEditEvent {
            public Pre(Requisite requisite, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
                super(requisite, screenClz, guiEditRunnable);
            }
        }
        public static class Post extends GuiEditEvent {
            public Post(Requisite requisite, Class<? extends GuiScreen> screenClz, GuiEditor.GuiEditRunnable guiEditRunnable) {
                super(requisite, screenClz, guiEditRunnable);
            }
        }
    }
    public static class CustomRegisterPacketEvent extends RequisiteEvent {
        public final NetworkManager netManager;
        public CustomRegisterPacketEvent(Requisite requisite, NetworkManager netManager) {
            super(requisite);
            this.netManager = netManager;
        }
        @Cancelable
        public static class Pre extends CustomRegisterPacketEvent {
            public final ByteBuf byteBuf;
            public Pre(Requisite requisite, NetworkManager netManager, ByteBuf byteBuf) {
                super(requisite, netManager);
                this.byteBuf = byteBuf;
            }
        }
        public static class Post extends CustomRegisterPacketEvent {
            public Post(Requisite requisite, NetworkManager netManager) {
                super(requisite, netManager);
            }
        }
    }
    public static class Pre extends RequisiteEvent {
        public Pre(Requisite requisite) {
            super(requisite);
        }
    }
    public static class Post extends RequisiteEvent {
        public Post(Requisite requisite) {
            super(requisite);
        }
    }
}