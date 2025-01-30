package net.memezforbeanz.starminerodyssey.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.memezforbeanz.starminerodyssey.fluid.ModFluids;

public class ModBlocks {
    public static Block HELIUM;

    public static void register() {
        HELIUM = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(StarminerAdditions.MOD_ID, "star_helium_fluid"),
                new LiquidBlock(ModFluids.STILL_HELIUM, FabricBlockSettings.copy(Blocks.LAVA)){});
    }
}