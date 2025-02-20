package net.memezforbeanz.starminerodyssey.registry;


import com.mojang.datafixers.types.Type;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;

import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.blockentities.custom.StellarGravityBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public class ModBlockEntityTypes {
    public static final ResourcefulRegistry<BlockEntityType<?>> BLOCK_ENTITY_TYPES;
    public static final RegistryEntry<BlockEntityType<StellarGravityBlockEntity>> STELLARGRAVITY;

    public ModBlockEntityTypes() {
    }

    public static <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityType.BlockEntitySupplier<E> factory, ResourcefulRegistry<Block> registry) {
        return Builder.of(factory, (Block[])registry.stream().map(RegistryEntry::get).toArray((x$0) -> new Block[x$0])).build((Type)null);
    }

    static {
        BLOCK_ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, StarminerAdditions.MOD_ID);
        STELLARGRAVITY = BLOCK_ENTITY_TYPES.register("stellar_gravity", () -> RegistryHelpers.createBlockEntityType(StellarGravityBlockEntity::new, new Block[]{(Block) ModBlocks.STELLARGRAVITY.get()}));
    }
}
