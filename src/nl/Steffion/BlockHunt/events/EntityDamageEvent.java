package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;
import nl.Steffion.BlockHunt.data.Arena.ArenaState;

public class EntityDamageEvent implements Listener {
	private BlockHunt plugin;
	
	public EntityDamageEvent() {
		plugin = BlockHunt.getPlugin();

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageEvent(org.bukkit.event.entity.EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (plugin.getArenaHandler().getAllEditors().contains(player)) {
				event.setCancelled(true);
			}
			
			if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
				Arena arena = plugin.getArenaHandler().getArena(player);
				
				if (arena.getState() != ArenaState.INGAME) {
					event.setCancelled(true);
				}
			}
		}
	}
}
