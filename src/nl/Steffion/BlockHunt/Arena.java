package nl.Steffion.BlockHunt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.Steffion.BlockHunt.Serializables.LocationSerializable;
import nl.Steffion.BlockHunt.Serializables.M;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

@SerializableAs("BlockHuntArena")
public class Arena implements ConfigurationSerializable {
	public String arenaName;
	public LocationSerializable pos1;
	public LocationSerializable pos2;
	public int maxPlayers;
	public int minPlayers;
	public int amountSeekersOnStart;
	public int timeInLobbyUntilStart;
	public int waitingTimeSeeker;
	public int gameTime;
	public int timeUntilHidersSword;
	public ArrayList<ItemStack> disguiseBlocks;
	public LocationSerializable lobbyWarp;
	public LocationSerializable hidersWarp;
	public LocationSerializable seekersWarp;
	public List<String> seekersWinCommands;
	public List<String> hidersWinCommands;
	public List<String> allowedCommands;
	public int seekersTokenWin;
	public int hidersTokenWin;
	public int killTokens;

	public List<Player> playersInArena;
	public ArenaState gameState;
	public int timer;
	public List<Player> seekers;
	public Scoreboard scoreboard;

	public Arena (String arenaName, LocationSerializable pos1,
			LocationSerializable pos2, int maxPlayers, int minPlayers,
			int amountSeekersOnStart, int timeInLobbyUntilStart,
			int waitingTimeSeeker, int gameTime, int timeUntilHidersSword,
			ArrayList<ItemStack> disguiseBlocks,
			LocationSerializable lobbyWarp, LocationSerializable hidersWarp,
			LocationSerializable seekersWarp, List<String> seekersWinCommands,
			List<String> hidersWinCommands, List<String> allowedCommands,
			int seekersTokenWin, int hidersTokenWin, int killTokens,
			List<Player> playersInArena, ArenaState gameState, int timer,
			List<Player> seekers, Scoreboard scoreboard) {
		this.arenaName = arenaName;
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.amountSeekersOnStart = amountSeekersOnStart;
		this.timeInLobbyUntilStart = timeInLobbyUntilStart;
		this.waitingTimeSeeker = waitingTimeSeeker;
		this.gameTime = gameTime;
		this.timeUntilHidersSword = timeUntilHidersSword;
		this.disguiseBlocks = disguiseBlocks;
		this.lobbyWarp = lobbyWarp;
		this.hidersWarp = hidersWarp;
		this.seekersWarp = seekersWarp;
		this.seekersWinCommands = seekersWinCommands;
		this.hidersWinCommands = hidersWinCommands;
		this.allowedCommands = allowedCommands;
		this.seekersTokenWin = seekersTokenWin;
		this.hidersTokenWin = hidersTokenWin;
		this.killTokens = killTokens;

		this.playersInArena = playersInArena;
		this.gameState = gameState;
		this.timer = timer;
		this.seekers = seekers;
		this.scoreboard = scoreboard;
	}

	public enum ArenaType {
		maxPlayers,
		minPlayers,
		amountSeekersOnStart,
		timeInLobbyUntilStart,
		waitingTimeSeeker,
		gameTime,
		timeUntilHidersSword,
		hidersTokenWin,
		seekersTokenWin,
		killTokens;
	}

	public enum ArenaState {
		WAITING, STARTING, INGAME, RESTARTING, DISABLED;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arenaName", arenaName);
		map.put("pos1", pos1);
		map.put("pos2", pos2);
		map.put("maxPlayers", maxPlayers);
		map.put("minPlayers", minPlayers);
		map.put("amountSeekersOnStart", amountSeekersOnStart);
		map.put("timeInLobbyUntilStart", timeInLobbyUntilStart);
		map.put("waitingTimeSeeker", waitingTimeSeeker);
		map.put("gameTime", gameTime);
		map.put("timeUntilHidersSword", timeUntilHidersSword);
		map.put("disguiseBlocks", disguiseBlocks);
		map.put("lobbyWarp", lobbyWarp);
		map.put("hidersWarp", hidersWarp);
		map.put("seekersWarp", seekersWarp);
		map.put("seekersWinCommands", seekersWinCommands);
		map.put("hidersWinCommands", hidersWinCommands);
		map.put("allowedCommands", allowedCommands);
		map.put("seekersTokenWin", seekersTokenWin);
		map.put("hidersTokenWin", hidersTokenWin);
		map.put("killTokens", killTokens);
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Arena deserialize(Map<String, Object> map) {
		LocationSerializable loc = new LocationSerializable(
				Bukkit.getWorld("world"), 0, 0, 0, 0, 0);
		return new Arena((String) M.g(map, "arenaName", "UNKNOWN_NAME"),
				(LocationSerializable) M.g(map, "pos1", loc),
				(LocationSerializable) M.g(map, "pos2", loc), (Integer) M.g(
						map, "maxPlayers", 12), (Integer) M.g(map,
						"minPlayers", 3), (Integer) M.g(map,
						"amountSeekersOnStart", 1), (Integer) M.g(map,
						"timeInLobbyUntilStart", 90), (Integer) M.g(map,
						"waitingTimeSeeker", 20), (Integer) M.g(map,
						"gameTime", 200), (Integer) M.g(map,
						"timeUntilHidersSword", 30),
				(ArrayList<ItemStack>) M.g(map, "disguiseBlocks",
						new ArrayList<ItemStack>()),
				(LocationSerializable) M.g(map, "lobbyWarp", loc),
				(LocationSerializable) M.g(map, "hidersWarp", loc),
				(LocationSerializable) M.g(map, "seekersWarp", loc),
				(ArrayList<String>) M.g(map, "seekersWinCommands",
						new ArrayList<String>()), (ArrayList<String>) M.g(map,
						"hidersWinCommands", new ArrayList<String>()),
				(ArrayList<String>) M.g(map, "allowedCommands",
						new ArrayList<String>()), (Integer) M.g(map,
						"seekersTokenWin", 10), (Integer) M.g(map,
						"hidersTokenWin", 50), (Integer) M.g(map, "killTokens",
						8), new ArrayList<Player>(), ArenaState.WAITING, 0,
				new ArrayList<Player>(), Bukkit.getScoreboardManager()
						.getNewScoreboard());
	}
}