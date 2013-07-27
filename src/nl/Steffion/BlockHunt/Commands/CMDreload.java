package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDreload extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.reload, true)) {
			W.config.load();
			W.messages.load();
			W.newFiles();
			MessageM.sendFMessage(player, ConfigC.normal_reloadedConfigs, true);
		}
		return true;
	}
}
