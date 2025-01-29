package net.memezforbeanz.starminerodyssey.item.client;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.custom.StellarCoreItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class StellarCoreModel extends GeoModel<StellarCoreItem> {
    @Override
    public ResourceLocation getModelResource(StellarCoreItem animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "geo/star_core.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StellarCoreItem animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "textures/item/star_core.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StellarCoreItem animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "animations/star_core.animation.json");
    }
}
