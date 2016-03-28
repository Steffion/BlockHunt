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

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChatEvent(org.bukkit.event.player.AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (plugin.getArenaHandler().getAllEditors().contains(player)) {
			Arena arena = plugin.getArenaHandler().getArena(player);
			String newName = event.getMessage();
			
			if (!arena.isEditorRenamingArena()) return;
			
			if (!newName.equals("-")) {
				plugin.getArenas().getConfig().set(arena.getName(), null);
				arena.setName(newName);
				arena.save();
				player.sendMessage("Arena renamed to '" + newName + "'!");
			} else {
				player.sendMessage("Arena was not renamed.");
			}
			
			arena.setEditorRenamingArena(false);
			event.setCancelled(true);
		}
	}
}
