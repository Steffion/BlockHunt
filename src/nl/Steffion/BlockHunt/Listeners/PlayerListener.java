package nl.Steffion.BlockHunt.Listeners;

import org.bukkit.event.Listener;

public class PlayerListener implements Listener {

	// private BlockHunt plugin;
	//
	// public PlayerListener (BlockHunt plugin) {
	// this.plugin = plugin;
	// }

	// @EventHandler(priority = EventPriority.MONITOR)
	// public void onPlayerTeleportEvent(final PlayerTeleportEvent event) {
	// final Player player = event.getPlayer();
	// if (W.previewWorlds.contains(event.getFrom().getWorld().getName())) {
	// if (!event.getFrom().getWorld().getName()
	// .equals(event.getTo().getWorld().getName())) {
	// final String worldLoc = event.getFrom().getWorld().getName();
	// boolean unload = true;
	//
	// for (Player pl : Bukkit.getOnlinePlayers()) {
	// if (pl.getLocation().getWorld().getName().equals(worldLoc)) {
	// if (!pl.equals(player)) {
	// unload = false;
	// }
	// }
	// }
	//
	// if (unload) {
	// ArenaHandler ah = new ArenaHandler(this.plugin);
	// ah.unLoadArena(worldLoc, player);
	// W.previewWorlds.remove(worldLoc);
	// }
	// }
	// }
	// }
}
