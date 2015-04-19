package nl.Steffion.BlockHunt;

import nl.Steffion.BlockHunt.utils.Config;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockHunt extends JavaPlugin {
	public static Config config;
	public static Config locale;
	public static Config messages;
	public static PluginDescriptionFile pdf;
	public static BlockHunt plugin;

	@Override
	public void onDisable() {

	}

	@Override
	public void onEnable() {
		BlockHunt.plugin = this;
		BlockHunt.pdf = getDescription();

		BlockHunt.locale = new Config(this, "plugins/Steffion", "locale.yml");
		BlockHunt.config = new Config("config.yml");
		BlockHunt.messages = new Config("messages.yml");
	}
}