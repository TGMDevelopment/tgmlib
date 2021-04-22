package ga.matthewtgm.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Used to enhance bits of code relating to the Minecraft {@link GuiScreen}.
 */
public class GuiHelper {

    private static GuiScreen toOpen;

    /**
     * Opens a {@link GuiScreen}. (will be most commonly used in commands.)
     *
     * @param screen the screen to open.
     */
    public static void open(GuiScreen screen) {
        toOpen = screen;
    }

    @SubscribeEvent
    protected void onTick(TickEvent event) {
        if (toOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(toOpen);
            toOpen = null;
        }
    }

}