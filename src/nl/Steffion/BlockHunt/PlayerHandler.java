package nl.Steffion.BlockHunt;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.data.PlayerData;

/**
 * This class combines an UUID with the PlayerData.
 * 
 * @author Steffion (Stef de Goey) 2016
 *
 */
public class PlayerHandler {
	private HashMap<UUID, PlayerData> playerData;
	
	PlayerHandler() {
		playerData = new HashMap<UUID, PlayerData>();
	}
	
	/**
	 * 
	 * @param player - The player whose PlayerData you want to get.
	 * @return The stored PlayerData of a specific player.
	 */
	public PlayerData getPlayerData(Player player) {
		return playerData.get(player.getUniqueId());
	}

	/**
	 * Removes the stored PlayerData.
	 * @param player - The player you want to remove.
	 */
	public void removePlayerData(Player player) {
		playerData.remove(player.getUniqueId());
	}
	
	/**
	 * Stores PlayerData.
	 * @param player - The player you want to store.
	 */
	public void storePlayerData(Player player) {
		PlayerData playerData = new PlayerData();
		playerData.store(player);
		this.playerData.put(player.getUniqueId(), playerData);
	}
	
}
