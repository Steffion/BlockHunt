package nl.Steffion.BlockHunt.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;
import nl.Steffion.BlockHunt.data.ArenaState;

public class EntityDamageByEntityEvent implements Listener {
	private BlockHunt plugin;
	
	public EntityDamageByEntityEvent() {
		plugin = BlockHunt.getPlugin();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntityEvent(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
		if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) {
			Player player = (Player) event.getEntity();
			Player attacker = (Player) event.getDamager();
			
			if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
				Arena arena = plugin.getArenaHandler().getArena(player);

				event.setCancelled(true);
				
				if (arena.getHiders().contains(player) && arena.getHiders().contains(attacker)) return;
				if (arena.getSeekers().contains(player) && arena.getSeekers().contains(attacker)) return;
				if (arena.getState() != ArenaState.INGAME) return;

				event.setCancelled(false);
				
				player.getLocation().getWorld().playSound(player.getLocation(), Sound.HURT_FLESH, 5, 0);

				if (event.getFinalDamage() >= player.getHealth()) {
					event.setCancelled(true);
					player.setHealth(player.getMaxHealth());

					for (Player arenaPlayer : arena.getPlayers()) {
						arenaPlayer.sendMessage(player.getName() + " was slain by " + attacker.getName());
					}
					
					
					
					arena.removeHider(player);
					arena.addSeeker(player);
				}
			}
		}
	}
	
}
