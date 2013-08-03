package nl.Steffion.BlockHunt;

import java.util.List;

import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.entity.Player;

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
	public List<Player> playersInArena;

	public Arena (String arenaName, LocationSerializable pos1,
			LocationSerializable pos2, int maxPlayers, int minPlayers,
			int amountSeekersOnStart, int timeInLobbyUntilStart,
			int waitingTimeSeeker, int gameTime, List<Player> playersInArena) {
		this.arenaName = arenaName;
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
		this.amountSeekersOnStart = amountSeekersOnStart;
		this.timeInLobbyUntilStart = timeInLobbyUntilStart;
		this.waitingTimeSeeker = waitingTimeSeeker;
		this.gameTime = gameTime;
		this.playersInArena = playersInArena;
	}

	public enum ArenaType {
		maxPlayers,
		minPlayers,
		amountSeekersOnStart,
		timeInLobbyUntilStart,
		waitingTimeSeeker,
		gameTime;
	}
}