package xyz.matthewtgm.tgmlib.kotlin

import net.minecraftforge.fml.common.FMLModContainer
import net.minecraftforge.fml.common.ILanguageAdapter
import net.minecraftforge.fml.common.ModContainer
import net.minecraftforge.fml.relauncher.Side
import java.lang.reflect.Field
import java.lang.reflect.Method

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
