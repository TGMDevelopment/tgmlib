package ga.matthewtgm.lib.util.keybindings;

import lombok.Getter;

/**
 * Custom keybind used to create a {@link net.minecraft.client.settings.KeyBinding}.
 * Must be used along with {@link KeyBindManager}.
 */
public abstract class KeyBind {

    @Getter private final String name;
    @Getter private final int keyCode;
    @Getter private final String category;

    public KeyBind(String description, int keyCode, String category) {
        this.name = description;
        this.keyCode = keyCode;
        this.category = category;
    }

    /**
     * Action performed on key press.
     */
    public abstract void press();

    /**
     * Action performed on key hold.
     */
    public abstract void hold();

}