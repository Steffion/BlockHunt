package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.W;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class OnEntityDamageEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamageEvent(EntityDamageEvent event) {
		// Early exit if no one is in any arena
		if (ArenaHandler.noPlayersInArenas()) return;

		Entity ent = event.getEntity();
		if (ent instanceof Player) {
			Player player = (Player) event.getEntity();
			for (Arena arena : W.arenaList) {
				if (arena.playersInArena.contains(player)) {
					DamageCause cause = event.getCause();
					switch (cause) {
						case ENTITY_ATTACK:
							// Do nothing about damage from an entity
							// Any entity damage that makes it to here was already allowed by the EntityDamageByEntity event
							break;
						case FALL:
							// Should we prevent the fall damage?
							if (arena.seekers.contains(player)) {
								if (!arena.seekersTakeFallDamage) {
									// Prevent seeker fall damage (if configured)
									event.setCancelled(true);
									return;
								}
							} else {
								if (!arena.hidersTakeFallDamage) {
									// Prevent hider fall damage (if configured)
									event.setCancelled(true);
									return;
								}
							}
							break;
						default:
							// Cancel all non-entity damage for all players (lava, drowning, fire, etc)
							event.setCancelled(true);
							break;
					}
					return;
				}
			}
		}
	}
}