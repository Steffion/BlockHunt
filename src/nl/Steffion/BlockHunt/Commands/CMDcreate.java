package nl.Steffion.BlockHunt.Commands;

import java.util.ArrayList;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.ScoreboardHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMDcreate extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			if (args.length <= 1) {
				MessageM.sendFMessage(player, ConfigC.error_notEnoughArguments,
						"syntax-" + BlockHunt.CMDcreate.usage);
			} else {
				if (W.pos1.get(player) != null && W.pos2.get(player) != null) {
					if (W.pos1.get(player).getWorld()
							.equals(W.pos2.get(player).getWorld())) {
						Arena arena = new Arena(args[1], W.pos1.get(player),
								W.pos2.get(player), 12, 3, 1, 50, 20, 300, 30,
								new ArrayList<ItemStack>(), null, null, null,
								new ArrayList<String>(),
								new ArrayList<String>(),
								new ArrayList<String>(), 10, 50, 8,
								new ArrayList<Player>(), ArenaState.WAITING, 0,
								new ArrayList<Player>(), Bukkit
										.getScoreboardManager()
										.getNewScoreboard());
						W.arenas.getFile().set(args[1], arena);
						W.arenas.save();
						W.signs.load();

						W.arenaList.add(arena);
						ScoreboardHandler.createScoreboard(arena);

						MessageM.sendFMessage(player,
								ConfigC.normal_createCreatedArena, "name-"
										+ args[1]);
					} else {
						MessageM.sendFMessage(player,
								ConfigC.error_createNotSameWorld);
					}
				} else {
					MessageM.sendFMessage(player,
							ConfigC.error_createSelectionFirst);
				}
			}
		} else {
			MessageM.sendFMessage(player, ConfigC.error_onlyIngame);
		}
		return true;
	}
}
