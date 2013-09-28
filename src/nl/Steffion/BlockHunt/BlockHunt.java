package nl.Steffion.BlockHunt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.PermissionsC.Permissions;
import nl.Steffion.BlockHunt.Commands.CMDcreate;
import nl.Steffion.BlockHunt.Commands.CMDhelp;
import nl.Steffion.BlockHunt.Commands.CMDinfo;
import nl.Steffion.BlockHunt.Commands.CMDjoin;
import nl.Steffion.BlockHunt.Commands.CMDleave;
import nl.Steffion.BlockHunt.Commands.CMDlist;
import nl.Steffion.BlockHunt.Commands.CMDnotfound;
import nl.Steffion.BlockHunt.Commands.CMDreload;
import nl.Steffion.BlockHunt.Commands.CMDremove;
import nl.Steffion.BlockHunt.Commands.CMDset;
import nl.Steffion.BlockHunt.Commands.CMDsetwarp;
import nl.Steffion.BlockHunt.Commands.CMDshop;
import nl.Steffion.BlockHunt.Commands.CMDstart;
import nl.Steffion.BlockHunt.Commands.CMDtokens;
import nl.Steffion.BlockHunt.Commands.CMDwand;
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
import nl.Steffion.BlockHunt.Managers.CommandM;
import nl.Steffion.BlockHunt.Managers.ConfigM;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PermissionsM;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;
import nl.Steffion.BlockHunt.mcstats.Metrics;
import nl.Steffion.BlockHunt.mcstats.Metrics.Graph;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockHunt extends JavaPlugin implements Listener {
	/**
	 * Steffion's Engine - Made by Steffion.
	 * 
	 * You're allowed to use this engine for own usage, you're not allowed to
	 * republish the engine. Using this for your own plugin is allowed when a
	 * credit is placed somewhere in the plugin.
	 * 
	 * Thanks for your cooperate!
	 * 
	 * @author Steffion
	 */

	public static PluginDescriptionFile pdfFile;
	public static BlockHunt plugin;

	@SuppressWarnings("serial")
	public static List<String> BlockHuntCMD = new ArrayList<String>() {
		{
			add("info");
			add("help");
			add("reload");
			add("join");
			add("leave");
			add("list");
			add("shop");
			add("start");
			add("wand");
			add("create");
			add("set");
			add("setwarp");
			add("remove");
			add("tokens");
		}
	};

	public static CommandM CMD;
	public static CommandM CMDinfo;
	public static CommandM CMDhelp;
	public static CommandM CMDreload;
	public static CommandM CMDjoin;
	public static CommandM CMDleave;
	public static CommandM CMDlist;
	public static CommandM CMDshop;
	public static CommandM CMDstart;
	public static CommandM CMDwand;
	public static CommandM CMDcreate;
	public static CommandM CMDset;
	public static CommandM CMDsetwarp;
	public static CommandM CMDremove;
	public static CommandM CMDtokens;

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

		pdfFile = getDescription();
		plugin = this;

		ConfigM.newFiles();

		CMD = new CommandM("BlockHunt", "BlockHunt", null, null,
				Permissions.info, ConfigC.help_info,
				(Boolean) W.config.get(ConfigC.commandEnabled_info),
				BlockHuntCMD, new CMDinfo(), null);
		CMDinfo = new CommandM("BlockHunt INFO", "BlockHunt", "info", "i",
				Permissions.info, ConfigC.help_info,
				(Boolean) W.config.get(ConfigC.commandEnabled_info),
				BlockHuntCMD, new CMDinfo(), "/BlockHunt [info|i]");
		CMDhelp = new CommandM("BlockHunt HELP", "BlockHunt", "help", "h",
				Permissions.help, ConfigC.help_help,
				(Boolean) W.config.get(ConfigC.commandEnabled_help),
				BlockHuntCMD, new CMDhelp(),
				"/BlockHunt <help|h> [page number]");
		CMDreload = new CommandM("BlockHunt RELOAD", "BlockHunt", "reload",
				"r", Permissions.reload, ConfigC.help_reload,
				(Boolean) W.config.get(ConfigC.commandEnabled_reload),
				BlockHuntCMD, new CMDreload(), "/BlockHunt <reload|r>");
		CMDjoin = new CommandM("BlockHunt JOIN", "BlockHunt", "join", "j",
				Permissions.join, ConfigC.help_join,
				(Boolean) W.config.get(ConfigC.commandEnabled_join),
				BlockHuntCMD, new CMDjoin(), "/BlockHunt <join|j> <arenaname>");
		CMDleave = new CommandM("BlockHunt LEAVE", "BlockHunt", "leave", "l",
				Permissions.leave, ConfigC.help_leave,
				(Boolean) W.config.get(ConfigC.commandEnabled_leave),
				BlockHuntCMD, new CMDleave(), "/BlockHunt <leave|l>");
		CMDlist = new CommandM("BlockHunt LIST", "BlockHunt", "list", "li",
				Permissions.list, ConfigC.help_list,
				(Boolean) W.config.get(ConfigC.commandEnabled_list),
				BlockHuntCMD, new CMDlist(), "/BlockHunt <list|li>");
		CMDshop = new CommandM("BlockHunt SHOP", "BlockHunt", "shop", "sh",
				Permissions.shop, ConfigC.help_shop,
				(Boolean) W.config.get(ConfigC.commandEnabled_shop),
				BlockHuntCMD, new CMDshop(), "/BlockHunt <shop|sh>");
		CMDstart = new CommandM("BlockHunt START", "BlockHunt", "start", "go",
				Permissions.start, ConfigC.help_start,
				(Boolean) W.config.get(ConfigC.commandEnabled_start),
				BlockHuntCMD, new CMDstart(),
				"/BlockHunt <start|go> <arenaname>");
		CMDwand = new CommandM("BlockHunt WAND", "BlockHunt", "wand", "w",
				Permissions.create, ConfigC.help_wand,
				(Boolean) W.config.get(ConfigC.commandEnabled_wand),
				BlockHuntCMD, new CMDwand(), "/BlockHunt <wand|w>");
		CMDcreate = new CommandM("BlockHunt CREATE", "BlockHunt", "create",
				"c", Permissions.create, ConfigC.help_create,
				(Boolean) W.config.get(ConfigC.commandEnabled_create),
				BlockHuntCMD, new CMDcreate(),
				"/BlockHunt <create|c> <arenaname>");
		CMDset = new CommandM("BlockHunt SET", "BlockHunt", "set", "s",
				Permissions.set, ConfigC.help_set,
				(Boolean) W.config.get(ConfigC.commandEnabled_set),
				BlockHuntCMD, new CMDset(), "/BlockHunt <set|s> <arenaname>");
		CMDsetwarp = new CommandM("BlockHunt SETWARP", "BlockHunt", "setwarp",
				"sw", Permissions.setwarp, ConfigC.help_setwarp,
				(Boolean) W.config.get(ConfigC.commandEnabled_setwarp),
				BlockHuntCMD, new CMDsetwarp(),
				"/BlockHunt <setwarp|sw> <lobby|hiders|seekers> <arenaname>");
		CMDremove = new CommandM("BlockHunt REMOVE", "BlockHunt", "remove",
				"delete", Permissions.remove, ConfigC.help_remove,
				(Boolean) W.config.get(ConfigC.commandEnabled_remove),
				BlockHuntCMD, new CMDremove(),
				"/BlockHunt <remove|delete> <arenaname>");
		CMDtokens = new CommandM("BlockHunt TOKENS", "BlockHunt", "tokens",
				"t", Permissions.tokens, ConfigC.help_tokens,
				(Boolean) W.config.get(ConfigC.commandEnabled_tokens),
				BlockHuntCMD, new CMDtokens(),
				"/BlockHunt <tokens|t> <set|add|take> <playername> <amount>");

		if (!getServer().getPluginManager().isPluginEnabled("LibsDisguises")) {
			MessageM.broadcastFMessage(ConfigC.error_libsDisguisesNotInstalled);
		}

		if (!getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
			MessageM.broadcastFMessage(ConfigC.error_protocolLibNotInstalled);
		}

		ArenaHandler.loadArenas();

		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

			@Override
			public void run() {
				try {
					Metrics metrics = new Metrics(plugin);
					Graph playersPlayingBlockHunt = metrics
							.createGraph("Players playing BlockHunt");

					playersPlayingBlockHunt.addPlotter(new Metrics.Plotter(
							"Playing") {

						@Override
						public int getValue() {
							int playersPlaying = 0;
							for (Arena arena : W.arenaList) {
								playersPlaying = playersPlaying
										+ arena.playersInArena.size();
							}
							return playersPlaying;
						}

					});

					playersPlayingBlockHunt.addPlotter(new Metrics.Plotter(
							"Not playing") {

						@Override
						public int getValue() {
							int playersPlaying = 0;
							for (Arena arena : W.arenaList) {
								playersPlaying = playersPlaying
										+ arena.playersInArena.size();
							}
							return Bukkit.getOnlinePlayers().length
									- playersPlaying;
						}

					});

					metrics.start();
					FileConfiguration metrics_fc = new YamlConfiguration();
					metrics_fc.load(metrics.getConfigFile());
					if (!metrics_fc.getBoolean("opt-out", false)) {
						MessageM.sendMessage(null,
								"%TAG%NSending %AMCStats%N to the server...");
					} else {
						MessageM.sendMessage(null,
								"%TAG%EUnable to send %AMCStats %Eto the server. %AMCStats%E is disabled?");
					}
				} catch (IOException e) {
					MessageM.sendMessage(null,
							"%TAG%EUnable to send %AMCStats %Eto the server. Something went wrong ;(!");
				} catch (InvalidConfigurationException e) {
					MessageM.sendMessage(null,
							"%TAG%EUnable to send %AMCStats %Eto the server. Something went wrong ;(!");
				}
			}
		}, 0, 6000);

		if ((Boolean) W.config.get(ConfigC.autoUpdateCheck)) {
			if ((Boolean) W.config.get(ConfigC.autoDownloadUpdate)) {
				new Updater(this, pdfFile.getName(), this.getFile(),
						Updater.UpdateType.DEFAULT, true);
			} else {
				new Updater(this, pdfFile.getName(), this.getFile(),
						Updater.UpdateType.NO_DOWNLOAD, true);
			}
		}

		MessageM.sendFMessage(null, ConfigC.log_enabledPlugin, "name-"
				+ BlockHunt.pdfFile.getName(),
				"version-" + BlockHunt.pdfFile.getVersion(), "autors-"
						+ BlockHunt.pdfFile.getAuthors().get(0));

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
									ConfigC.normal_lobbyArenaIsStarting, "1-"
											+ arena.timeInLobbyUntilStart);
						}
					} else if (arena.gameState == ArenaState.STARTING) {
						arena.timer = arena.timer - 1;
						if (arena.timer > 0) {
							if (arena.timer == 60) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										"1-60");
							} else if (arena.timer == 30) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										"1-30");
							} else if (arena.timer == 10) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										"1-10");
							} else if (arena.timer == 5) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 0);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										"1-5");
							} else if (arena.timer == 4) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 0);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										"1-4");
							} else if (arena.timer == 3) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 1);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										"1-3");
							} else if (arena.timer == 2) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 1);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										"1-2");
							} else if (arena.timer == 1) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 2);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_lobbyArenaIsStarting,
										"1-1");
							}
						} else {
							arena.gameState = ArenaState.INGAME;
							arena.timer = arena.gameTime;
							ArenaHandler.sendFMessage(arena,
									ConfigC.normal_lobbyArenaStarted, "secs-"
											+ arena.waitingTimeSeeker);

							for (int i = arena.amountSeekersOnStart; i > 0; i = i - 1) {
								Player seeker = arena.playersInArena
										.get(W.random
												.nextInt(arena.playersInArena
														.size()));
								if (!arena.seekers.contains(seeker)) {
									ArenaHandler.sendFMessage(arena,
											ConfigC.normal_ingameSeekerChoosen,
											"seeker-" + seeker.getName());
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
									arenaPlayer.getInventory().clear();
									arenaPlayer.updateInventory();
									ItemStack block = arena.disguiseBlocks.get(W.random
											.nextInt(arena.disguiseBlocks
													.size()));

									if (W.choosenBlock.get(arenaPlayer) != null) {
										block = W.choosenBlock.get(arenaPlayer);
										W.choosenBlock.remove(arenaPlayer);
									}

									MiscDisguise disguise = new MiscDisguise(
											DisguiseType.FALLING_BLOCK, block
													.getTypeId(), block
													.getDurability());
									DisguiseAPI.disguiseToAll(arenaPlayer,
											disguise);

									arenaPlayer.teleport(arena.hidersWarp);

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
							if (arena.timer == arena.gameTime
									- arena.timeUntilHidersSword) {
								ItemStack sword = new ItemStack(
										Material.WOOD_SWORD, 1);
								sword.addUnsafeEnchantment(
										Enchantment.KNOCKBACK, 1);
								for (Player arenaPlayer : arena.playersInArena) {
									if (!arena.seekers.contains(arenaPlayer)) {
										arenaPlayer.getInventory().addItem(
												sword);
										MessageM.sendFMessage(arenaPlayer,
												ConfigC.normal_ingameGivenSword);
									}
								}
							}
							if (arena.timer == 190) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-190");
							} else if (arena.timer == 60) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-60");
							} else if (arena.timer == 30) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-30");
							} else if (arena.timer == 10) {
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-10");
							} else if (arena.timer == 5) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 0);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-5");
							} else if (arena.timer == 4) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 0);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-4");
							} else if (arena.timer == 3) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 1);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-3");
							} else if (arena.timer == 2) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 1);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-2");
							} else if (arena.timer == 1) {
								arena.lobbyWarp.getWorld()
										.playSound(arena.lobbyWarp,
												Sound.ORB_PICKUP, 1, 2);
								ArenaHandler.sendFMessage(arena,
										ConfigC.normal_ingameArenaEnd, "1-1");
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
												if (DisguiseAPI
														.isDisguised(player)) {
													DisguiseAPI
															.undisguiseToAll(player);
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
											} else {
												MessageM.sendFMessage(
														player,
														ConfigC.warning_ingameNoSolidPlace);
											}
										}
									} else {
										block.setAmount(5);
										if (!DisguiseAPI.isDisguised(player)) {
											SolidBlockHandler
													.makePlayerUnsolid(player);
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

					ScoreboardHandler.updateScoreboard(arena);
				}

				SignsHandler.updateSigns();
			}
		}, 0, 20);
	}

	public void onDisable() {
		for (Arena arena : W.arenaList) {
			ArenaHandler.stopArena(arena);
		}

		MessageM.sendFMessage(null, ConfigC.log_disabledPlugin, "name-"
				+ BlockHunt.pdfFile.getName(),
				"version-" + BlockHunt.pdfFile.getVersion(), "autors-"
						+ BlockHunt.pdfFile.getAuthors().get(0));
	}

	/**
	 * Args to String. Makes 1 string.
	 * 
	 * @param input
	 *            String list which should be converted to a string.
	 * @param startArg
	 *            Start on this length.
	 * 
	 * @return The converted string.
	 */
	public static String stringBuilder(String[] input, int startArg) {
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

		for (CommandM command : W.commands) {
			String[] argsSplit = null;
			String[] argsSplitAlias = null;

			if (command.args != null && command.argsalias != null) {
				argsSplit = command.args.split("/");
				argsSplitAlias = command.argsalias.split("/");
			}

			if (cmd.getName().equalsIgnoreCase(command.label)) {
				boolean equals = true;

				if (argsSplit == null) {
					if (args.length == 0) {
						equals = true;
					} else {
						equals = false;
					}
				} else {
					if (args.length >= argsSplit.length) {
						for (int i2 = argsSplit.length - 1; i2 >= 0; i2 = i2 - 1) {
							int loc = argsSplit.length - i2 - 1;
							if (!argsSplit[loc].equalsIgnoreCase(args[loc])
									&& !argsSplitAlias[loc]
											.equalsIgnoreCase(args[loc])) {
								equals = false;
							}
						}
					} else {
						equals = false;
					}
				}

				if (equals) {
					if (PermissionsM.hasPerm(player, command.permission, true)) {
						if (command.enabled) {
							command.CMD.exectue(player, cmd, label, args);
						} else {
							MessageM.sendFMessage(player,
									ConfigC.error_commandNotEnabled);
						}
					}

					return true;
				}
			}
		}

		CMDnotfound.exectue(player, cmd, label, args);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String label, String[] args) {

		for (CommandM command : W.commands) {
			if (cmd.getName().equalsIgnoreCase(command.label)) {
				if (args.length == 1) {
					return command.mainTABlist;
				}
			}
		}

		return null;
	}
}
