package nl.Steffion.BlockHunt.Managers;

import java.io.File;

import nl.Steffion.BlockHunt.W;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigM {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	String fileName;
	File file;
	FileConfiguration fileC;
	ConfigurationSection fileCS;
	String location;

	public ConfigM (String fileName, String location) {
		this.fileName = fileName;
		this.file = new File("plugins/" + W.pluginName + "/" + location, fileName
				+ ".yml");
		this.location = W.pluginName + "/" + location;
		
		this.fileC = new YamlConfiguration();
		this.checkFile();
		this.fileCS = fileC.getConfigurationSection("");
		this.load();
	}

	/**
	 * Check if file exists, if not create one.
	 */
	public void checkFile() {
		if (!this.file.exists()) {
			try {
				this.file.getParentFile().mkdirs();
				this.file.createNewFile();
				W.newFiles.add(this.location + this.fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Save the file.
	 */
	public void save() {
		try {
			this.fileC.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the file.
	 */
	public void load() {
		this.checkFile();
		if (this.file.exists()) {
			try {
				this.fileC.load(this.file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add variables to the files if they don't exist.
	 */
	public static void setDefaults() {
		for (ConfigC value : ConfigC.values()) {
			value.config.load();
			String location = value.getLocation();
			if (value.config.getFile().get(location) == null) {
				value.config.getFile().set(location, value.value);
				value.config.save();
			}
		}
	}

	/**
	 * Get the File.
	 */
	public FileConfiguration getFile() {
		return this.fileC;
	}
	
	/**
	 * Get object from a Config.
	 * @param location
	 * @return
	 */
	public Object get(ConfigC location) {
		return this.getFile().get(location.getLocation());
	}
}