package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDnotfound extends DefaultCMD {
	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		MessageM.sendFMessage(player, ConfigC.error_commandNotFound, true);
		return true;
	}
}
