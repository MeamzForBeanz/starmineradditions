package net.memezforbeanz.starminerodyssey.item.client;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.custom.StellarGravityItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class StellarGravityItemModel extends GeoModel<StellarGravityItem> {
    @Override
    public ResourceLocation getModelResource(StellarGravityItem animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "geo/stellar_gravity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StellarGravityItem animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "textures/block/stellar_gravity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StellarGravityItem animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "animations/stellar_gravity.animation.json");
    }
}
