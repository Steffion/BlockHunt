package nl.Steffion.BlockHunt.events;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.projectiles.ProjectileSource;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;
import nl.Steffion.BlockHunt.data.Arena.ArenaState;

public class EntityDamageByEntityEvent implements Listener {
	private BlockHunt plugin;
	
	public EntityDamageByEntityEvent() {
		plugin = BlockHunt.getPlugin();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntityEvent(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Player attacker = null;
			
			if (event.getDamager() instanceof Arrow) {
				ProjectileSource shooter = ((Arrow) event.getDamager()).getShooter();

				if (shooter instanceof Player) {
					attacker = (Player) shooter;
				}
			} else if (event.getDamager() instanceof Player) {
				attacker = (Player) event.getDamager();
			}
			
			if (attacker == null) return;
			
			if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
				Arena arena = plugin.getArenaHandler().getArena(player);

				event.setCancelled(true);
				
				if (arena.getHiders().contains(player) && arena.getHiders().contains(attacker)) return;
				if (arena.getSeekers().contains(player) && arena.getSeekers().contains(attacker)) return;
				if (arena.getState() != ArenaState.INGAME) return;

				event.setCancelled(false);
				
				if (arena.getHider(player) != null) {
					arena.getHider(player).revealHider();
				}
				
				player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 5, 0);

				if (event.getFinalDamage() >= player.getHealth()) {
					event.setCancelled(true);
					player.setHealth(player.getMaxHealth());

					for (Player arenaPlayer : arena.getPlayers()) {
						arenaPlayer.sendMessage(player.getName() + " was slain by " + attacker.getName());
					}
					
					if (!arena.getSeekers().contains(player)) {
						arena.removeHider(player);
						arena.addSeeker(player);
					}
					
					player.teleport(arena.getHidersSpawn());
				}
			}
		}
	}
}