package nl.Steffion.BlockHunt;

import java.util.logging.Level;

import nl.Steffion.BlockHunt.util.Config;
import nl.Steffion.BlockHunt.util.Locale;
import nl.Steffion.BlockHunt.util.Messenger;

import org.bukkit.Bukkit;
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
		Messenger.sendConsoleMessage(Level.INFO, BlockHunt.messages,
				"onDisable.author", "author", BlockHunt.pdf.getAuthors().get(0));
	}

	@Override
	public void onEnable() {
		BlockHunt.plugin = this;
		BlockHunt.pdf = getDescription();

		BlockHunt.config = new Config("config.yml");
		BlockHunt.messages = new Config("messages.yml");
		Locale.initiateLocaleSystem();

		Bukkit.getPluginManager().registerEvents(new Messenger(), this);

		Messenger.sendConsoleMessage(Level.INFO, BlockHunt.messages,
				"onEnable.author", "author", BlockHunt.pdf.getAuthors().get(0));
		Messenger.sendConsoleMessage(Level.INFO, BlockHunt.messages,
				"onEnable.finished", "name", BlockHunt.pdf.getName(),
				"version", BlockHunt.pdf.getVersion());
	}
}