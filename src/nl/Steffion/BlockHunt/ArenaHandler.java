package nl.Steffion.BlockHunt;

import nl.Steffion.BlockHunt.Arena.ArenaState;

import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.MessageM.CType;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class ArenaHandler {
	public static void loadArenas() {
		W.arenaList.clear();
		for (String arenaName : W.arenas.getFile().getKeys(false)) {
			W.arenaList.add((Arena) W.arenas.getFile().get(arenaName));
		}

		for (Arena arena : W.arenaList) {
			ScoreboardHandler.createScoreboard(arena);
		}
	}

	public static void sendMessage(Arena arena, String message, Boolean tag,
			String... vars) {
		for (Player player : arena.playersInArena) {
			String pMessage = message.replaceAll("%player%", player.getName());
			player.sendMessage(MessageM.replaceAll(CType.TAG(tag) + pMessage,
					vars));
		}
	}

	public static void sendFMessage(Arena arena, ConfigC location, Boolean tag,
			String... vars) {
		for (Player player : arena.playersInArena) {
			String pMessage = location.config.getFile()
					.get(location.getLocation()).toString()
					.replaceAll("%player%", player.getName());
			player.sendMessage(MessageM.replaceAll(CType.TAG(tag) + pMessage,
					vars));
		}
	}

	public static void playerJoinArena(Player player, String arenaname) {
		boolean found = false;
		boolean alreadyJoined = false;
		for (Arena arena : W.arenaList) {
			if (arena.playersInArena != null) {
				if (arena.playersInArena.contains(player)) {
					alreadyJoined = true;
				}
			}
		}

		if (!alreadyJoined) {
			for (Arena arena : W.arenaList) {
				if (arena.arenaName.equalsIgnoreCase(arenaname)) {
					found = true;
					if (arena.disguiseBlocks.isEmpty()) {
						MessageM.sendFMessage(player,
								ConfigC.error_joinNoBlocksSet, true);
					} else {
						LocationSerializable zero = new LocationSerializable(
								Bukkit.getWorld(player.getWorld().getName()
										.toString()), 0, 0, 0, 0, 0);
						if (arena.lobbyWarp != null && arena.hidersWarp != null
								&& arena.seekersWarp != null) {
							if (!arena.lobbyWarp.equals(zero)
									&& !arena.hidersWarp.equals(zero)
									&& !arena.seekersWarp.equals(zero)) {
								if (arena.gameState == ArenaState.WAITING
										|| arena.gameState == ArenaState.STARTING) {
									if (arena.playersInArena.size() >= arena.maxPlayers) {
										if (!PlayerM.hasPerm(player,
												PermsC.joinfull, false)) {
											MessageM.sendFMessage(player,
													ConfigC.error_joinFull,
													true);
											return;
										}
									}
									arena.playersInArena.add(player);

									// OLD WAY OF SETTING A PLAYER'S DATA.
									// W.pLocation.put(player,
									// player.getLocation());
									// W.pGameMode.put(player,
									// player.getGameMode());
									//
									// player.teleport(arena.lobbyWarp);
									// player.setGameMode(GameMode.SURVIVAL);
									//
									// W.pInventory.put(player, player
									// .getInventory().getContents());
									// player.getInventory().clear();
									// player.updateInventory();
									// W.pArmor.put(player,
									// player.getInventory()
									// .getArmorContents());
									// player.getInventory().setHelmet(
									// new ItemStack(Material.AIR));
									// player.getInventory().setChestplate(
									// new ItemStack(Material.AIR));
									// player.getInventory().setLeggings(
									// new ItemStack(Material.AIR));
									// player.getInventory().setBoots(
									// new ItemStack(Material.AIR));
									// W.pEXP.put(player, player.getExp());
									// player.setExp(0);
									// W.pEXPL.put(player, player.getLevel());
									// player.setLevel(0);
									// W.pHealth.put(player,
									// player.getHealth());
									// player.setHealth(20);
									// W.pFood.put(player,
									// player.getFoodLevel());
									// player.setFoodLevel(20);

									PlayerArenaData pad = new PlayerArenaData(
											player.getLocation(),
											player.getGameMode(), player
													.getInventory()
													.getContents(), player
													.getInventory()
													.getArmorContents(),
											player.getExp(), player.getLevel(),
											player.getHealth(),
											player.getFoodLevel(),
											player.getActivePotionEffects());

									W.pData.put(player, pad);

									player.teleport(arena.lobbyWarp);
									player.setGameMode(GameMode.SURVIVAL);
									player.getActivePotionEffects().clear();
									player.setFoodLevel(20);
									player.setHealth(20);
									player.setLevel(arena.timer);
									player.setExp(0);
									player.getInventory().clear();
									player.getInventory().setHelmet(
											new ItemStack(Material.AIR));
									player.getInventory().setChestplate(
											new ItemStack(Material.AIR));
									player.getInventory().setLeggings(
											new ItemStack(Material.AIR));
									player.getInventory().setBoots(
											new ItemStack(Material.AIR));
									player.updateInventory();

									if (W.dcAPI.isDisguised(player)) {
										W.dcAPI.undisguisePlayer(player);
									}

									ArenaHandler.sendFMessage(arena,
											ConfigC.normal_joinJoinedArena,
											true,
											"playername-" + player.getName(),
											"1-" + arena.playersInArena.size(),
											"2-" + arena.maxPlayers);
									if (arena.playersInArena.size() < arena.minPlayers) {
										ArenaHandler
												.sendFMessage(
														arena,
														ConfigC.warning_lobbyNeedAtleast,
														true,
														"1-" + arena.minPlayers);
									}
								} else {
									MessageM.sendFMessage(player,
											ConfigC.error_joinArenaIngame, true);
								}
							} else {
								MessageM.sendFMessage(player,
										ConfigC.error_joinWarpsNotSet, true);
							}
						} else {
							MessageM.sendFMessage(player,
									ConfigC.error_joinWarpsNotSet, true);
						}
					}
				}
			}
		} else {
			MessageM.sendFMessage(player, ConfigC.error_joinAlreadyJoined, true);
			return;
		}

		if (!found) {
			MessageM.sendFMessage(player, ConfigC.error_noArena, true, "name-"
					+ arenaname);
		}

		SignsHandler.updateSigns();
	}

	public static void playerLeaveArena(Player player, boolean message,
			boolean cleanup) {
		Arena arena = null;
		for (Arena arena2 : W.arenaList) {
			if (arena2.playersInArena != null) {
				if (arena2.playersInArena.contains(player)) {
					arena = arena2;
				}
			}
		}

		if (arena != null) {
			if (cleanup) {
				arena.playersInArena.remove(player);
				if (arena.seekers.contains(player)) {
					arena.seekers.remove(player);
				}

				if (arena.playersInArena.size() < arena.minPlayers
						&& arena.gameState.equals(ArenaState.STARTING)) {
					arena.gameState = ArenaState.WAITING;
					arena.timer = 0;

					ArenaHandler.sendFMessage(arena,
							ConfigC.warning_lobbyNeedAtleast, true, "1-"
									+ arena.minPlayers);
				}

				if (arena.playersInArena.size() <= 1
						&& arena.gameState == ArenaState.INGAME) {
					if (arena.seekers.size() >= arena.playersInArena.size()) {
						ArenaHandler.seekersWin(arena);
					} else {
						ArenaHandler.hidersWin(arena);
					}
				}

				if (arena.seekers.size() >= arena.playersInArena.size()) {
					ArenaHandler.seekersWin(arena);
				}

				if (arena.seekers.size() <= 0
						&& arena.gameState == ArenaState.INGAME) {
					Player seeker = arena.playersInArena.get(W.random
							.nextInt(arena.playersInArena.size()));
					ArenaHandler.sendFMessage(arena,
							ConfigC.warning_ingameNEWSeekerChoosen, true,
							"seeker-" + seeker.getName());
					ArenaHandler.sendFMessage(arena,
							ConfigC.normal_ingameSeekerChoosen, true, "seeker-"
									+ seeker.getName());
					W.dcAPI.undisguisePlayer(seeker);
					for (Player pl : Bukkit.getOnlinePlayers()) {
						pl.showPlayer(seeker);
					}
					seeker.getInventory().clear();
					arena.seekers.add(seeker);
					seeker.teleport(arena.seekersWarp);
					W.seekertime.put(seeker, arena.waitingTimeSeeker);
				}
			}

			// OLD WAY OF RESETTING A PLAYER'S DATA.
			// player.getInventory().clear();
			// player.getInventory().setContents(W.pInventory.get(player));
			// player.updateInventory();
			// W.pInventory.remove(player);
			// player.getInventory().setArmorContents(W.pArmor.get(player));
			// W.pArmor.remove(player);
			// player.setExp(W.pEXP.get(player));
			// W.pEXP.remove(player);
			// player.setLevel(W.pEXPL.get(player));
			// W.pEXPL.remove(player);
			// player.setHealth(W.pHealth.get(player));
			// W.pHealth.remove(player);
			// player.setFoodLevel(W.pFood.get(player));
			// W.pFood.remove(player);
			// W.pBlock.remove(player);
			//
			// player.teleport(W.pLocation.get(player));
			//
			// player.setGameMode(W.pGameMode.get(player));
			// W.pGameMode.remove(player);
			// W.pLocation.remove(player);

			PlayerArenaData pad = new PlayerArenaData(null, null, null, null,
					null, null, null, null, null);

			if (W.pData.get(player) != null) {
				pad = W.pData.get(player);
			}

			player.getInventory().clear();
			player.getInventory().setContents(pad.pInventory);
			player.getInventory().setArmorContents(pad.pArmor);
			player.updateInventory();
			player.setExp(pad.pEXP);
			player.setLevel(pad.pEXPL);
			player.setHealth(pad.pHealth);
			player.setFoodLevel(pad.pFood);
			player.addPotionEffects(pad.pPotionEffects);
			player.teleport(pad.pLocation);
			player.setGameMode(pad.pGameMode);

			W.pData.remove(player);

			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.showPlayer(player);
				if (W.hiddenLoc.get(player) != null) {
					if (W.hiddenLocWater.get(player) != null) {
						Block pBlock = W.hiddenLoc.get(player).getBlock();
						if (W.hiddenLocWater.get(player)) {
							pl.sendBlockChange(pBlock.getLocation(),
									Material.STATIONARY_WATER, (byte) 0);
						} else {
							pl.sendBlockChange(pBlock.getLocation(),
									Material.AIR, (byte) 0);
						}
					}
				}

				if (W.dcAPI.isDisguised(player)) {
					W.dcAPI.undisguisePlayer(player);
				}
			}

			ScoreboardHandler.removeScoreboard(player);

			MessageM.sendFMessage(player, ConfigC.normal_leaveYouLeft, true);
			if (message) {
				ArenaHandler.sendFMessage(arena, ConfigC.normal_leaveLeftArena,
						true, "playername-" + player.getName(), "1-"
								+ arena.playersInArena.size(), "2-"
								+ arena.maxPlayers);
			}
		} else {
			if (message) {
				MessageM.sendFMessage(player, ConfigC.error_leaveNotInArena,
						true);
			}
			return;
		}

		SignsHandler.updateSigns();
	}

	public static void seekersWin(Arena arena) {
		ArenaHandler.sendFMessage(arena, ConfigC.normal_winSeekers, true);
		for (Player player : arena.playersInArena) {
			if (arena.seekersWinCommands != null) {
				for (String command : arena.seekersWinCommands) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							command.replaceAll("%player%", player.getName()));
				}
			}
		}

		arena.seekers.clear();

		for (Player player : arena.playersInArena) {
			playerLeaveArena(player, false, false);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		}

		arena.gameState = ArenaState.WAITING;
		arena.timer = 0;
		arena.playersInArena.clear();
	}

	public static void hidersWin(Arena arena) {
		ArenaHandler.sendFMessage(arena, ConfigC.normal_winHiders, true);
		for (Player player : arena.playersInArena) {
			if (!arena.seekers.contains(player)) {
				if (arena.hidersWinCommands != null) {
					for (String command : arena.hidersWinCommands) {
						Bukkit.dispatchCommand(
								Bukkit.getConsoleSender(),
								command.replaceAll("%player%", player.getName()));
					}
				}
			}
		}

		arena.seekers.clear();

		for (Player player : arena.playersInArena) {
			playerLeaveArena(player, false, false);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		}

		arena.gameState = ArenaState.WAITING;
		arena.timer = 0;
		arena.playersInArena.clear();
	}

	public static void stopArena(Arena arena) {
		ArenaHandler.sendFMessage(arena, ConfigC.warning_arenaStopped, true);

		arena.seekers.clear();

		for (Player player : arena.playersInArena) {
			playerLeaveArena(player, false, false);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
		}

		arena.gameState = ArenaState.WAITING;
		arena.timer = 0;
		arena.playersInArena.clear();
	}
}
