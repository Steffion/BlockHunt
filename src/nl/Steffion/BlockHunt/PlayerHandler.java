package nl.Steffion.BlockHunt;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.data.PlayerData;

public class PlayerHandler {
	private HashMap<UUID, PlayerData> playerData;
	
	public PlayerHandler() {
		playerData = new HashMap<UUID, PlayerData>();
	}
	
	public HashMap<UUID, PlayerData> getPlayerData() {
		return playerData;
	}
	
	public PlayerData getPlayerData(Player player) {
		return playerData.get(player.getUniqueId());
	}

	public void removePlayerData(Player player) {
		playerData.remove(player.getUniqueId());
	}
	
	public void storePlayerData(Player player) {
		PlayerData backupPlayer = new PlayerData();
		backupPlayer.store(player);
		playerData.put(player.getUniqueId(), backupPlayer);
	}
	
}
