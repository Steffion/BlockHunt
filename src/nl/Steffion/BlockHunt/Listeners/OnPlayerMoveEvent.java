package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.W;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMoveEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		for (Arena arena : W.arenaList) {
			if (arena.playersInArena.contains(player)) {
				if (arena.gameState == ArenaState.INGAME) {
					W.moveLoc.put(player, player.getLocation());
					double maxX = Math
							.max(arena.pos1.getX(), arena.pos2.getX());
					double minX = Math
							.min(arena.pos1.getX(), arena.pos2.getX());
					double maxY = Math
							.max(arena.pos1.getY(), arena.pos2.getY());
					double minY = Math
							.min(arena.pos1.getY(), arena.pos2.getY());
					double maxZ = Math
							.max(arena.pos1.getZ(), arena.pos2.getZ());
					double minZ = Math
							.min(arena.pos1.getZ(), arena.pos2.getZ());

					Location loc = player.getLocation();
					if (loc.getBlockX() > maxX) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, null);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(arena.hidersWarp);
					} else if (loc.getBlockX() < minX) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, null);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(arena.hidersWarp);
					} else if (loc.getBlockZ() > maxZ) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, null);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(arena.hidersWarp);
					} else if (loc.getBlockZ() < minZ) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, null);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(arena.hidersWarp);
					} else if (loc.getBlockY() > maxY) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, null);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(arena.hidersWarp);
					} else if (loc.getBlockY() < minY) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, null);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(arena.hidersWarp);
					}
				}
			}
		}
	}
}
