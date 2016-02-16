package nl.Steffion.BlockHunt.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;
import nl.Steffion.BlockHunt.data.Hider;

public class PlayerInteractEvent implements Listener {
	private BlockHunt plugin;
	
	public PlayerInteractEvent() {
		plugin = BlockHunt.getPlugin();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (plugin.getArenaHandler().getAllEditors().contains(player)) {
			if ((player.getItemInHand() != null) && (player.getItemInHand().getType() != Material.AIR)) {
				ItemStack itemInHand = player.getItemInHand();
				Arena arena = plugin.getArenaHandler().getArena(player);
				
				if (itemInHand.getItemMeta().hasDisplayName()
						&& itemInHand.getItemMeta().getDisplayName().equals("§cExit editor mode")
						&& ((event.getAction() == Action.RIGHT_CLICK_AIR)
								|| (event.getAction() == Action.RIGHT_CLICK_BLOCK))) {
					Bukkit.dispatchCommand(player, "blockhunt leave");
					event.setCancelled(true);
					return;
				}
				
				if (itemInHand.getItemMeta().hasDisplayName()
						&& itemInHand.getItemMeta().getDisplayName().equals("§7Rename arena")
						&& ((event.getAction() == Action.RIGHT_CLICK_AIR)
								|| (event.getAction() == Action.RIGHT_CLICK_BLOCK))) {
					player.sendMessage("Enter the new name in chat (enter '-' to abort):");
					arena.setEditorRenamingArena(true);
					event.setCancelled(true);
					return;
				}
				
				if (itemInHand.getItemMeta().hasDisplayName()
						&& itemInHand.getItemMeta().getDisplayName().equals("§c§lDELETE ARENA IMMEDIATELY")
						&& ((event.getAction() == Action.RIGHT_CLICK_AIR)
								|| (event.getAction() == Action.RIGHT_CLICK_BLOCK))) {
					Bukkit.dispatchCommand(player, "blockhunt remove " + arena.getName());
					event.setCancelled(true);
					return;
				}
			}
		}
		
		if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
			if ((player.getItemInHand() != null) && (player.getItemInHand().getType() != Material.AIR)) {
				ItemStack itemInHand = player.getItemInHand();
				
				if (itemInHand.getItemMeta().hasDisplayName()
						&& itemInHand.getItemMeta().getDisplayName().equals("§cExit arena")
						&& ((event.getAction() == Action.RIGHT_CLICK_AIR)
								|| (event.getAction() == Action.RIGHT_CLICK_BLOCK))) {
					Bukkit.dispatchCommand(player, "blockhunt leave");
					event.setCancelled(true);
					return;
				}
				
				if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
					Arena arena = plugin.getArenaHandler().getArena(player);
					Location loc = event.getClickedBlock().getLocation();
					
					if (arena == null) return;
					
					for (Hider hider : arena.getHiders()) {
						Location hideLocation = hider.getHideLocation();
						
						if (hideLocation == null) return;
						
						if ((loc.getBlockX() == hideLocation.getBlockX())
								&& (loc.getBlockY() == hideLocation.getBlockY())
								&& (loc.getBlockZ() == hideLocation.getBlockZ())) {
							for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
								onlinePlayer.sendBlockChange(hider.getHideLocation(), Material.AIR, (byte) 0);
								onlinePlayer.showPlayer(hider.getPlayer());
							}
							
							hideLocation.getWorld().playSound(hideLocation, Sound.HURT_FLESH, 5, 0);
							hider.setSolidBlockTimer(0);
						}
					}
				}
			}
		}
	}
	
}
