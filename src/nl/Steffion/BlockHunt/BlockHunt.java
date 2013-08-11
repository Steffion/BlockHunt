package nl.Steffion.BlockHunt;

import java.io.IOException;
import java.util.LinkedList;

import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.Listeners.OnBlockBreakEvent;
import nl.Steffion.BlockHunt.Listeners.OnBlockPlaceEvent;
import nl.Steffion.BlockHunt.Listeners.OnEntityDamageByEntityEvent;
import nl.Steffion.BlockHunt.Listeners.OnEntityDamageEvent;
import nl.Steffion.BlockHunt.Listeners.OnFoodLevelChangeEvent;
import nl.Steffion.BlockHunt.Listeners.OnInventoryClickEvent;
import nl.Steffion.BlockHunt.Listeners.OnInventoryCloseEvent;
import nl.Steffion.BlockHunt.Listeners.OnPlayerCommandPreprocessEvent;
import nl.Steffion.BlockHunt.Listeners.OnPlayerDropItemEvent;
import nl.Steffion.BlockHunt.Listeners.OnPlayerInteractEvent;
import nl.Steffion.BlockHunt.Listeners.OnPlayerMoveEvent;
import nl.Steffion.BlockHunt.Listeners.OnPlayerQuitEvent;
import nl.Steffion.BlockHunt.Listeners.OnSignChangeEvent;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

public class BlockHunt extends JavaPlugin implements Listener {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	public void onEnable() {

		getServer().getPluginManager().registerEvents(this, this);

		getServer().getPluginManager().registerEvents(new OnBlockBreakEvent(),
				this);
		getServer().getPluginManager().registerEvents(new OnBlockPlaceEvent(),
				this);
		getServer().getPluginManager().registerEvents(
				new OnEntityDamageByEntityEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnEntityDamageEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnFoodLevelChangeEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnInventoryClickEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnInventoryCloseEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnPlayerCommandPreprocessEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnPlayerDropItemEvent(), this);
		getServer().getPluginManager().registerEvents(
				new OnPlayerInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new OnPlayerMoveEvent(),
				this);
		getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(),
				this);
		getServer().getPluginManager().registerEvents(new OnSignChangeEvent(),
				this);

		ConfigurationSerialization.registerClass(LocationSerializable.class,
				"BlockHuntLocation");
		ConfigurationSerialization.registerClass(Arena.class, "BlockHuntArena");

		W.newFiles();

		ArenaHandler.loadArenas();

		if (!getServer().getPluginManager().isPluginEnabled("DisguiseCraft")) {
			MessageM.broadcastFMessage(ConfigC.error_disguiseCraftNotInstalled,
					true);
		}

		W.dcAPI = DisguiseCraft.getAPI();

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
			if (!metrics.configuration.getBoolean("opt-out", false)) {
				MessageM.sendMessage(null,
						"%NSending %AMCStats %Nto their server.", true);
			} else {
				MessageM.sendMessage(
						null,
						"%EUnable to send %AMCStats %Eto their server. %AMCStats%E is disabled?",
						true);
			}
		} catch (IOException e) {
			MessageM.sendMessage(
					null,
					"%EUnable to send %AMCStats %Eto their server. Something went wrong ;(!",
					true);
		}

		MessageM.sendFMessage(null, ConfigC.log_Enabled, true, "name-"
				+ W.pluginName, "version-" + W.pluginVersion, "autors-"
				+ W.pluginAutors);

		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			@SuppressWarnings("deprecation")
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
						arena.timer = arena.timer - 1;
						if (arena.timer > 0) {
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
									ConfigC.normal_lobbyArenaStarted, true,
									"secs-" + arena.waitingTimeSeeker);

							for (int i = arena.amountSeekersOnStart; i > 0; i = i - 1) {
								Player seeker = arena.playersInArena
										.get(W.random
												.nextInt(arena.playersInArena
														.size()));
								if (!arena.seekers.contains(seeker)) {
									ArenaHandler.sendFMessage(arena,
											ConfigC.normal_ingameSeekerChoosen,
											true, "seeker-" + seeker.getName());
									arena.seekers.add(seeker);
									seeker.teleport(arena.seekersWarp);
									W.seekertime.put(seeker,
											arena.waitingTimeSeeker);
								} else {
									i = i + 1;
								}
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
									ItemStack sword = new ItemStack(
											Material.WOOD_SWORD, 1);
									sword.addUnsafeEnchantment(
											Enchantment.KNOCKBACK, 1);

									arenaPlayer.getInventory().addItem(sword);

									ItemStack blockCount = new ItemStack(block
											.getType(), 5);
									blockCount.setDurability(block
											.getDurability());
									arenaPlayer.getInventory().setItem(8,
											blockCount);
									arenaPlayer.getInventory().setHelmet(
											new ItemStack(block));
									W.pBlock.put(arenaPlayer, block);

									if (block.getDurability() != 0) {
										MessageM.sendFMessage(
												arenaPlayer,
												ConfigC.normal_ingameBlock,
												true,
												"block-"
														+ block.getType()
																.name()
																.replaceAll(
																		"_", "")
																.replaceAll(
																		"BLOCK",
																		"")
																.toLowerCase()
														+ ":"
														+ block.getDurability());
									} else {
										MessageM.sendFMessage(
												arenaPlayer,
												ConfigC.normal_ingameBlock,
												true,
												"block-"
														+ block.getType()
																.name()
																.replaceAll(
																		"_", "")
																.replaceAll(
																		"BLOCK",
																		"")
																.toLowerCase());
									}
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
							player.playSound(player.getLocation(),
									Sound.ANVIL_USE, 1, 1);
						}

						if (W.seekertime.get(player) != null) {
							W.seekertime.put(player,
									W.seekertime.get(player) - 1);
							if (W.seekertime.get(player) <= 0) {
								player.teleport(arena.hidersWarp);
								W.seekertime.remove(player);
							}
						}
					}

					if (arena.gameState == ArenaState.INGAME) {
						arena.timer = arena.timer - 1;
						if (arena.timer > 0) {
							if (arena.timer == 190) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-190");
							} else if (arena.timer == 60) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-60");
							} else if (arena.timer == 30) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-30");
							} else if (arena.timer == 10) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-10");
							} else if (arena.timer == 5) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-5");
							} else if (arena.timer == 4) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-4");
							} else if (arena.timer == 3) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-3");
							} else if (arena.timer == 2) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-2");
							} else if (arena.timer == 1) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, true,
										"1-1");
							}
						} else {
							ArenaHandler.hidersWin(arena);
							return;
						}

						for (Player player : arena.playersInArena) {
							if (!arena.seekers.contains(player)) {
								Location pLoc = player.getLocation();
								Location moveLoc = W.moveLoc.get(player);
								ItemStack block = player.getInventory()
										.getItem(8);

								if (block == null) {
									if (W.pBlock.get(player) != null) {
										block = W.pBlock.get(player);
										player.getInventory().setItem(8, block);
										player.updateInventory();
									}
								}

								if (moveLoc != null) {
									if (moveLoc.getX() == pLoc.getX()
											&& moveLoc.getY() == pLoc.getY()
											&& moveLoc.getZ() == pLoc.getZ()) {
										if (block.getAmount() > 1) {
											block.setAmount(block.getAmount() - 1);
										} else {
											Block pBlock = player.getLocation()
													.getBlock();
											if (pBlock.getType().equals(
													Material.AIR)
													|| pBlock.getType().equals(
															Material.WATER)
													|| pBlock
															.getType()
															.equals(Material.STATIONARY_WATER)) {
												if (pBlock.getType().equals(
														Material.WATER)
														|| pBlock
																.getType()
																.equals(Material.STATIONARY_WATER)) {
													W.hiddenLocWater.put(
															player, true);
												} else {
													W.hiddenLocWater.put(
															player, false);
												}
												if (W.dcAPI.isDisguised(player)) {
													W.dcAPI.undisguisePlayer(player);
													for (Player pl : Bukkit
															.getOnlinePlayers()) {
														if (!pl.equals(player)) {
															pl.hidePlayer(player);
															pl.sendBlockChange(
																	pBlock.getLocation(),
																	block.getType(),
																	(byte) block
																			.getDurability());
														}
													}

													block.addUnsafeEnchantment(
															Enchantment.DURABILITY,
															10);
													player.playSound(pLoc,
															Sound.ORB_PICKUP,
															1, 1);
													W.hiddenLoc.put(player,
															moveLoc);
													if (block.getDurability() != 0) {
														MessageM.sendFMessage(
																player,
																ConfigC.normal_ingameNowSolid,
																true,
																"block-"
																		+ block.getType()
																				.name()
																				.replaceAll(
																						"_",
																						"")
																				.replaceAll(
																						"BLOCK",
																						"")
																				.toLowerCase()
																		+ ":"
																		+ block.getDurability());
													} else {
														MessageM.sendFMessage(
																player,
																ConfigC.normal_ingameNowSolid,
																true,
																"block-"
																		+ block.getType()
																				.name()
																				.replaceAll(
																						"_",
																						"")
																				.replaceAll(
																						"BLOCK",
																						"")
																				.toLowerCase());
													}
												}
											} else {
												MessageM.sendFMessage(
														player,
														ConfigC.warning_ingameNoSolidPlace,
														true);
											}

										}
									} else {
										Block pBlock = player.getLocation()
												.getBlock();
										block.setAmount(5);

										if (W.hiddenLoc.get(player) != null) {
											pBlock = W.hiddenLoc.get(player)
													.getBlock();
										}
										if (!W.dcAPI.isDisguised(player)) {
											for (Player pl : Bukkit
													.getOnlinePlayers()) {
												if (!pl.equals(player)) {
													if (W.hiddenLocWater
															.get(player) != null) {
														if (W.hiddenLocWater
																.get(player) == true) {
															pl.sendBlockChange(
																	pBlock.getLocation(),
																	Material.STATIONARY_WATER,
																	(byte) 0);
														} else {
															pl.sendBlockChange(
																	pBlock.getLocation(),
																	Material.AIR,
																	(byte) 0);
														}
													} else {
														pl.sendBlockChange(
																pBlock.getLocation(),
																Material.AIR,
																(byte) 0);
													}

													W.hiddenLocWater
															.remove(player);
												}
											}

											player.playSound(pLoc,
													Sound.BAT_HURT, 1, 1);
											block.removeEnchantment(Enchantment.DURABILITY);

											LinkedList<String> data = new LinkedList<String>();
											data.add("blockID:"
													+ block.getTypeId());
											data.add("blockData:"
													+ block.getDurability());
											Disguise disguise = new Disguise(
													W.dcAPI.newEntityID(),
													data,
													DisguiseType.FallingBlock);
											if (W.dcAPI.isDisguised(player)) {
												W.dcAPI.changePlayerDisguise(
														player, disguise);
											} else {
												W.dcAPI.disguisePlayer(player,
														disguise);
											}

											MessageM.sendFMessage(
													player,
													ConfigC.normal_ingameNoMoreSolid,
													true);
										}
									}
								}
							}
						}
					}

					for (Player pl : arena.playersInArena) {
						pl.setLevel(arena.timer);
						pl.setGameMode(GameMode.SURVIVAL);
					}

					ScoreboardHandler.doScoreboard(arena);
				}

				SignsHandler.updateSigns();
			}
		}, 0, 20);
	}

	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			ArenaHandler.playerLeaveArena(player, false, true);
		}

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
