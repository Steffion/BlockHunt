package nl.Steffion.BlockHunt.Listeners;

import java.io.File;
import java.io.IOException;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.FileM;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements Listener {

	private BlockHunt plugin;

	public PlayerListener (BlockHunt plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
		final Player player = event.getPlayer();
		MessageM.broadcastMessage("-2", false);
		if (W.previewWorlds.contains(event.getFrom().getWorld().getName())) {
			MessageM.broadcastMessage("-1", false);
			if (!event.getFrom().getWorld().getName()
					.equals(event.getTo().getWorld().getName())) {
				MessageM.broadcastMessage("0", false);
				final String worldLoc = event.getFrom().getWorld().getName();
				boolean unload = true;

				for (Player pl : Bukkit.getOnlinePlayers()) {
					MessageM.broadcastMessage("1", false);
					if (pl.getLocation().getWorld().getName().equals(worldLoc)) {
						MessageM.broadcastMessage("2", false);
						if (!pl.equals(player)) {
							MessageM.broadcastMessage("3a", false);
							unload = false;
						} else {
							MessageM.broadcastMessage("3b", false);
						}
					}
				}

				MessageM.broadcastMessage("4", false);
				if (unload) {
					MessageM.broadcastMessage("5", false);
					Bukkit.getScheduler().runTaskLaterAsynchronously(
							this.plugin, new Runnable() {
								@Override
								public void run() {
									Bukkit.unloadWorld(
											Bukkit.getWorld(worldLoc), false);
									MessageM.sendFMessage(
											player,
											ConfigC.normal_previewWorldUnloaded,
											true);
								}
							}, 20);

					Bukkit.getScheduler().runTaskLaterAsynchronously(
							this.plugin, new Runnable() {
								@Override
								public void run() {
									try {
										FileM.delete(new File(worldLoc));
									} catch (IOException e) {
										e.printStackTrace();
									}

									MessageM.sendFMessage(player,
											ConfigC.normal_previewWorldDeleted,
											true);
								}

							}, 40);
					W.previewWorlds
							.remove(event.getFrom().getWorld().getName());
				}
			}
		}
	}
}
