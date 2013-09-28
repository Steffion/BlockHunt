package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.PermissionsC.Permissions;
import nl.Steffion.BlockHunt.SignsHandler;
import nl.Steffion.BlockHunt.SolidBlockHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PermissionsM;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OnPlayerInteractEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (PermissionsM.hasPerm(player, Permissions.create, false)) {
			ItemStack item = player.getItemInHand();
			if (item.getType() != Material.AIR) {
				if (item.getItemMeta().hasDisplayName()) {
					ItemMeta im = item.getItemMeta();
					if (im.getDisplayName().equals(
							MessageM.replaceAll((String) W.config
									.get(ConfigC.wandName)))) {
						Action action = event.getAction();
						if (event.hasBlock()) {
							LocationSerializable location = new LocationSerializable(
									event.getClickedBlock().getLocation());
							if (action.equals(Action.LEFT_CLICK_BLOCK)) {
								event.setCancelled(true);
								if (W.pos1.get(player) == null
										|| !W.pos1.get(player).equals(location)) {
									MessageM.sendFMessage(
											player,
											ConfigC.normal_wandSetPosition,
											"number-1",
											"pos-%N(%A" + location.getBlockX()
													+ "%N, %A"
													+ location.getBlockY()
													+ "%N, %A"
													+ location.getBlockZ()
													+ "%N)",
											"x-" + location.getBlockX(), "y-"
													+ location.getBlockY(),
											"z-" + location.getBlockZ());
									W.pos1.put(player, location);
								}
							} else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
								event.setCancelled(true);
								if (W.pos2.get(player) == null
										|| !W.pos2.get(player).equals(location)) {
									MessageM.sendFMessage(
											player,
											ConfigC.normal_wandSetPosition,
											"number-2",
											"pos-%N(%A" + location.getBlockX()
													+ "%N, %A"
													+ location.getBlockY()
													+ "%N, %A"
													+ location.getBlockZ()
													+ "%N)",
											"x-" + location.getBlockX(), "y-"
													+ location.getBlockY(),
											"z-" + location.getBlockZ());
									W.pos2.put(player, location);
								}
							}
						}
					}
				}
			}
		}

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock() != null) {
				if (event.getClickedBlock().getType()
						.equals(Material.SIGN_POST)
						|| event.getClickedBlock().getType()
								.equals(Material.WALL_SIGN)) {
					if (SignsHandler.isSign(new LocationSerializable(event
							.getClickedBlock().getLocation()))) {
						Sign sign = (Sign) event.getClickedBlock().getState();
						if (sign.getLine(1) != null) {
							if (sign.getLine(1)
									.equals(MessageM
											.replaceAll(W.config
													.getFile()
													.getStringList(
															ConfigC.sign_LEAVE.location)
													.get(1)))) {
								if (PermissionsM.hasPerm(player,
										Permissions.joinsign, true)) {
									ArenaHandler.playerLeaveArena(player, true,
											true);
								}
							} else {
								for (Arena arena : W.arenaList) {
									if (sign.getLines()[1]
											.contains(arena.arenaName)) {
										if (PermissionsM.hasPerm(player,
												Permissions.joinsign, true)) {
											ArenaHandler.playerJoinArena(
													player, arena.arenaName);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			for (Arena arena : W.arenaList) {
				if (arena.seekers.contains(player)) {
					for (Player pl : arena.playersInArena) {
						if (W.hiddenLoc.get(pl) != null) {
							Block pLoc = event.getClickedBlock();
							Block moveLocBlock = W.hiddenLoc.get(pl).getBlock();
							if (moveLocBlock.getX() == pLoc.getX()
									&& moveLocBlock.getY() == pLoc.getY()
									&& moveLocBlock.getZ() == pLoc.getZ()) {
								W.moveLoc.put(pl, new Location(pl.getWorld(),
										0, 0, 0));
								pl.getWorld().playSound(player.getLocation(),
										Sound.HURT_FLESH, 1, 1);
								SolidBlockHandler.makePlayerUnsolid(pl);
							}
						}
					}
				}
			}
		}

		for (Arena arena : W.arenaList) {
			if (arena.playersInArena.contains(player)
					&& (arena.gameState.equals(ArenaState.WAITING) || arena.gameState
							.equals(ArenaState.STARTING))) {
				event.setCancelled(true);
				ItemStack item = player.getInventory().getItemInHand();
				if (item.getType() != Material.AIR) {
					if (item.getItemMeta().getDisplayName() != null) {
						if (item.getItemMeta()
								.getDisplayName()
								.equals(MessageM.replaceAll((String) W.config
										.get(ConfigC.shop_blockChooserName)))) {
							Inventory blockChooser = Bukkit
									.createInventory(
											null,
											36,
											MessageM.replaceAll("\u00A7r"
													+ W.config
															.get(ConfigC.shop_blockChooserName)));
							if (arena.disguiseBlocks != null) {
								for (int i = arena.disguiseBlocks.size(); i > 0; i = i - 1) {
									blockChooser.setItem(i - 1,
											arena.disguiseBlocks.get(i - 1));
								}
							}

							player.openInventory(blockChooser);
						}
					}
				}
			}
		}
	}
}
