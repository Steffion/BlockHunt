package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDlist extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.list, true)) {
			MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
					"header-" + W.pluginName);
			if (W.arenaList.size() >= 1) {
				for (Arena arena : W.arenaList) {
					MessageM.sendMessage(player, "&7Available arena(s):", false);
					MessageM.sendMessage(player, "%A" + arena.arenaName, false);
				}
			} else {
				MessageM.sendMessage(player, "&7&oNo arenas available...",
						false);
				MessageM.sendMessage(player,
						"&7&oCreate an arena first please.", false);
			}
			MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
					"header-&oArenas list");
		}
		return true;
	}
}
