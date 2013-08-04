package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDsetwarp extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.set, true)) {
			if (player != null) {
				if (args.length <= 2) {
					MessageM.sendFMessage(player,
							ConfigC.error_notEnoughArguments, true, "syntax-"
									+ CommandC.SETWARP.usage);
				} else {
					String arenaname = args[2];
					String warpname = args[1];

					Arena arena = null;
					for (Arena arena2 : W.arenaList) {
						if (arena2.arenaName.equalsIgnoreCase(arenaname)) {
							arena = arena2;
						}
					}
					if (arena != null) {
						LocationSerializable loc = new LocationSerializable(
								player.getLocation());
						if (warpname.equalsIgnoreCase("lobby")) {
							arena.lobbyWarp = loc;
							save(arena);
							MessageM.sendFMessage(player,
									ConfigC.normal_setwarpWarpSet, true,
									"warp-" + warpname);
						} else if (warpname.equalsIgnoreCase("hiders")) {
							arena.hidersWarp = loc;
							save(arena);
							MessageM.sendFMessage(player,
									ConfigC.normal_setwarpWarpSet, true,
									"warp-" + warpname);
						} else if (warpname.equalsIgnoreCase("seekers")) {
							arena.seekersWarp = loc;
							save(arena);
							MessageM.sendFMessage(player,
									ConfigC.normal_setwarpWarpSet, true,
									"warp-" + warpname);
						} else {
							MessageM.sendFMessage(player,
									ConfigC.error_setwarpWarpNotFound, true,
									"warp-" + warpname);
						}
					} else {
						MessageM.sendFMessage(player, ConfigC.error_noArena,
								true, "name-" + arenaname);
					}
				}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_onlyIngame, true);
			}
		}
		return true;
	}

	public void save(Arena arena) {
		W.arenas.getFile().set(arena.arenaName, arena);
		W.arenas.save();
		ArenaHandler.loadArenas();
	}
}
