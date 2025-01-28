package net.memezforbeanz.starmineroddysey;

import net.fabricmc.api.ModInitializer;

import net.memezforbeanz.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarminerAdditions implements ModInitializer {
	public static final String MOD_ID = "starminer-additions";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("This truly has been a star miner odyssey...");
		ModItems.registerModItems();
	}
}