package nl.Steffion.BlockHunt;

import java.util.ArrayList;
import java.util.List;

import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Arena {
	public String arenaName;
	public LocationSerializable pos1;
	public LocationSerializable pos2;
	public int maxPlayers;
	public int minPlayers;
	public int amountSeekersOnStart;
	public int timeInLobbyUntilStart;
	public int waitingTimeSeeker;
	public int gameTime;
	public ArrayList<ItemStack> disguiseBlocks;
	public List<Player> playersInArena;
	public ArenaState gameState;
	public int timer;
	public List<Player> seekers;

	public Arena (String arenaName, LocationSerializable pos1,
			LocationSerializable pos2, int maxPlayers, int minPlayers,
			int amountSeekersOnStart, int timeInLobbyUntilStart,
			int waitingTimeSeeker, int gameTime,
			ArrayList<ItemStack> disguiseBlocks, List<Player> playersInArena,
			ArenaState gameState, int timer, List<Player> seekers) {
		this.arenaName = arenaName;
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.amountSeekersOnStart = amountSeekersOnStart;
		this.timeInLobbyUntilStart = timeInLobbyUntilStart;
		this.waitingTimeSeeker = waitingTimeSeeker;
		this.gameTime = gameTime;
		this.disguiseBlocks = disguiseBlocks;
		this.playersInArena = playersInArena;
		this.gameState = gameState;
		this.timer = timer;
		this.seekers = seekers;
	}

	public enum ArenaType {
		maxPlayers,
		minPlayers,
		amountSeekersOnStart,
		timeInLobbyUntilStart,
		waitingTimeSeeker,
		gameTime;
	}

	public enum ArenaState {
		WAITING, STARTING, INGAME, RESTARTING, DISABLED;
	}
}