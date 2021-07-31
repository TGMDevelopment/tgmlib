package xyz.matthewtgm.tgmlib.kotlin.dsl

import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.client.settings.KeyBinding
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.Event
import xyz.matthewtgm.json.entities.JsonObject
import xyz.matthewtgm.json.util.JsonHelper
import xyz.matthewtgm.tgmlib.data.ColourRGB
import xyz.matthewtgm.tgmlib.keybinds.KeyBind
import xyz.matthewtgm.tgmlib.keybinds.KeyBindManager
import xyz.matthewtgm.tgmlib.util.CustomClientRegistry
import xyz.matthewtgm.tgmlib.util.NpcHelper
import xyz.matthewtgm.tgmlib.util.ResourceHelper
import xyz.matthewtgm.tgmlib.util.StringHelper
import xyz.matthewtgm.tgmlib.util.global.GlobalMinecraft
import java.awt.Color
import java.io.InputStream
import java.util.*

/* Object/Any */
fun Any.stringify(): String = Objects.toString(this)

/* String */
fun String.stripControlCodes(): String = StringHelper.removeColourCodes(this)

/* Numbers */
fun Number.isPositive(): Boolean = this.toInt() >= 0f
fun Number.isNegative(): Boolean = this.toInt() < 0
fun Number.isZero(): Boolean = this.toInt() == 0
fun Number.percentage(min: Int, max: Int): Int = (this.toInt() - min) / (max - min)
fun Number.isBetween(min: Int, max: Int): Boolean = this.toInt() in (min + 1) until max

/* net.minecraft.client.gui.GuiScreen */
fun GuiScreen.open() = mc.displayGuiScreen(this)

/* net.minecraft.client.settings.KeyBinding */
fun KeyBinding.register() = CustomClientRegistry.registerKeyBinding(this)
fun KeyBinding.unregister() = CustomClientRegistry.unregisterKeyBinding(this)

/* net.minecraft.entity.EntityLivingBase */
fun EntityLivingBase.isNpc(): Boolean = NpcHelper.isNpc(this)

/* net.minecraft.util.ResourceLocation */
fun ResourceLocation.bind() = GlobalMinecraft.getTextureManager().bindTexture(this)
fun ResourceLocation.toInputStream(): InputStream = ResourceHelper.toInputStream(this)

/* net.minecraft.client.renderer.texture.DynamicTexture */
fun DynamicTexture.convert(name: String): ResourceLocation = GlobalMinecraft.getTextureManager().getDynamicTextureLocation(name, this)
fun DynamicTexture.bind(name: String) = GlobalMinecraft.getTextureManager().bindTexture(GlobalMinecraft.getTextureManager().getDynamicTextureLocation(name, this))

/* xyz.matthewtgm.tgmlib.keybinds.KeyBind */
fun KeyBind.register() = KeyBindManager.register(this)
fun KeyBind.unregister() = KeyBindManager.unregister(name())

/* java.awt.Color */
fun Color.toColourRGB(): ColourRGB = ColourRGB(red, green, blue, alpha)

/* net.minecraftforge.fml.common.eventhandler.Event */
fun Event.post() = MinecraftForge.EVENT_BUS.post(this)

/* xyz.matthewtgm.json.entities.JsonObject */
fun JsonObject.prettify(): String = JsonHelper.makePretty(this)