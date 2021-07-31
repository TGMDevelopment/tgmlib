package xyz.matthewtgm.tgmlib.kotlin.dsl

import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import xyz.matthewtgm.tgmlib.util.ForgeHelper

/**
 * Heavily modified version of "kotlin-forge-api"'s event DSL
 * https://github.com/ChachyDev/kotlin-forge-api/blob/master/LICENSE
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