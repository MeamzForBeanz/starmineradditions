package net.memezforbeanz.starminerodyssey.registry;


import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.utils.EnergyUtils;
import earth.terrarium.adastra.common.utils.FluidUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class Tabs {
    public static final ResourcefulRegistry<CreativeModeTab> TABS;
    public static final Supplier<CreativeModeTab> TAB;

    public Tabs() {
    }

    static {
        TABS = ResourcefulRegistries.create(BuiltInRegistries.CREATIVE_MODE_TAB, StarminerAdditions.MOD_ID);
        TAB = (new ResourcefulCreativeTab(new ResourceLocation("starminer", "main"))).setItemIcon(net.memezforbeanz.starminerodyssey.registry.ModItems.STELLAR_CORE).addRegistry(ModItems.ITEMS).build();
    }
}
