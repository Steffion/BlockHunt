package nl.Steffion.BlockHunt.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import nl.Steffion.BlockHunt.BlockHunt;

public class Arena {
	private UUID		editor;
	private boolean		editorIsRenamingArena;
	private Location	hidersSpawn;
	private Location	lobbyLocation;
	private String		name;
	private List<UUID>	players;
	private BlockHunt	plugin;
	private Objective	scoreboard;
	private Location	seekersSpawn;
	private BukkitTask	thread;

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
		
		players = new ArrayList<UUID>();
		scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard().registerNewObjective("BlockHunt",
				"dummy");
	}

	public Arena(String name) {
		plugin = BlockHunt.getPlugin();

		this.name = name;
		
		players = new ArrayList<UUID>();
		scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard().registerNewObjective("BlockHunt",
				"dummy");
	}

	public void addPlayer(Player player) {
		players.add(player.getUniqueId());
		startThread();
	}

	public Player getEditor() {
		return plugin.getServer().getPlayer(editor);
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

	public List<Player> getPlayers() {

		List<Player> players = new ArrayList<Player>();

		for (UUID uuid : this.players) {
			players.add(plugin.getServer().getPlayer(uuid));
		}
		
		return players;
	}
	
	public Location getSeekersSpawn() {
		return seekersSpawn;
	}
	
	public boolean isEditorRenamingArena() {
		return editorIsRenamingArena;
	}

	public boolean isSetup() {
		if ((hidersSpawn == null) || (lobbyLocation == null) || (seekersSpawn == null)) return false;

		return true;
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
	
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
		player.setScoreboard(plugin.getServer().getScoreboardManager().getMainScoreboard());
	}

	public void resetEditor() {
		editor = null;
		editorIsRenamingArena = false;
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
	
	public void setEditor(Player editor) {
		this.editor = editor.getUniqueId();
	}

	public void setEditorRenamingArena(boolean editorRenamingArena) {
		editorIsRenamingArena = editorRenamingArena;
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

	public void startThread() {
		thread = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			
			@Override
			public void run() {
				if (players.isEmpty()) {
					stopThread();
				}

				plugin.getServer().broadcastMessage("THREAD RUNNING: " + name);
				updateScoreboard();
				
			}
			
		}, 0, 20);
	}
	
	public void stopThread() {
		plugin.getServer().getScheduler().cancelTask(thread.getTaskId());
	}

	protected void updateScoreboard() {
		scoreboard.setDisplaySlot(DisplaySlot.SIDEBAR);
		scoreboard.setDisplayName("§9§lBlockHunt");

		for (String entry : scoreboard.getScoreboard().getEntries()) {
			scoreboard.getScoreboard().resetScores(entry);
		}

		List<String> scoreboardEntries = new ArrayList<String>();

		scoreboardEntries.add("§lArena: §6" + name);
		scoreboardEntries
				.add("§lPlayers: §6" + players.size() + "/" + plugin.getPluginConfig().get("GENERAL_MAXPLAYERS"));
				
		if (players.size() < ((int) plugin.getPluginConfig().get("GENERAL_MINPLAYERS"))) {
			scoreboardEntries
					.add("§lMinimum required players: §6" + plugin.getPluginConfig().get("GENERAL_MINPLAYERS"));
			scoreboardEntries.add("§6§lWaiting for players...");
		}
		
		for (int i = 0; i < scoreboardEntries.size(); i++) {
			Score scoreboardEntry = scoreboard.getScore(scoreboardEntries.get(i));
			scoreboardEntry.setScore(scoreboardEntries.size() - i - 1);
		}
		
		for (Player player : getPlayers()) {
			player.setScoreboard(scoreboard.getScoreboard());
		}
	}
	
}
