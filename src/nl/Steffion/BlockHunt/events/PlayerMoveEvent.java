package nl.Steffion.BlockHunt.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;

public class PlayerMoveEvent implements Listener {
	private BlockHunt plugin;
	
	public PlayerMoveEvent() {
		plugin = BlockHunt.getPlugin();
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMoveEvent(org.bukkit.event.player.PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (plugin.getEditors().containsKey(player.getUniqueId())) {
			Arena arena = plugin.getEditors().get(player.getUniqueId());
			
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
	}

}
