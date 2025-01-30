package net.memezforbeanz.starminerodyssey.fluid;

import net.memezforbeanz.starminerodyssey.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.dimension.DimensionType;

public abstract class HeliumFluid extends FlowingFluid {
    // Match lava's properties
    private static final int TICK_DELAY = 30;
    private static final int SLOPE_FIND_DISTANCE = 3;
    private static final int DROP_OFF = 2;
    private static final float EXPLOSION_RESISTANCE = 100.0f;

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_HELIUM;
    }

    @Override
    public Fluid getSource() {
        return ModFluids.STILL_HELIUM;
    }

    @Override
    public Item getBucket() {
        return ModFluids.HELIUM_BUCKET;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.HELIUM.defaultBlockState()
                .setValue(BlockStateProperties.LEVEL, getLegacyLevel(state));
    }

    @Override
    protected int getSlopeFindDistance(LevelReader level) {
        return SLOPE_FIND_DISTANCE;
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return TICK_DELAY;
    }

    @Override
    protected int getDropOff(LevelReader level) {
        return DROP_OFF;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !isSame(fluid);
    }

    @Override
    protected float getExplosionResistance() {
        return EXPLOSION_RESISTANCE;
    }

    @Override
    protected boolean canConvertToSource(Level level) {
        return false;
    }

    public static class Flowing extends HeliumFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {

        }

        @Override
        public int getTickDelay(LevelReader levelReader) {
            return 0;
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Still extends HeliumFluid {
        @Override
        protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {

        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public int getTickDelay(LevelReader levelReader) {
            return 30;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}