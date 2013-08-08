package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.W;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class OnPlayerRespawnEvent implements Listener {

	public static BlockHunt plugin;

	public OnPlayerRespawnEvent (BlockHunt plugin) {
		OnPlayerRespawnEvent.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerRespawnEvent(final PlayerRespawnEvent event) {
		final Player player = event.getPlayer();

		for (Arena arena : W.arenaList) {
			if (arena.seekers.contains(player)) {
				if (arena.seekers.size() >= arena.playersInArena.size()) {
					event.setRespawnLocation(W.pLocation.get(player));
					ArenaHandler.seekersWin(arena);
				} else {
					W.dcAPI.undisguisePlayer(player);
					W.seekertime.put(player, arena.waitingTimeSeeker);
					event.setRespawnLocation(arena.seekersWarp);
					player.setGameMode(GameMode.ADVENTURE);
				}
			}
		}
	}
}
