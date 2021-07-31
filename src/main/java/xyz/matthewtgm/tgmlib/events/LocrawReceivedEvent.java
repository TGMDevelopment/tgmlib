package xyz.matthewtgm.tgmlib.events;

import net.minecraftforge.fml.common.eventhandler.Event;
import xyz.matthewtgm.tgmlib.util.HypixelHelper;

public class LocrawReceivedEvent extends Event {
    public final HypixelHelper.HypixelLocraw locraw;
    public LocrawReceivedEvent(HypixelHelper.HypixelLocraw locraw) {
        this.locraw = locraw;
    }
}