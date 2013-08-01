package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.InventoryHandler;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDset extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.set, true)) {
			if (player != null) {
				if (args.length <= 1) {
					MessageM.sendFMessage(player,
							ConfigC.error_notEnoughArguments, true, "syntax-"
									+ CommandC.SET.usage);
				} else {
					String arenaname = args[1];
					InventoryHandler.openPanel(player, arenaname);
				}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_onlyIngame, true);
			}
		}
		return true;
	}
}
