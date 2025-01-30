package net.memezforbeanz.starminerodyssey.item.client;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.custom.StellarCoreItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class StellarCoreRenderer extends GeoItemRenderer<StellarCoreItem> {
    public StellarCoreRenderer() {
        super(new StellarCoreModel());
       // this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
