package net.memezforbeanz.starminerodyssey.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.fluid.ModFluids;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final CreativeModeTab STARMINER_GROUP = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB, // Use registry INSTANCE, not registry key
            new ResourceLocation(StarminerAdditions.MOD_ID, "starminer"),
            FabricItemGroup.builder()
                    .title(Component.translatable("itemgroup.starminer"))
                    .icon(() -> new ItemStack(ModItems.STAR_CORE))
                    .displayItems((displayParameters, entries) -> {
                        entries.accept(ModFluids.HELIUM_BUCKET);
                        entries.accept(ModItems.STAR_CORE);
                    })
                    .build()
    );

    public static void register() {
        StarminerAdditions.LOGGER.info("Registering Item Groups for " + StarminerAdditions.MOD_ID);
    }
}