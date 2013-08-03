package nl.Steffion.BlockHunt.Serializables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.Steffion.BlockHunt.Arena;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

@SerializableAs("Arena")
public class ArenaSerializable extends Arena implements
		ConfigurationSerializable {
	public ArenaSerializable (String arenaName, LocationSerializable pos1,
			LocationSerializable pos2, int maxPlayers, int minPlayers,
			int amountSeekersOnStart, int timeInLobbyUntilStart,
			int waitingTimeSeeker, int gameTime, List<Player> playersInArena) {
		super(arenaName, pos1, pos2, maxPlayers, minPlayers,
				amountSeekersOnStart, timeInLobbyUntilStart, waitingTimeSeeker,
				gameTime, playersInArena);
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
		return map;
	}

	public static ArenaSerializable deserialize(Map<String, Object> map) {
		LocationSerializable loc = new LocationSerializable(
				Bukkit.getWorld("world"), 0, 0, 0, 0, 0);
		return new ArenaSerializable((String) M.g(map, "arenaName",
				"UNKNOWN_NAME"), (LocationSerializable) M.g(map, "pos1", loc),
				(LocationSerializable) M.g(map, "pos2", loc), (Integer) M.g(
						map, "maxPlayers", 12), (Integer) M.g(map,
						"minPlayers", 3), (Integer) M.g(map,
						"amountSeekersOnStart", 1), (Integer) M.g(map,
						"timeInLobbyUntilStart", 90), (Integer) M.g(map,
						"waitingTimeSeeker", 20), (Integer) M.g(map,
						"gameTime", 200), new ArrayList<Player>());
	}
}
