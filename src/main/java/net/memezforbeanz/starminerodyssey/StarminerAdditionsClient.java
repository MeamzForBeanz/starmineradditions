package net.memezforbeanz.starminerodyssey;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.memezforbeanz.starminerodyssey.blockentities.client.StellarGravityRenderer;
import net.memezforbeanz.starminerodyssey.registry.ModBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class StarminerAdditionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(ModBlockEntityTypes.STELLARGRAVITY.get(), StellarGravityRenderer::new);
    }
}
