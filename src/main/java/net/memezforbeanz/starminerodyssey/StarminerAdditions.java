package net.memezforbeanz.starminerodyssey;

import net.memezforbeanz.starminerodyssey.registry.*;
import net.memezforbeanz.starminerodyssey.registry.ModFluidProperties;
import org.slf4j.Logger;


import com.mojang.logging.LogUtils;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.utils.radio.StationLoader;

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