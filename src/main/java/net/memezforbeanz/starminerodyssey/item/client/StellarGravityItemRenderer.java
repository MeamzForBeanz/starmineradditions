package net.memezforbeanz.starminerodyssey.item.client;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.custom.StellarCoreItem;
import net.memezforbeanz.starminerodyssey.item.custom.StellarGravityItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class StellarGravityItemRenderer extends GeoItemRenderer<StellarGravityItem> {
    public StellarGravityItemRenderer() {
        super(new StellarGravityItemModel());
        // this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
