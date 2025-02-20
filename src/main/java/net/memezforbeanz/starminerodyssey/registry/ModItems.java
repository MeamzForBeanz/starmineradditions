package net.memezforbeanz.starminerodyssey.registry;


import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.common.items.TooltipBlockItem;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.memezforbeanz.starminerodyssey.constants.ConstantComponents;
import net.memezforbeanz.starminerodyssey.item.custom.StellarGravityItem;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.item.Item;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.custom.StellarCoreItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;

public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS;
    public static final RegistryEntry<Item> STELLAR_CORE;
    public static final RegistryEntry<Item> HELIUM_BUCKET;
    public static final RegistryEntry<Item> STELLAR_GRAVITY;


    public ModItems() {
    }

    static {

        ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, StarminerAdditions.MOD_ID);
        HELIUM_BUCKET = ITEMS.register("star_helium_bucket", () -> new FluidBucketItem(ModFluidProperties.HELIUM, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)));
        STELLAR_CORE = ITEMS.register("star_core", () -> new StellarCoreItem(new Item.Properties().rarity(Rarity.EPIC),
                890909000, 10000000, 10000000));
        STELLAR_GRAVITY = ITEMS.register("stellar_gravity", () -> new StellarGravityItem(ModBlocks.STELLARGRAVITY.get(), ConstantComponents.STELLAR_GRAVITY_INFO ,new FabricItemSettings()));


    }
}