package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;

public class PlayerQuitEvent implements Listener {
	private BlockHunt plugin;

	public PlayerQuitEvent() {
		plugin = BlockHunt.getPlugin();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuitEvent(org.bukkit.event.player.PlayerQuitEvent event) {
		Player player = event.getPlayer();

		if (plugin.getArenaHandler().getArena(player) != null) {
			Arena arena = plugin.getArenaHandler().getArena(player);
			arena.removePlayer(player);
		}
	}

}
