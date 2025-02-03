package net.memezforbeanz.starminerodyssey.blocks.fluid;

import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.FluidData;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.registry.ModBlocks;
import net.memezforbeanz.starminerodyssey.registry.ModDamageSources;
import net.memezforbeanz.starminerodyssey.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class HeliumLiquidBlock extends BotariumLiquidBlock {
    public HeliumLiquidBlock(FluidData data, BlockBehaviour.Properties properties) {
        super(data, properties);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.makeStuckInBlock(state, new Vec3((double)0.9F, (double)1.5F, (double)0.9F));
            if (level.isClientSide()) {
                RandomSource random = level.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {
                    level.addParticle(ParticleTypes.ASH, entity.getX(), (double)(pos.getY() + 1), entity.getZ(), (double)(Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F), (double)0.05F, (double)(Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F));
                }
            }

        }
        if (!level.isClientSide()) {
            entity.setSharedFlagOnFire(true);
            entity.hurt(net.memezforbeanz.starminerodyssey.registry.ModDamageSources.create(level, ModDamageSources.HELIUM), (float)(4 * (entity.fireImmune() ? 2 : 1)));
        }
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (this.shouldSpreadLiquid(level, pos, state)) {
            level.scheduleTick(pos, state.getFluidState().getType(), ((FlowingFluid) net.memezforbeanz.starminerodyssey.registry.ModFluids.HELIUM.get()).getTickDelay(level));
        }

    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (this.shouldSpreadLiquid(level, pos, state)) {
            level.scheduleTick(pos, state.getFluidState().getType(), ((FlowingFluid) net.memezforbeanz.starminerodyssey.registry.ModFluids.HELIUM.get()).getTickDelay(level));
        }

    }

    private boolean shouldSpreadLiquid(Level level, BlockPos pos, BlockState state) {
        for(Direction direction : new Direction[]{Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST}) {
            BlockPos blockPos = pos.relative(direction.getOpposite());
            BlockState blockState = level.getBlockState(blockPos);
            FluidState fluidState = level.getFluidState(blockPos);

            if (blockState.is(Blocks.SNOW_BLOCK) || blockState.is(Blocks.POWDER_SNOW)) {
                level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
                level.levelEvent(1501, pos, 0);
                return false;
            }

            if (blockState.is(Blocks.BLUE_ICE)) {
                level.setBlockAndUpdate(blockPos, Blocks.PACKED_ICE.defaultBlockState());

                level.levelEvent(1501, pos, 0);
                return false;
            }
            if (blockState.is(Blocks.PACKED_ICE)) {
                level.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());

                level.levelEvent(1501, pos, 0);
                return false;
            }
            if (blockState.is(Blocks.ICE)) {
                level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());

                level.levelEvent(1501, pos, 0);
                return false;
            }

                if (fluidState.is(Fluids.WATER) || fluidState.is(Fluids.FLOWING_WATER)) {
                level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());

                level.levelEvent(1501, pos, 0);
                return false;
            }
        }

        return true;
    }
}
