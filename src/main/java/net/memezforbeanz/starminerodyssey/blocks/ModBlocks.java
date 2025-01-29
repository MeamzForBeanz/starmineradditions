package net.memezforbeanz.starminerodyssey.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.memezforbeanz.starminerodyssey.fluid.ModFluids;

public class ModBlocks {
    public static Block HELIUM;

    public static void register() {
        HELIUM = Registry.register(Registries.BLOCK, new Identifier(StarminerAdditions.MOD_ID, "star_helium_fluid"), new FluidBlock(ModFluids.STILL_HELIUM, FabricBlockSettings.copy(Blocks.LAVA)){});
    }
}