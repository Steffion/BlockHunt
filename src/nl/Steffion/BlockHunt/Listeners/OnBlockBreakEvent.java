package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.PermissionsC.Permissions;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.PermissionsM;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreakEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();

		for (Arena arena : W.arenaList) {
			if (arena.playersInArena.contains(player)) {
				event.setCancelled(true);
			}
		}

		if (event.getBlock().equals(Material.SIGN_POST)
				|| event.getBlock().equals(Material.WALL_SIGN)) {
			if (!PermissionsM.hasPerm(player, Permissions.signcreate, true)) {
				event.setCancelled(true);
			}
		}
	}
}
