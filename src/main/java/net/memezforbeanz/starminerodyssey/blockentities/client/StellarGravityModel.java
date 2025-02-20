package net.memezforbeanz.starminerodyssey.blockentities.client;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.blockentities.custom.StellarGravityBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class StellarGravityModel extends GeoModel<StellarGravityBlockEntity> {
    @Override
    public ResourceLocation getModelResource(StellarGravityBlockEntity animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "geo/stellar_gravity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StellarGravityBlockEntity animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "textures/block/stellar_gravity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StellarGravityBlockEntity animatable) {
        return new ResourceLocation(StarminerAdditions.MOD_ID, "animations/stellar_gravity.animation.json");
    }
}
