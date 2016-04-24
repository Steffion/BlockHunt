package nl.Steffion.BlockHunt.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.W;

public class OnEntityDamageEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamageEvent(EntityDamageEvent event) {
		Entity ent = event.getEntity();

		if (ent instanceof Player) {
			Player player = (Player) event.getEntity();
			for (Arena arena : W.arenaList) {
				if (arena.playersInArena.contains(player)) {
					if (!event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
