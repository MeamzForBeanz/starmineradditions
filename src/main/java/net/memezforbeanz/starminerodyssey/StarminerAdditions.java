package net.memezforbeanz.starminerodyssey;

import earth.terrarium.adastra.common.planets.AdAstraData;
import net.memezforbeanz.starminerodyssey.registry.*;
import net.memezforbeanz.starminerodyssey.blocks.fluid.ModFluidProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.slf4j.Logger;


import com.mojang.logging.LogUtils;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.utils.radio.StationLoader;

import java.util.function.BiConsumer;

public class StarminerAdditions {
	public static final String MOD_ID = "starminer-additions";
	public static final Logger LOGGER = LogUtils.getLogger();

	public StarminerAdditions() {
	}

	public static void init() {
		NetworkHandler.init();
		StationLoader.init();
		ModFluidProperties.FLUID_PROPERTIES.initialize();
		ModFluids.FLUIDS.init();
		ModBlocks.BLOCKS.init();
		ModItems.ITEMS.init();
		Tabs.TABS.init();
	}

}