package net.memezforbeanz.starminerodyssey.item.client;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.custom.StellarCoreItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;


public class StellarCoreModel extends GeoModel<StellarCoreItem> {
    @Override
    public Identifier getModelResource(StellarCoreItem animatable) {
        return new Identifier(StarminerAdditions.MOD_ID, "geo/star_core.geo.json");
    }

    @Override
    public Identifier getTextureResource(StellarCoreItem animatable) {
        return new Identifier(StarminerAdditions.MOD_ID, "textures/item/star_core.png");
    }

    @Override
    public Identifier getAnimationResource(StellarCoreItem animatable) {
        return new Identifier(StarminerAdditions.MOD_ID, "animations/star_core.animation.json");
    }
}
