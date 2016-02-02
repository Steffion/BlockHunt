package nl.Steffion.BlockHunt.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;

public class BlockPlaceEvent implements Listener {

	private BlockHunt plugin;
	
	public BlockPlaceEvent() {
		plugin = BlockHunt.getPlugin();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
		Player player = event.getPlayer();

		if (plugin.getArenaHandler().getAllEditors().contains(player)) {
			ItemStack itemInHand = player.getItemInHand();
			Arena arena = plugin.getArenaHandler().getArena(player);
			
			if (itemInHand.getItemMeta().hasDisplayName()
					&& itemInHand.getItemMeta().getDisplayName().equals("§aHider's spawn")) {
				if (arena.getHidersSpawn() != null) {
					player.sendBlockChange(arena.getHidersSpawn(), Material.AIR, (byte) 0);
				}
				
				arena.setHidersSpawn(event.getBlock().getLocation());
				arena.save();
				event.setCancelled(true);
				return;
			}
			
			if (itemInHand.getItemMeta().hasDisplayName()
					&& itemInHand.getItemMeta().getDisplayName().equals("§2Lobby location")) {
				if (arena.getLobbyLocation() != null) {
					player.sendBlockChange(arena.getLobbyLocation(), Material.AIR, (byte) 0);
				}
				
				arena.setLobbyLocation(event.getBlock().getLocation());
				arena.save();
				event.setCancelled(true);
				return;
			}
			
			if (itemInHand.getItemMeta().hasDisplayName()
					&& itemInHand.getItemMeta().getDisplayName().equals("§eSeeker's spawn")) {
				if (arena.getSeekersSpawn() != null) {
					player.sendBlockChange(arena.getSeekersSpawn(), Material.AIR, (byte) 0);
				}
				
				arena.setSeekersSpawn(event.getBlock().getLocation());
				arena.save();
				event.setCancelled(true);
				return;
			}
		}
	}
}
