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
	public void onPlayerTeleportEvent(final PlayerTeleportEvent event) {
		final Player player = event.getPlayer();
		if (W.previewWorlds.contains(event.getFrom().getWorld().getName())) {
			if (!event.getFrom().getWorld().getName()
					.equals(event.getTo().getWorld().getName())) {
				final String worldLoc = event.getFrom().getWorld().getName();
				boolean unload = true;

				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (pl.getLocation().getWorld().getName().equals(worldLoc)) {
						if (!pl.equals(player)) {
							unload = false;
						}
					}
				}

				if (unload) {
					Bukkit.getScheduler().runTaskLaterAsynchronously(
							this.plugin, new Runnable() {
								@Override
								public void run() {
									Bukkit.unloadWorld(
											Bukkit.getWorld(worldLoc), true);
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
										String[] worldPlace = event.getFrom()
												.getWorld().getName()
												.split("/");
										String[] worldName = worldPlace[worldPlace.length - 1]
												.split("_");
										File destFolder = new File("plugins/"
												+ W.pluginName
												+ "/defaultArenas/"
												+ worldName[0]);
										if (destFolder.exists()) {
											FileM.delete(destFolder);
										}

										FileM.copyFolder(new File(worldLoc),
												destFolder);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}, 40);

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

							}, 60);
					W.previewWorlds
							.remove(event.getFrom().getWorld().getName());
				}
			}
		}
	}
}
