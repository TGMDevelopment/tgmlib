package ga.matthewtgm.lib;
import ga.matthewtgm.lib.util.ForgeUtils;
import ga.matthewtgm.lib.util.GuiHelper;
import ga.matthewtgm.lib.util.keybindings.KeyBindManager;
import lombok.Getter;

public class TGMLib {

    private static TGMLib INSTANCE;

    public static String NAME = "TGMLib", VERSION = "1.0.3";

    @Getter private boolean listenersRegistered;

    public void onForgePreInit() {
        if (!listenersRegistered) {
            ForgeUtils.registerEventListeners(new KeyBindManager(), new GuiHelper());
            listenersRegistered = true;
        }
    }

    public static TGMLib getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TGMLib();
        return INSTANCE;
    }

}