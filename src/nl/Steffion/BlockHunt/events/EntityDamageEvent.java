package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;

public class EntityDamageEvent implements Listener {
	private BlockHunt plugin;
	
	public EntityDamageEvent() {
		plugin = BlockHunt.getPlugin();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageEvent(org.bukkit.event.entity.EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (plugin.getEditors().containsKey(player.getUniqueId())) {
				event.setCancelled(true);
			}
		}
	}
}
