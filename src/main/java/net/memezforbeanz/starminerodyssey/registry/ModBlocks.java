package net.memezforbeanz.starminerodyssey.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.blocks.StellarGravityBlock;
import net.memezforbeanz.starminerodyssey.blocks.fluid.HeliumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS;
    public static final ResourcefulRegistry<Block> FLUIDS;
    public static final RegistryEntry<Block> HELIUM;
    public static final RegistryEntry<Block> STELLARGRAVITY;


    public ModBlocks() {
    }

    static {
        BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, StarminerAdditions.MOD_ID);
        FLUIDS = ResourcefulRegistries.create(BLOCKS);
        HELIUM = FLUIDS.register("star_helium", () -> new HeliumLiquidBlock(ModFluidProperties.HELIUM, BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.COLOR_CYAN)));
        STELLARGRAVITY = BLOCKS.register("stellar_gravity", () -> new StellarGravityBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)));

    }
}