package net.memezforbeanz.starminerodyssey.fluid;


import net.memezforbeanz.starminerodyssey.StarminerAdditions;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static FlowableFluid STILL_HELIUM;
    public static FlowableFluid FLOWING_HELIUM;
    public static Item HELIUM_BUCKET;

    public static void register() {
        STILL_HELIUM = Registry.register(Registries.FLUID, new Identifier(StarminerAdditions.MOD_ID, "star_helium_still"), new HeliumFluid.Still());
        FLOWING_HELIUM = Registry.register(Registries.FLUID, new Identifier(StarminerAdditions.MOD_ID, "star_helium_flow"), new HeliumFluid.Flowing());
        HELIUM_BUCKET = Registry.register(Registries.ITEM, new Identifier(StarminerAdditions.MOD_ID, "star_helium_bucket"),
                new BucketItem(STILL_HELIUM, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
    }
}