package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;

public class BlockBreakEvent implements Listener {
	private BlockHunt plugin;
	
	public BlockBreakEvent() {
		plugin = BlockHunt.getPlugin();

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
			event.setCancelled(true);
		}
	}
}
