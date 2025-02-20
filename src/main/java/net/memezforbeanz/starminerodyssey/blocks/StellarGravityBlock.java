package net.memezforbeanz.starminerodyssey.blocks;

import dev.enjarai.amethystgravity.block.AbstractFieldGeneratorBlock;
import net.memezforbeanz.starminerodyssey.blockentities.custom.StellarGravityBlockEntity;
import net.memezforbeanz.starminerodyssey.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class StellarGravityBlock extends AbstractFieldGeneratorBlock<StellarGravityBlockEntity> {

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public StellarGravityBlock(BlockBehaviour.Properties settings) {
        super(true, settings);
    }

    public BlockEntityType<StellarGravityBlockEntity> getBlockEntity() {
        return ModBlockEntityTypes.STELLARGRAVITY.get();
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StellarGravityBlockEntity(pos, state);
    }
}
