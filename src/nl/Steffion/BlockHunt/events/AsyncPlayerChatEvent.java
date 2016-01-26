package nl.Steffion.BlockHunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.data.Arena;

public class AsyncPlayerChatEvent implements Listener {
	private BlockHunt plugin;

	public AsyncPlayerChatEvent() {
		plugin = BlockHunt.getPlugin();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChatEvent(org.bukkit.event.player.AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (plugin.getEditorsRenamingArena().containsKey(player.getUniqueId())) {
			String newName = event.getMessage();
			Arena arena = plugin.getEditorsRenamingArena().get(player.getUniqueId());

			if (!newName.equals("-")) {
				plugin.getArenas().getConfig().set(arena.getName(), null);
				arena.setName(newName);
				arena.save();
				player.sendMessage("Arena renamed to '" + newName + "'!");
			} else {
				player.sendMessage("Arena was not renamed.");
			}
			
			plugin.getEditorsRenamingArena().remove(player.getUniqueId());
			event.setCancelled(true);
		}
	}
}
