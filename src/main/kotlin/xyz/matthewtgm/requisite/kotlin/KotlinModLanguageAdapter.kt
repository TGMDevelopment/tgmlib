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

package xyz.matthewtgm.requisite.kotlin

import net.minecraftforge.fml.common.FMLModContainer
import net.minecraftforge.fml.common.ILanguageAdapter
import net.minecraftforge.fml.common.ModContainer
import net.minecraftforge.fml.relauncher.Side
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Permits mods to be able to use `object` as their mod holder.
 */
class KotlinModLanguageAdapter : ILanguageAdapter {

    override fun supportsStatics() = true

    override fun setProxy(target: Field, proxyTarget: Class<*>, proxy: Any) {
        var instance: Field? = null
        for (field in proxyTarget.fields) {
            if (field.name.lowercase().equals("instance")) {
                instance = field
            }
        }
        target.set(instance?.get(null), proxy)
    }

    override fun getNewInstance(
        container: FMLModContainer,
        objectClass: Class<*>,
        classLoader: ClassLoader,
        factoryMarkedAnnotation: Method?
    ): Any {
        return objectClass.fields.find { it.name.lowercase() == "instance" }?.get(null) ?: objectClass.newInstance()
    }

    override fun setInternalProxies(mod: ModContainer, side: Side, loader: ClassLoader) {}

}
