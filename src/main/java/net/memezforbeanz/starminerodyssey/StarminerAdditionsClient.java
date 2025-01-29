package net.memezforbeanz.starminerodyssey;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.memezforbeanz.starminerodyssey.fluid.ModFluids;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StarminerAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register fluid rendering
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_HELIUM, ModFluids.FLOWING_HELIUM, new SimpleFluidRenderHandler(
                new Identifier("starminer-additions:block/star_helium_still"),
                new Identifier("starminer-additions:block/star_helium_flow"),
                0x4CC248 // Fluid color
        ));

        // Set render layer for fluids
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.STILL_HELIUM, ModFluids.FLOWING_HELIUM);
    }
}
