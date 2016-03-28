package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;

public class PlayerDropItemEvent implements Listener {
	private BlockHunt plugin;
	
	public PlayerDropItemEvent() {
		plugin = BlockHunt.getPlugin();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItemEvent(org.bukkit.event.player.PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		if (plugin.getArenaHandler().getAllEditors().contains(player)
				|| plugin.getArenaHandler().getAllPlayers().contains(player)) {
			event.setCancelled(true);
		}
	}
}
