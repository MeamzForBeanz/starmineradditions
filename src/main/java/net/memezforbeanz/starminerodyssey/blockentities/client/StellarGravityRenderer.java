package net.memezforbeanz.starminerodyssey.blockentities.client;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.memezforbeanz.starminerodyssey.blockentities.custom.StellarGravityBlockEntity;
import net.memezforbeanz.starminerodyssey.item.custom.StellarCoreItem;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class StellarGravityRenderer extends GeoBlockRenderer<StellarGravityBlockEntity> {
    public StellarGravityRenderer(BlockEntityRendererProvider.Context context) {
        super(new StellarGravityModel());

        // this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
