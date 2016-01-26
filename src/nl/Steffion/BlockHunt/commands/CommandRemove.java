package nl.Steffion.BlockHunt.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.data.Arena;

public class CommandRemove extends Command {

	public CommandRemove() {
		super("blockhunt remove <arena>", "blockhunt.remove", false, "Remove an arena.");
	}

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("§cUsage: /" + getUsage());
			return true;
		}

		String arenaName = "";
		
		for (int i = 1; i < args.length; i++) {
			arenaName = arenaName + args[i] + " ";
		}
		
		arenaName = arenaName.substring(0, arenaName.length() - 1);

		if (!plugin.getArenas().getConfig().contains(arenaName)) {
			sender.sendMessage("§cNo arena exists with the name '" + arenaName + "'");
			return true;
		}

		Arena arena = new Arena(arenaName);

		if (sender instanceof Player) {
			Bukkit.dispatchCommand(sender, "blockhunt leave");
		}

		plugin.getArenas().getConfig().set(arena.getName(), null);
		plugin.getArenas().save();

		sender.sendMessage("Arena '" + arena.getName() + "' has been removed succesfully!");
		return true;
		
	}

}
