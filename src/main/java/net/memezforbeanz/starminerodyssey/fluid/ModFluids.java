package net.memezforbeanz.starminerodyssey.fluid;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;

public class ModFluids {
    public static FlowingFluid STILL_HELIUM;
    public static FlowingFluid FLOWING_HELIUM;
    public static Item HELIUM_BUCKET;

    public static void register() {
        STILL_HELIUM = Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(StarminerAdditions.MOD_ID, "star_helium_still"), new HeliumFluid.Still());
        FLOWING_HELIUM = Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(StarminerAdditions.MOD_ID, "star_helium_flow"), new HeliumFluid.Flowing());
        HELIUM_BUCKET = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(StarminerAdditions.MOD_ID, "star_helium_bucket"),
                new BucketItem(STILL_HELIUM, new Item.Properties().stacksTo(1)));
    }
}
