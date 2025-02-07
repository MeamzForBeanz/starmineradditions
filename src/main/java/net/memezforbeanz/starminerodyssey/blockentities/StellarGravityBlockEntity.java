package net.memezforbeanz.starminerodyssey.blockentities;


import dev.enjarai.amethystgravity.block.entity.AbstractFieldGeneratorBlockEntity;
import dev.enjarai.amethystgravity.block.entity.PlanetFieldGeneratorBlockEntity;
import dev.enjarai.amethystgravity.gravity.GravityEffect;
import java.util.Arrays;

import net.memezforbeanz.starminerodyssey.registry.ModBlockEntityTypes;
import net.memezforbeanz.starminerodyssey.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
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
public class StellarGravityBlockEntity extends AbstractFieldGeneratorBlockEntity {
    private static final String RADIUS_KEY = "Radius";
    private int radius = 10;

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public StellarGravityBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.STELLARGRAVITY.get(), pos, state);
    }

    protected void clientTick(Level world, BlockPos blockPos, BlockState blockState) {
        AABB box = this.getGravityEffectBox();
        GravityEffect.applyGravityEffectToPlayers(this.getGravityEffect(blockPos), box, world, this.getPolarity() != 0, Arrays.asList(Direction.values()), false);
    }

    protected void serverTick(Level world, BlockPos blockPos, BlockState blockState) {
        AABB box = this.getGravityEffectBox();
        GravityEffect.applyGravityEffectToEntities(this.getGravityEffect(blockPos), box, world, this.getPolarity() != 0, Arrays.asList(Direction.values()), false);
        if (world.getRandom().nextInt(20) == 0) {
            for(int found = this.searchAmethyst(); this.radius > 1 && this.calculateRequiredAmethyst() > found; --this.radius) {
            }

            if (world instanceof ServerLevel) {
                ServerLevel sw = (ServerLevel)world;
                sw.blockEntityChanged(this.worldPosition);
                sw.getChunkSource().blockChanged(this.worldPosition);
            }
        }

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

    public void updateSettings(ServerPlayer player, int radius, int polarity, int visibility) {
        int oldRadius = this.radius;
        this.setRadius(radius);
        this.setPolarity(polarity);
        this.setVisibility(visibility);
        int required = this.calculateRequiredAmethyst();
        int found = this.searchAmethyst();
        if (required > found) {
            this.setRadius(oldRadius);
        }
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }
}
