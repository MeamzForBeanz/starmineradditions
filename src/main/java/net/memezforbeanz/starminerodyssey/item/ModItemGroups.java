package net.memezforbeanz.starminerodyssey.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.fluid.ModFluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup STARMINER_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(StarminerAdditions.MOD_ID, "starminer"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.starminer"))
                    .icon(() -> new ItemStack(ModItems.STAR_CORE)).entries((displayContext, entries) -> {
                        entries.add(ModFluids.HELIUM_BUCKET);
                        entries.add(ModItems.STAR_CORE);


                    }).build());


    public static void register() {
        StarminerAdditions.LOGGER.info("Registering Item Groups for " + StarminerAdditions.MOD_ID);
    }
}
