package nl.Steffion.BlockHunt.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.data.Arena;

public class CommandCreate extends Command {

	public CommandCreate() {
		super("blockhunt create", "blockhunt.create", false, "Create a new arena.");
	}

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		Arena newArena = new Arena();
		newArena.save();
		
		sender.sendMessage("Arena has been created as '" + newArena.getName() + "'.");

		if (sender instanceof Player) {
			Bukkit.dispatchCommand(sender, "blockhunt edit " + newArena.getName());
		} else {
			sender.sendMessage("You can edit properties of the arena from in-game.");
		}

		return true;
	}

}
