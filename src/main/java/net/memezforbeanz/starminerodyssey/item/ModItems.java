package net.memezforbeanz.starminerodyssey.item;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.Registry;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.custom.StellarCoreItem;

import static net.memezforbeanz.starminerodyssey.fluid.ModFluids.HELIUM_BUCKET;

public class ModItems {
    public static final Item STAR_CORE = registerItem("star_core",
            new StellarCoreItem(
                    new Item.Properties().rarity(Rarity.EPIC),
                    890909000, 10000000, 10000000
            ));

    private static void addItemsToIngredientItemGroup(CreativeModeTab.Output entries) {
        entries.accept(STAR_CORE);
        entries.accept(HELIUM_BUCKET);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(StarminerAdditions.MOD_ID, name), item);
    }

    public static void register() {
        StarminerAdditions.LOGGER.info("Registering Mod Items for " + StarminerAdditions.MOD_ID);
    }
}