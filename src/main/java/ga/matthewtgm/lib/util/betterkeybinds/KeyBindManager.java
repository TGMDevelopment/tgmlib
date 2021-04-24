package ga.matthewtgm.lib.util.betterkeybinds;

import lombok.Getter;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to add Minecraft keybinds with ease.
 *
 * @author MatthewTGM
 */
public class KeyBindManager {

    @Getter
    private static final Map<KeyBind, KeyBinding> keyBinds = new HashMap<>();

    /**
     * Used to register new Minecraft keybinds.
     *
     * @param keyBind The keybind to register.
     * @author MatthewTGM
     */
    public static void register(KeyBind keyBind) {
        KeyBinding generated = new KeyBinding(keyBind.getName(), keyBind.getKeyCode(), keyBind.getCategory());
        keyBinds.put(keyBind, generated);
        ClientRegistry.registerKeyBinding(generated);
    }

    @SubscribeEvent
    protected void onKeyPressed(InputEvent.KeyInputEvent event) {
        keyBinds.forEach((keyBind, keyBinding) -> {
            if (keyBinding.isPressed())
                keyBind.press();

            if (keyBinding.isKeyDown())
                keyBind.hold();
        });
    }

}