package net.memezforbeanz.starminerodyssey.mixin;

import dev.enjarai.amethystgravity.block.entity.AbstractFieldGeneratorBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFieldGeneratorBlockEntity.class)
public class AbstractFieldGeneratorBlockEntityMixin {

    @Redirect(method = "searchAmethyst", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean replaceAmethystBlock(BlockState state, net.minecraft.block.Block block) {
        return state.isOf(Blocks.DIAMOND_BLOCK); // Replace AMETHYST_BLOCK with DIAMOND_BLOCK
    }
}