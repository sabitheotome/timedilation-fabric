package me.sabitheotome.timedilation;

import net.fabricmc.api.ModInitializer;
import com.google.common.util.concurrent.AtomicDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main implements ModInitializer {

	public static final String MOD_ID = "timedilation";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final AtomicDouble TICKRATE_MULTIPLIER = new AtomicDouble(1D);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Time Dilation Mod");
		CommandRegistry.registerCommands();
	}
}