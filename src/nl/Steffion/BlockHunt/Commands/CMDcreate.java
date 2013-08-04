package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;
import nl.Steffion.BlockHunt.Serializables.ArenaSerializable;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDcreate extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.create, true)) {
			if (player != null) {
				if (args.length <= 1) {
					MessageM.sendFMessage(player,
							ConfigC.error_notEnoughArguments, true, "syntax-"
									+ CommandC.CREATE.usage);
				} else {
					if (W.pos1.get(player) != null
							&& W.pos2.get(player) != null) {
						if (W.pos1.get(player).getWorld()
								.equals(W.pos2.get(player).getWorld())) {

							ArenaSerializable arena = new ArenaSerializable(
									args[1], W.pos1.get(player),
									W.pos2.get(player), 12, 3, 1, 90, 20, 300,
									null, null, null, null, null, null, 0, null);
							W.arenas.getFile().set(args[1], arena);
							W.arenas.save();
							ArenaHandler.loadArenas();

							MessageM.sendFMessage(player,
									ConfigC.normal_createCreatedArena, true,
									"name-" + args[1]);
						} else {
							MessageM.sendFMessage(player,
									ConfigC.error_createNotSameWorld, true);
						}
					} else {
						MessageM.sendFMessage(player,
								ConfigC.error_createSelectionFirst, true);
					}
				}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_onlyIngame, true);
			}
		}
		return true;
	}
}
