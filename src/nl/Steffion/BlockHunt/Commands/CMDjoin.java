package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDjoin extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.join, true)) {
			if (player != null) {
				if (args.length <= 1) {
					MessageM.sendFMessage(player,
							ConfigC.error_notEnoughArguments, true, "syntax-"
									+ CommandC.JOIN.usage);
				} else {
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
							if (arena.arenaName.equalsIgnoreCase(args[1])) {
								found = true;
								if (arena.disguiseBlocks.isEmpty()) {
									MessageM.sendFMessage(player,
											ConfigC.error_joinNoBlocksSet, true);
								} else {
									arena.playersInArena.add(player);
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
								}
							}
						}
					} else {
						MessageM.sendFMessage(player,
								ConfigC.error_joinAlreadyJoined, true);
						return true;
					}

					if (!found) {
						MessageM.sendFMessage(player, ConfigC.error_noArena,
								true, "name-" + args[1]);
					}
				}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_onlyIngame, true);
			}
		}
		return true;
	}
}
