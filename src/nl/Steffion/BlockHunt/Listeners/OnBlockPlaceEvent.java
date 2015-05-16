package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.W;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlockPlaceEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		// Early exit if no one is in any arena
		if (ArenaHandler.noPlayersInArenas()) return;

		Player player = event.getPlayer();
		for (Arena arena : W.arenaList) {
			if (arena.playersInArena.contains(player)) {
				event.setCancelled(true);
			}
		}
	}
}
