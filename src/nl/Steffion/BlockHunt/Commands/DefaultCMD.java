package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class DefaultCMD {
	
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		MessageM.sendMessage(player, "%NExample of a Command!", true);
		//TODO Place the command stuff here.
		return true;
	}
}
