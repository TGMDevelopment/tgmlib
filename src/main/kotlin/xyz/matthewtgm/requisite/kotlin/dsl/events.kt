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

package xyz.matthewtgm.requisite.kotlin.dsl

import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import xyz.matthewtgm.requisite.util.ForgeHelper

/**
 * Heavily modified version of "kotlin-forge-api"'s event DSL
 * https://github.com/ChachyDev/kotlin-forge-api/blob/master/LICENSE
 *
 * CHANGES:
 * Renamed functions and classes.
 * Compressed all files (~4) into this single file.
 *
 * @author ChachyDev
 */

internal val eventHandlers = HashMap<Class<*>, SubscriptionHandler<*>>()

inline fun <reified T : Event> event(): SubscriptionBuilder<T> = SubscriptionBuilder(T::class.java)

class SubscriptionBuilder<T : Event>(
    private val clazz: Class<T>
) {
    private var execute: (T) -> Unit = {}
    fun subscribe(subscribe: (T) -> Unit) {
        execute = subscribe
        build()
    }
    private fun build() {
        val gotten = eventHandlers[clazz] as? SubscriptionHandler<T>
        if (gotten != null) {
            gotten.add(SubscriptionHandler.SubscriptionData(execute))
        } else {
            val handler = object : SubscriptionHandler<T>() {
                @SubscribeEvent
                fun handle(event: T) {
                    if (event::class.java == clazz) {
                        for (handler in handlers) {
                            handler.execute(event)
                        }
                    }
                }
            }
            handler.add(SubscriptionHandler.SubscriptionData(execute))
            eventHandlers[clazz] = handler
            ForgeHelper.registerEventListener(handler)
        }
    }
}

abstract class SubscriptionHandler<T : Event> internal constructor() {
    data class SubscriptionData<T : Event>(val execute: (T) -> Unit)
    protected val handlers: MutableList<SubscriptionData<T>> = ArrayList()
    fun add(data: SubscriptionData<T>) = handlers.add(data)
}