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
								Bukkit.getWorld("world"), 0, 0, 0, 0, 0);
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

									W.pLocation.put(player,
											player.getLocation());
									W.pGameMode.put(player,
											player.getGameMode());
									player.setGameMode(GameMode.SURVIVAL);
									W.pInventory.put(player, player
											.getInventory().getContents());
									player.getInventory().clear();
									player.updateInventory();
									W.pArmor.put(player, player.getInventory()
											.getArmorContents());
									player.getInventory().setHelmet(
											new ItemStack(Material.AIR));
									player.getInventory().setChestplate(
											new ItemStack(Material.AIR));
									player.getInventory().setLeggings(
											new ItemStack(Material.AIR));
									player.getInventory().setBoots(
											new ItemStack(Material.AIR));
									W.pEXP.put(player, player.getExp());
									player.setExp(0);
									W.pEXPL.put(player, player.getLevel());
									player.setLevel(0);
									W.pHealth.put(player, player.getHealth());
									player.setHealth(20);
									W.pFood.put(player, player.getFoodLevel());
									player.setFoodLevel(20);

									player.teleport(arena.lobbyWarp);
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
				if (arena.playersInArena.size() <= arena.minPlayers) {
					if (arena.gameState.equals(ArenaState.STARTING)) {
						arena.gameState = ArenaState.WAITING;
						arena.timer = 0;

						ArenaHandler.sendFMessage(arena,
								ConfigC.warning_lobbyNeedAtleast, true, "1-"
										+ arena.minPlayers);
					} else {
						ArenaHandler.seekersWin(arena);
					}
				} else if (arena.seekers.size() <= 0
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
					seeker.getInventory().clear();
					arena.seekers.add(seeker);
					seeker.teleport(arena.seekersWarp);
					W.seekertime.put(seeker, arena.waitingTimeSeeker);
				}
			}

			player.teleport(W.pLocation.get(player));
			W.pLocation.remove(player);
			player.setGameMode(W.pGameMode.get(player));
			W.pGameMode.remove(player);
			player.getInventory().clear();
			player.getInventory().setContents(W.pInventory.get(player));
			player.updateInventory();
			W.pInventory.remove(player);
			player.getInventory().setArmorContents(W.pArmor.get(player));
			W.pArmor.remove(player);
			player.setExp(W.pEXP.get(player));
			W.pEXP.remove(player);
			player.setLevel(W.pEXPL.get(player));
			W.pEXPL.remove(player);
			player.setHealth(W.pHealth.get(player));
			W.pHealth.remove(player);
			player.setFoodLevel(W.pFood.get(player));
			W.pFood.remove(player);

			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.showPlayer(player);
				if (W.hiddenLoc.get(player) != null) {
					Block pBlock = W.hiddenLoc.get(player).getBlock();
					pl.sendBlockChange(pBlock.getLocation(), Material.AIR,
							(byte) 0);
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
			playerLeaveArena(player, false, false);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
			if (arena.seekersWinCommands != null) {
				for (String command : arena.seekersWinCommands) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							command.replaceAll("%player%", player.getName()));
				}
			}
		}

		arena.playersInArena.clear();
		arena.seekers.clear();
		arena.gameState = ArenaState.WAITING;
	}

	public static void hidersWin(Arena arena) {
		ArenaHandler.sendFMessage(arena, ConfigC.normal_winHiders, true);
		for (Player player : arena.playersInArena) {
			playerLeaveArena(player, false, false);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
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

		arena.playersInArena.clear();
		arena.seekers.clear();
		arena.gameState = ArenaState.WAITING;
	}
}
