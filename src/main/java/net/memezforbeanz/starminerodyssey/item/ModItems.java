package net.memezforbeanz.starminerodyssey.item;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;

import net.minecraft.item.Item;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.memezforbeanz.starminerodyssey.item.custom.StellarCoreItem;

import static net.memezforbeanz.starminerodyssey.fluid.ModFluids.HELIUM_BUCKET;


public class ModItems {
    public static final Item STAR_CORE = registerItem("star_core",
            new StellarCoreItem(
                    new FabricItemSettings(),
                    890909000, 10000000, 10000000
            ));


    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(STAR_CORE);
        entries.add(HELIUM_BUCKET);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(StarminerAdditions.MOD_ID, name), item);
    }

    public static void register() {
        StarminerAdditions.LOGGER.info("Registering Mod Items for " + StarminerAdditions.MOD_ID);

    }


}
