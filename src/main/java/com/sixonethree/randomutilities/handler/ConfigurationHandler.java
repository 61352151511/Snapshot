package com.sixonethree.randomutilities.handler;

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.sixonethree.randomutilities.reference.Reference;

public class ConfigurationHandler {
	public static Configuration configuration;
	private static HashMap<String, Boolean> CommandConfig = new HashMap<>();
	
	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		String[] CommandNames = new String[] {"Afk", "Back", "Burn", "DelHome", "Depth", "EnderChest", "Extinguish", "Feed", "Hat", "Heal", "Home", "KillPlayer",
				"More", "Mute", "Ping", "PvP", "Repair", "SetBiome", "SetHome", "Suicide", "Tpa", "TpAccept", "TpDeny", "WhoIs"};
		for (String CommandName : CommandNames) {
			CommandConfig.put(CommandName, configuration.getBoolean(CommandName, "Commands", true, "Enable Command: " + CommandName));
		}
		
		if (configuration.hasChanged()) configuration.save();
	}
	
	public static HashMap<String, Boolean> getEnabledCommands() {
		return CommandConfig;
	}
}