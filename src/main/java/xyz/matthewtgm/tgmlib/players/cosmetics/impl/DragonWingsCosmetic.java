package xyz.matthewtgm.tgmlib.players.cosmetics.impl;

import net.minecraft.util.ResourceLocation;
import xyz.matthewtgm.tgmlib.data.ColourRGB;
import xyz.matthewtgm.tgmlib.players.cosmetics.BaseWingsCosmetic;

public class DragonWingsCosmetic extends BaseWingsCosmetic {

    protected ResourceLocation texture;
    protected ColourRGB colour;

    public DragonWingsCosmetic(String name, String id, ColourRGB colour, ResourceLocation texture) {
        super(name, id);
        this.texture = texture;
        this.colour = colour;
    }

    public void tick() {}

    public ResourceLocation texture() {
        return texture;
    }

    public ColourRGB colour() {
        return colour;
    }

}