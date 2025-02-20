package net.memezforbeanz.starminerodyssey.mixin;

import dev.enjarai.amethystgravity.block.entity.AbstractFieldGeneratorBlockEntity;

import earth.terrarium.adastra.AdAstra;
import net.memezforbeanz.starminerodyssey.registry.ModBlocks;

import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(AbstractFieldGeneratorBlockEntity.class)
public abstract class AbstractFieldGeneratorBlockEntityMixin {
    @Redirect(method = "searchAmethyst", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean replaceAmethystBlock(BlockState state, net.minecraft.world.level.block.Block block) {
        return state.is(ModBlocks.HELIUM.get()) && state.getFluidState().isSource();
    }
}