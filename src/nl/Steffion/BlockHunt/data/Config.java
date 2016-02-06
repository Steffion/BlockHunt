package nl.Steffion.BlockHunt.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.Steffion.BlockHunt.BlockHunt;

public class Config {
	private HashMap<String, Object>	defaults;
	private File					file;
	private FileConfiguration		fileC;
	private ConfigurationSection	fileCS;
	private BlockHunt				plugin;
									
	/**
	 * Use this class to create an automated config file.
	 *
	 * @param fileName
	 *            Name of the file.
	 */
	public Config(String fileName) {
		plugin = BlockHunt.getPlugin();

		file = new File("plugins/BlockHunt", fileName + ".yml");
		fileC = new YamlConfiguration();
		checkFile();
		fileCS = fileC.getConfigurationSection("");
		load();
	}
	
	/**
	 * Use this class to create an automated config file.
	 *
	 * @param fileName
	 *            Name of the file.
	 * @param fileLocation
	 *            Sub-Location of the file.
	 */
	public Config(String fileName, String fileLocation) {
		plugin = BlockHunt.getPlugin();

		file = new File("plugins/BlockHunt" + "/" + fileLocation, fileName + ".yml");
		fileC = new YamlConfiguration();
		checkFile();
		fileCS = fileC.getConfigurationSection("");
		load();
	}
	
	/**
	 * Check if file exists, if not create one.
	 */
	private void checkFile() {
		if (!file.exists()) {
			file.getParentFile().mkdirs();

			try {
				file.createNewFile();
				plugin.getLogger().log(Level.INFO, "Missing config file has been created. (" + file.getName() + ")");
			} catch (IOException e) {
				plugin.handleExeption(e);
			}
		}
	}
	
	public Object get(String key) {
		if (!defaults.containsKey(key)) return "§c" + key + "§r";
		
		if (fileCS.contains(key) && (defaults.get(key).getClass() == fileCS.get(key).getClass()))
			return fileCS.get(key);
		else return defaults.get(key);
	}
	
	public ConfigurationSection getConfig() {
		return fileCS;
	}

	public HashMap<String, Object> getDefaults() {
		return defaults;
	}
	
	/**
	 * Load the file.
	 */
	public void load() {
		checkFile();
		
		if (file.exists()) {
			try {
				fileC.load(file);
			} catch (Exception e) {
				plugin.handleExeption(e);
			}
		}
	}
	
	/**
	 * Save the file.
	 */
	public void save() {
		try {
			fileC.save(file);
		} catch (Exception e) {
			plugin.handleExeption(e);
		}
	}
	
	public void setDefaults(HashMap<String, Object> defaults) {
		this.defaults = defaults;
	}
	
}
