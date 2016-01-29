package nl.Steffion.BlockHunt.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import nl.Steffion.BlockHunt.BlockHunt;

public class Arena {
	private Location	hidersSpawn;
	private Location	lobbyLocation;
	private String		name;
	private BlockHunt	plugin;
	private Location	seekersSpawn;

	public Arena() {
		plugin = BlockHunt.getPlugin();
		
		int arenaNumber = 1;

		while (true) {
			if (plugin.getArenas().getConfig().contains("Arena_" + arenaNumber)) {
				arenaNumber++;
				continue;
			}

			name = "Arena_" + arenaNumber;
			break;
		}
	}

	public Arena(String name) {
		plugin = BlockHunt.getPlugin();

		this.name = name;
		load();
	}
	
	public Location getHidersSpawn() {
		return hidersSpawn;
	}

	public Location getLobbyLocation() {
		return lobbyLocation;
	}
	
	public String getName() {
		return name;
	}

	public Location getSeekersSpawn() {
		return seekersSpawn;
	}
	
	public void load() {
		plugin.getArenas().load();
		ConfigurationSection arenas = plugin.getArenas().getConfig();

		if (arenas.getString(name + ".hidersSpawn.world") != null) {
			hidersSpawn = new Location(Bukkit.getWorld(arenas.getString(name + ".hidersSpawn.world")),
					arenas.getDouble(name + ".hidersSpawn.x"), arenas.getDouble(name + ".hidersSpawn.y"),
					arenas.getDouble(name + ".hidersSpawn.z"));
		}
		
		if (arenas.getString(name + ".lobbyLocation.world") != null) {
			lobbyLocation = new Location(Bukkit.getWorld(arenas.getString(name + ".lobbyLocation.world")),
					arenas.getDouble(name + ".lobbyLocation.x"), arenas.getDouble(name + ".lobbyLocation.y"),
					arenas.getDouble(name + ".lobbyLocation.z"));
		}
		
		if (arenas.getString(name + ".seekersSpawn.world") != null) {
			seekersSpawn = new Location(Bukkit.getWorld(arenas.getString(name + ".seekersSpawn.world")),
					arenas.getDouble(name + ".seekersSpawn.x"), arenas.getDouble(name + ".seekersSpawn.y"),
					arenas.getDouble(name + ".seekersSpawn.z"));
		}
	}

	public void save() {
		plugin.getArenas().getConfig().set(name, "");

		if (hidersSpawn != null) {
			plugin.getArenas().getConfig().set(name + ".hidersSpawn.world", hidersSpawn.getWorld().getName());
			plugin.getArenas().getConfig().set(name + ".hidersSpawn.x", hidersSpawn.getBlockX());
			plugin.getArenas().getConfig().set(name + ".hidersSpawn.y", hidersSpawn.getBlockY());
			plugin.getArenas().getConfig().set(name + ".hidersSpawn.z", hidersSpawn.getBlockZ());
		}
		
		if (lobbyLocation != null) {
			plugin.getArenas().getConfig().set(name + ".lobbyLocation.world", lobbyLocation.getWorld().getName());
			plugin.getArenas().getConfig().set(name + ".lobbyLocation.x", lobbyLocation.getBlockX());
			plugin.getArenas().getConfig().set(name + ".lobbyLocation.y", lobbyLocation.getBlockY());
			plugin.getArenas().getConfig().set(name + ".lobbyLocation.z", lobbyLocation.getBlockZ());
		}
		
		if (seekersSpawn != null) {
			plugin.getArenas().getConfig().set(name + ".seekersSpawn.world", seekersSpawn.getWorld().getName());
			plugin.getArenas().getConfig().set(name + ".seekersSpawn.x", seekersSpawn.getBlockX());
			plugin.getArenas().getConfig().set(name + ".seekersSpawn.y", seekersSpawn.getBlockY());
			plugin.getArenas().getConfig().set(name + ".seekersSpawn.z", seekersSpawn.getBlockZ());
		}
		
		plugin.getArenas().save();
	}

	public void setHidersSpawn(Location hidersSpawn) {
		this.hidersSpawn = hidersSpawn;
	}
	
	public void setLobbyLocation(Location lobbyLocation) {
		this.lobbyLocation = lobbyLocation;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSeekersSpawn(Location seekersSpawn) {
		this.seekersSpawn = seekersSpawn;
	}
}
