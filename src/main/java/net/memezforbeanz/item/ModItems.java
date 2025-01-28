package net.memezforbeanz.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.memezforbeanz.starmineroddysey.StarminerAdditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item STELLAR_CORE = registerItem("stellar_core", new Item(new FabricItemSettings()));
    private static void addItemsToItemGroup(FabricItemGroupEntries entries) {
        entries.add(STELLAR_CORE);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(StarminerAdditions.MOD_ID, name), item);
    }

    public static void registerModItems() {
        StarminerAdditions.LOGGER.info("Registering items for" + StarminerAdditions.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModItems::addItemsToItemGroup);
    }
}
