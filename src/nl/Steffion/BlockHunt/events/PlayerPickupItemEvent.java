package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;

public class PlayerPickupItemEvent implements Listener {
	private BlockHunt plugin;
	
	public PlayerPickupItemEvent() {
		plugin = BlockHunt.getPlugin();

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItemEvent(org.bukkit.event.player.PlayerPickupItemEvent event) {
		Player player = event.getPlayer();

		if (plugin.getArenaHandler().getAllEditors().contains(player)
				|| plugin.getArenaHandler().getAllPlayers().contains(player)) {
			event.setCancelled(true);
		}
	}
}
