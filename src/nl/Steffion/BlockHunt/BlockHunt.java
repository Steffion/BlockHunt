package nl.Steffion.BlockHunt;

import java.util.LinkedList;

import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.Listeners.OnInventoryClickEvent;
import nl.Steffion.BlockHunt.Listeners.OnInventoryCloseEvent;
import nl.Steffion.BlockHunt.Listeners.OnPlayerDropItemEvent;
import nl.Steffion.BlockHunt.Listeners.OnPlayerInteractEvent;
import nl.Steffion.BlockHunt.Listeners.OnPlayerMoveEvent;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Serializables.ArenaSerializable;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

public class BlockHunt extends JavaPlugin implements Listener {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(
				new OnPlayerInteractEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnInventoryClickEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnInventoryCloseEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnPlayerDropItemEvent(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerMoveEvent(),
				this);

		ConfigurationSerialization.registerClass(LocationSerializable.class,
				"Location");
		ConfigurationSerialization.registerClass(ArenaSerializable.class,
				"Arena");

		W.newFiles();

		ArenaHandler.loadArenas();

		W.dcAPI = DisguiseCraft.getAPI();

		MessageM.sendFMessage(null, ConfigC.log_Enabled, true, "name-"
				+ W.pluginName, "version-" + W.pluginVersion, "autors-"
				+ W.pluginAutors);

		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				for (Arena arena : W.arenaList) {
					if (arena.gameState == ArenaState.WAITING) {
						if (arena.playersInArena.size() >= arena.minPlayers) {
							arena.gameState = ArenaState.STARTING;
							arena.timer = arena.timeInLobbyUntilStart;
							ArenaHandler.sendFMessage(arena,
									ConfigC.normal_lobbyArenaIsStarting, true,
									"1-" + arena.timeInLobbyUntilStart);
						}
					} else if (arena.gameState == ArenaState.STARTING) {
						if (arena.timer > 0) {
							arena.timer = arena.timer - 1;
							if (arena.timer == 60) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										true, "1-60");
							} else if (arena.timer == 30) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										true, "1-30");
							} else if (arena.timer == 10) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										true, "1-10");
							} else if (arena.timer == 5) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										true, "1-5");
							} else if (arena.timer == 4) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										true, "1-4");
							} else if (arena.timer == 3) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										true, "1-3");
							} else if (arena.timer == 2) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										true, "1-2");
							} else if (arena.timer == 1) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										true, "1-1");
							}
						} else {
							arena.gameState = ArenaState.INGAME;
							arena.timer = arena.gameTime;
							ArenaHandler.sendFMessage(arena,
									ConfigC.normal_lobbyArenaStarted, true);

							for (int i = arena.amountSeekersOnStart; i > 0; i = i - 1) {
								Player seeker = arena.playersInArena
										.get(W.random
												.nextInt(arena.playersInArena
														.size()));
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameSeekerChoosen,
										true, "seeker-" + seeker.getName());
								arena.seekers.add(seeker);
								seeker.teleport(arena.seekersWarp);
							}

							for (Player arenaPlayer : arena.playersInArena) {
								if (!arena.seekers.contains(arenaPlayer)) {
									ItemStack block = arena.disguiseBlocks.get(W.random
											.nextInt(arena.disguiseBlocks
													.size()));
									LinkedList<String> data = new LinkedList<String>();
									data.add("blockID:" + block.getTypeId());
									data.add("blockData:"
											+ block.getDurability());
									Disguise disguise = new Disguise(W.dcAPI
											.newEntityID(), data,
											DisguiseType.FallingBlock);
									if (W.dcAPI.isDisguised(arenaPlayer)) {
										W.dcAPI.changePlayerDisguise(
												arenaPlayer, disguise);
									} else {
										W.dcAPI.disguisePlayer(arenaPlayer,
												disguise);
									}

									arenaPlayer.teleport(arena.hidersWarp);

									MessageM.sendFMessage(
											arenaPlayer,
											ConfigC.normal_ingameBlock,
											true,
											"block-"
													+ block.getType()
															.name()
															.replaceAll("_", "")
															.replaceAll(
																	"BLOCK", "")
															.toLowerCase()
													+ ":"
													+ block.getDurability());
								}
							}
						}
					}

					for (Player player : arena.seekers) {
						if (player.getInventory().getItem(0) == null
								|| player.getInventory().getItem(0).getType() != Material.DIAMOND_SWORD) {
							player.getInventory().setItem(0,
									new ItemStack(Material.DIAMOND_SWORD, 1));
							player.getInventory().setHelmet(
									new ItemStack(Material.IRON_HELMET, 1));
							player.getInventory().setChestplate(
									new ItemStack(Material.IRON_CHESTPLATE, 1));
							player.getInventory().setLeggings(
									new ItemStack(Material.IRON_LEGGINGS, 1));
							player.getInventory().setBoots(
									new ItemStack(Material.IRON_BOOTS, 1));
							player.getWorld().playSound(player.getLocation(),
									Sound.ANVIL_USE, 1, 1);
						}
					}
				}
			}
		}, 0, 20);
	}

	public void onDisable() {
		MessageM.sendFMessage(null, ConfigC.log_Disabled, true, "name-"
				+ W.pluginName, "version-" + W.pluginVersion, "autors-"
				+ W.pluginAutors);
	}

	/**
	 * Build a string.
	 * 
	 * @param input
	 * @param startArg
	 * @return
	 */
	public static String argsBuild(String[] input, int startArg) {
		if (input.length - startArg <= 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder(input[startArg]);
		for (int i = ++startArg; i < input.length; i++) {
			sb.append(' ').append(input[i]);
		}
		return sb.toString();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		for (CommandC command : CommandC.values()) {
			String[] commandArgsSplit = command.command.split("%");
			String[] argsSplit = commandArgsSplit[1].split("_");
			String[] argsSplitAlias = command.alias.split("%")[1].split("_");

			if (cmd.getName().equalsIgnoreCase(commandArgsSplit[0])) {
				int i = 0;
				boolean equals = true;

				if (command.minLenght == 0) {
					if (args.length == 0) {
						equals = true;
					} else {
						equals = false;
					}
				} else {
					if (args.length >= command.minLenght) {
						for (String arg : argsSplit) {
							for (String arga : argsSplitAlias) {
								if (!arg.equalsIgnoreCase(args[i])
										&& !arga.equalsIgnoreCase(args[i])) {
									equals = false;
								}
								i = i + 1;
							}
						}
					} else {
						equals = false;
					}
				}

				if (equals) {
					if (W.config.getFile().getBoolean(
							command.enabled.getLocation())) {
						command.cmd.exectue(player, cmd, label, args);
					} else {
						MessageM.sendFMessage(player,
								ConfigC.error_commandNotEnabled, true);
					}

					return true;
				}
			}
		}
		CommandC.NOT_FOUND.cmd.exectue(player, cmd, label, args);
		return true;
	}
}
