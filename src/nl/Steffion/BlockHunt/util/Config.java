package nl.Steffion.BlockHunt.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import nl.Steffion.BlockHunt.BlockHunt;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	public FileConfiguration config = null;
	public File configFile = null;
	public String configName;
	public String location;
	public String path;

	public Config(final String configName) {
		path = BlockHunt.plugin.getDataFolder().toString();
		this.configName = configName;
		location = configName;

		initiateConfig();
	}

	public Config(final String path, final String configName) {
		this.path = path;
		this.configName = configName;
		location = path + "/" + configName;

		initiateConfig();
	}

	public void checkDefaults(final Boolean createdNew) {
		config = YamlConfiguration.loadConfiguration(configFile);

		Reader reader = null;
		try {
			reader = new InputStreamReader(
					BlockHunt.plugin.getResource(configName), "UTF8");
		} catch (final UnsupportedEncodingException e) {
			BlockHunt.plugin.getLogger().log(Level.SEVERE,
					"Unsupported Encoding Exception:", e);
		} catch (final Exception e) {
			BlockHunt.plugin.getLogger().log(Level.SEVERE, "Exception:", e);
		}

		if (reader != null) {
			final YamlConfiguration yamlConfiguration = YamlConfiguration
					.loadConfiguration(reader);
			boolean changed = false;
			if (createdNew) {
				for (final String key : yamlConfiguration.getKeys(true)) {
					config.set(key, yamlConfiguration.get(key));
					BlockHunt.plugin.getLogger().log(
							Level.INFO,
							"New file created: Added '" + key + "' to "
									+ location);
					changed = true;
				}
			} else {
				for (final String key : yamlConfiguration.getKeys(true)) {
					if (config.get(key) == null) {
						config.set(key, yamlConfiguration.get(key));
						BlockHunt.plugin.getLogger().log(
								Level.INFO,
								"Found missing setting: Added '" + key
								+ "' to " + location);
						changed = true;
					}
				}
			}

			if (changed) {
				saveConfig();
			}
		}
	}

	public FileConfiguration getConfig() {
		if (config == null) {
			loadConfig();
		}

		return config;
	}

	public void initiateConfig() {
		if (configFile == null) {
			configFile = new File(path, configName);
		}

		if (!configFile.exists()) {
			if (BlockHunt.plugin.getResource(configName) == null) {
				BlockHunt.plugin
				.getLogger()
						.log(Level.SEVERE,
								"The file "
										+ configName
										+ " is missing the the resource folder. Contact the developer for help");
				return;
			}

			BlockHunt.plugin.getLogger().log(Level.INFO,
					location + " has been created");
			checkDefaults(true);
		}

		loadConfig(true);
	}

	public void loadConfig() {
		loadConfig(false);
	}

	public void loadConfig(final Boolean firstLoad) {
		if (configFile == null) {
			configFile = new File(path, configName);
		}

		checkDefaults(false);

		if (firstLoad) {
			BlockHunt.plugin.getLogger().log(Level.INFO,
					location + " has been loaded");
		} else {
			BlockHunt.plugin.getLogger().log(Level.INFO,
					location + " has been reloaded");
		}
	}

	public void reloadConfig() {
		loadConfig(false);
	}

	public void saveConfig() {
		if ((config == null) || (configFile == null)) {
			return;
		}

		try {
			getConfig().save(configFile);
		} catch (final IOException e) {
			BlockHunt.plugin.getLogger().log(Level.SEVERE,
					location + " could not be saved:", e);
		}

		BlockHunt.plugin.getLogger().log(Level.INFO,
				configName + " has been saved");
	}
}