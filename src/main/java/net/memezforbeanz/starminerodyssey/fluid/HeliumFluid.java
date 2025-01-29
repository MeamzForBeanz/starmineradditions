package net.memezforbeanz.starminerodyssey.fluid;

import net.memezforbeanz.starminerodyssey.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.World;

public abstract class HeliumFluid extends FluidAbstract {
    @Override
    public Fluid getStill() {
        return ModFluids.STILL_HELIUM;
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_HELIUM;
    }

    @Override
    public Item getBucketItem() {
        return ModFluids.HELIUM_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ModBlocks.HELIUM.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    public static class Flowing extends HeliumFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        protected boolean isInfinite(World world) {
            return false;
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends HeliumFluid {
        @Override
        protected boolean isInfinite(World world) {
            return false;
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}