package net.memezforbeanz.starminerodyssey.blockentities.custom;


import dev.enjarai.amethystgravity.block.entity.AbstractFieldGeneratorBlockEntity;
import dev.enjarai.amethystgravity.gravity.GravityEffect;
import java.util.Arrays;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.systems.GravityApi;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.registry.ModBlockEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class StellarGravityBlockEntity extends AbstractFieldGeneratorBlockEntity implements GeoBlockEntity {
    private static final String RADIUS_KEY = "Radius";
    private int radius = 10;
    private static final float DEFAULT_GRAVITY = 1.0f;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public StellarGravityBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.STELLARGRAVITY.get(), pos, state);
    }



    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    protected void clientTick(Level world, BlockPos blockPos, BlockState blockState) {
        AABB box = this.getGravityEffectBox();
        GravityEffect.applyGravityEffectToPlayers(this.getGravityEffect(blockPos), box, world, this.getPolarity() != 0, Arrays.asList(Direction.values()), false);

    }

    protected void serverTick(Level world, BlockPos blockPos, BlockState blockState) {
        AABB box = this.getGravityEffectBox();
        GravityEffect.applyGravityEffectToEntities(
                this.getGravityEffect(blockPos),
                box,
                world,
                this.getPolarity() != 0,
                Arrays.asList(Direction.values()),
                false
        );

        if (world.getRandom().nextInt(20) == 0) {
            int found = this.searchAmethyst();
            int target = computeTargetRadius(found);

            // Gradually adjust radius toward target (by one unit per tick)
            if (this.radius < target) {
                this.radius++;
                StarminerAdditions.LOGGER.info("Found target radius " + this.radius);

            } else if (this.radius > target) {
                StarminerAdditions.LOGGER.info("Found target radius " + this.radius);

                this.radius--;
            }

            if (world instanceof ServerLevel sw) {
                sw.blockEntityChanged(this.worldPosition);
                sw.getChunkSource().blockChanged(this.worldPosition);
            }
        }
    }

    /**
     * Given a candidate radius (in our internal integer units), compute the required amethyst
     * count. This replicates the logic in calculateRequiredAmethyst (which uses the inflated box).
     *
     * Effective radius = candidate / 10.0
     * Box size = 1 + 2*(effective radius) in each dimension.
     * Volume = (1 + 2*(candidate/10))^3
     * Then the requirement is roughly floor( (volume)^(0.6666) ) / 4.
     */
    private int calculateRequiredAmethystForCandidate(int candidate) {
        double effectiveRadius = candidate / 10.0;
        double volume = Math.pow(1 + 2 * effectiveRadius, 3);
        // Replicates: (int)Math.floor(Math.pow(volume, 0.6666666666666666)) / 4
        int required = (int)Math.floor(Math.pow(volume, 0.6666666666666666)) / 4;
        return required;
    }

    /**
     * Computes the target radius (in our internal integer units) based on the number of amethyst
     * blocks found. We iterate upward until the required amethyst count for the next candidate
     * would exceed the found count.
     */
    private int computeTargetRadius(int found) {
        int candidate = this.radius; // start at current radius
        int maxCandidate = 100;       // maximum allowed value (adjust as needed)

        // Increase candidate as long as the next candidate's requirement is met.
        while (candidate < maxCandidate && calculateRequiredAmethystForCandidate(candidate + 1) <= found) {
            candidate++;
        }
        // If the candidate is too high (its requirement exceeds found), lower it.
        while (candidate > 1 && calculateRequiredAmethystForCandidate(candidate) > found) {
            candidate--;
        }
        return candidate;
    }




    public AABB getGravityEffectBox() {
        BlockPos blockPos = this.getBlockPos();
        Vec3 pos1 = new Vec3((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
        Vec3 pos2 = pos1.add((double)1.0F, (double)1.0F, (double)1.0F);
        return (new AABB(pos1, pos2)).inflate(this.getRadius());
    }

    private GravityEffect getGravityEffect(BlockPos blockPos) {
        return new GravityEffect((Direction)null, this.getVolume(), blockPos);
    }

    public double getRadius() {
        return (double)this.radius / (double)10.0F;
    }

    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("Radius", this.radius);
    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.radius = nbt.getInt("Radius");
    }

    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeInt(this.radius);
        buf.writeInt(this.polarity);
        buf.writeInt(this.visibility);
    }

    public void updateSettings(int radius, int polarity, int visibility) {
        int oldRadius = this.radius;
        this.radius = radius;
        this.setPolarity(polarity);
        this.setVisibility(visibility);

        if (oldRadius != this.radius) {
            StarminerAdditions.LOGGER.info("Updated settings - Radius: {}, Polarity: {}, Visibility: {}",
                    radius, polarity, visibility);
            //this.updateAdAstraGravity();
        }

    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
controllerRegistrar.add(new AnimationController<GeoAnimatable>(this,"controller", 0, this::predicate));
    }
    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("stellar_gravity.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
