package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;

public class FoodLevelChangeEvent implements Listener {
	private BlockHunt plugin;

	public FoodLevelChangeEvent() {
		plugin = BlockHunt.getPlugin();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItemEvent(org.bukkit.event.entity.FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if (plugin.getArenaHandler().getAllPlayers().contains(player)
					|| plugin.getArenaHandler().getAllEditors().contains(player)) {
				event.setCancelled(true);
			}
		}
	}
}
