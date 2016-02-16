package nl.Steffion.BlockHunt.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;
import nl.Steffion.BlockHunt.data.Hider;

public class PlayerMoveEvent implements Listener {
	private BlockHunt plugin;

	public PlayerMoveEvent() {
		plugin = BlockHunt.getPlugin();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMoveEvent(org.bukkit.event.player.PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (plugin.getArenaHandler().getAllEditors().contains(player)) {
			Arena arena = plugin.getArenaHandler().getArena(player);

			if (arena.getHidersSpawn() != null) {
				player.sendBlockChange(arena.getHidersSpawn(), Material.WOOL, (byte) 5);
			}

			if (arena.getLobbyLocation() != null) {
				player.sendBlockChange(arena.getLobbyLocation(), Material.WOOL, (byte) 13);
			}

			if (arena.getSeekersSpawn() != null) {
				player.sendBlockChange(arena.getSeekersSpawn(), Material.WOOL, (byte) 4);
			}

			int tasksComplete = 0;

			if (arena.getHidersSpawn() != null) {
				tasksComplete++;
			}
			
			if (arena.getLobbyLocation() != null) {
				tasksComplete++;
			}

			if (arena.getSeekersSpawn() != null) {
				tasksComplete++;
			}

			player.setExp((float) (((double) tasksComplete / 3) - 0.01));
			return;
		}

		if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
			Arena arena = plugin.getArenaHandler().getArena(player);

			if (arena.getHider(player)==null) return;

			Location from = event.getFrom();
			Location to = event.getTo();
			
			if ((to.getBlockX() != from.getBlockX()) || (to.getBlockY() != from.getBlockY())
					|| (to.getBlockZ() != from.getBlockZ())) {
				Hider hider = arena.getHider(player);
				
				if (hider.getSolidBlockTimer() >= 3) {
					for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
						onlinePlayer.sendBlockChange(hider.getHideLocation(), Material.AIR, (byte) 0);
						onlinePlayer.showPlayer(player);
					}
				}
				
				hider.setSolidBlockTimer(0);
			}
		}
	}
	
}
