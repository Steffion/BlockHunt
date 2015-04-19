package nl.Steffion.BlockHunt;

import java.util.logging.Level;

import nl.Steffion.BlockHunt.util.Config;
import nl.Steffion.BlockHunt.util.Messager;

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
		Messager.sendConsoleMessage(Level.INFO, BlockHunt.messages,
				"onDisable.author", "author", BlockHunt.pdf.getAuthors().get(0));
	}

	@Override
	public void onEnable() {
		BlockHunt.plugin = this;
		BlockHunt.pdf = getDescription();

		BlockHunt.config = new Config("config.yml");
		BlockHunt.messages = new Config("messages.yml");

		Bukkit.getPluginManager().registerEvents(new Messager(), this);

		Messager.sendConsoleMessage(Level.INFO, BlockHunt.messages,
				"onEnable.author", "author", BlockHunt.pdf.getAuthors().get(0));
		Messager.sendConsoleMessage(Level.INFO, BlockHunt.messages,
				"onEnable.finished", "name", BlockHunt.pdf.getName(),
				"version", BlockHunt.pdf.getVersion());

	}
}