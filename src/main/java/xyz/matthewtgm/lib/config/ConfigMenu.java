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

package xyz.matthewtgm.lib.config;

import lombok.Getter;
import net.minecraft.client.gui.GuiScreen;
import xyz.matthewtgm.json.objects.JsonObject;
import xyz.matthewtgm.lib.util.ExceptionHelper;
import xyz.matthewtgm.lib.util.GuiHelper;
import xyz.matthewtgm.tgmconfig.ConfigEntry;
import xyz.matthewtgm.tgmconfig.TGMConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class ConfigMenu {

    private final ConfigMenu $this = this;
    @Getter private final List<ConfigOptionHolder> options = new ArrayList<>();
    public final GuiScreen screen;

    public ConfigMenu() {
        discoverOptions();
        this.screen = ConfigScreenGenerator.generate(this, options);
    }

    public abstract String title();
    public abstract TGMConfig config();

    public void save() {
        ExceptionHelper.tryCatch(() -> {
            for (ConfigOptionHolder optionHolder : options) config().addAndSave(new ConfigEntry<>(optionHolder.field.getName(), optionHolder.field.get($this)));
        });
    }

    public void load() {
        ExceptionHelper.tryCatch(() -> {
            for (ConfigOptionHolder optionHolder : options) {
                if (config().containsKey(optionHolder.field.getName())) optionHolder.field.set($this, config().get(optionHolder.field.getName()));
                else optionHolder.field.set($this, null);
            }
        });
    }

    public void open() {
        GuiHelper.open(screen);
    }

    private void discoverOptions() {
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ConfigOption.class)) options.add(new ConfigOptionHolder(field, field.getAnnotation(ConfigOption.class)));
        }
    }

    public static class ConfigOptionHolder {
        public final Field field;
        public final ConfigOption option;
        public ConfigOptionHolder(Field field, ConfigOption option) {
            this.field = field;
            this.option = option;
        }
        public String toString() {
            return new JsonObject().add("field", field).add("option", new JsonObject().add("name", option.name()).add("description", option.description()).add("category", option.category()).add("type", option.type())).toJson();
        }
    }

}