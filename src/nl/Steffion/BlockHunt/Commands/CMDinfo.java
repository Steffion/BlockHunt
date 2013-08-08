package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDinfo extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.info, true)) {
			MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
					"header-" + W.pluginName);
			MessageM.sendMessage(player, "%A%name%%N made by %A%autors%%N.",
					false, "name-" + W.pluginName, "autors-" + W.pluginAutors);
			MessageM.sendMessage(player, "%NVersion: %A%version%%N.", false,
					"version-" + W.pluginVersion);
			MessageM.sendMessage(player, "%NType %A/%helpusage% %Nfor help.",
					false, "helpusage-" + CommandC.HELP.usage);
			MessageM.sendMessage(
					player,
					"%NDev-Page: %Ahttp://dev.bukkit.org/bukkit-plugins/blockhunt/",
					false);
			MessageM.sendMessage(player, "%NDonations are welcome!", false);
			MessageM.sendMessage(player,
					"%NMade by help from some friends &c<3%N!", false);
			MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
					"header-&oInfo Page");
		}
		return true;
	}
}
