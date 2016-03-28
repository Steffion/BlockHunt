package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;

public class PlayerItemDamageEvent implements Listener {
	private BlockHunt plugin;
	
	public PlayerItemDamageEvent() {
		plugin = BlockHunt.getPlugin();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerItemDamageEvent(org.bukkit.event.player.PlayerItemDamageEvent event) {
		Player player = event.getPlayer();
		if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
			event.setCancelled(true);
		}
	}
}
