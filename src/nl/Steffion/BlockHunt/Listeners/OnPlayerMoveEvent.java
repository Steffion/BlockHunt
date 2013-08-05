package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Arena.ArenaState;

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
					W.pLocation.put(player, player.getLocation());
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
					if (loc.getBlockX() > maxX || loc.getBlockX() < minX) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, 0);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(loc.add(0, 2, 0));
					} else if (loc.getBlockZ() > maxZ || loc.getBlockZ() < minZ) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, 0);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(loc.add(0, 2, 0));
					} else if (loc.getBlockY() > maxY) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, 0);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(loc.subtract(0, 1, 0));
					} else if (loc.getBlockY() < minY) {
						event.setCancelled(true);
						player.playEffect(loc, Effect.ENDER_SIGNAL, 0);
						player.playSound(loc, Sound.GHAST_FIREBALL, 1, 1);
						player.teleport(loc.add(0, 5, 0));
					}

					// Location pLocation = W.pLocation.get(player);
					// ItemStack block = player.getInventory().getItem(8);
					//
					// if (pLocation.getX() == pLoc.getX()
					// && pLocation.getY() == pLoc.getY()
					// && pLocation.getZ() == pLoc.getZ()) {
					// if (block.getAmount() > 1) {
					// block.setAmount(block.getAmount() - 1);
					// } else {
					// Disguise dis = W.dcAPI.getDisguise(player);
					// dis.addSingleData("blocklock");
					// W.dcAPI.changePlayerDisguise(player, dis);
					// block.addEnchantment(Enchantment.DURABILITY, 10);
					// player.playSound(pLoc, Sound.ORB_PICKUP, 1, 1);
					// }
					// } else {
					// Disguise dis = W.dcAPI.getDisguise(player);
					// dis.data.remove("blocklock");
					// W.dcAPI.changePlayerDisguise(player, dis);
					// player.playSound(pLoc, Sound.BAT_HURT, 1, 1);
					// block.removeEnchantment(Enchantment.DURABILITY);
					// block.setAmount(5);
					// }
				}
			}
		}
	}
}
