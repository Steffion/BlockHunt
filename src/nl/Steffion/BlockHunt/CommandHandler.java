package nl.Steffion.BlockHunt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.commands.Command;
import nl.Steffion.BlockHunt.commands.CommandCreate;
import nl.Steffion.BlockHunt.commands.CommandEdit;
import nl.Steffion.BlockHunt.commands.CommandHelp;
import nl.Steffion.BlockHunt.commands.CommandLeave;
import nl.Steffion.BlockHunt.commands.CommandList;
import nl.Steffion.BlockHunt.commands.CommandRemove;

public class CommandHandler {
	private CommandCreate	commandCreate;
	private CommandEdit		commandEdit;
	private CommandHelp		commandHelp;
	private CommandLeave	commandLeave;
	private CommandList		commandList;
	private CommandRemove	commandRemove;
	private List<Command>	commands	= new ArrayList<Command>();

	public CommandHandler() {
		commandCreate = new CommandCreate();
		commandEdit = new CommandEdit();
		commandHelp = new CommandHelp();
		commandLeave = new CommandLeave();
		commandList = new CommandList();
		commandRemove = new CommandRemove();

		commands.add(commandHelp);

		commands.add(commandCreate);
		commands.add(commandEdit);
		commands.add(commandLeave);
		commands.add(commandList);
		commands.add(commandRemove);
	}

	private Command getCommandMatch(org.bukkit.command.Command cmd, String[] args) {
		if (cmd.getName().equalsIgnoreCase("blockhunt")) {
			if (args.length == 0) {
				return commandHelp;
			}
			
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
					return commandCreate;
				}
				
				if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("e")) {
					return commandEdit;
				}
				
				if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
					return commandHelp;
				}
				
				if (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("l")) {
					return commandLeave;
				}
				
				if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("li")) {
					return commandList;
				}
				
				if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
					return commandRemove;
				}
			}
		}
		
		return null;
	}

	public List<Command> getCommands() {
		return commands;
	}
	
	public boolean handleCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		Command command = getCommandMatch(cmd, args);

		if (command == null) {
			return false;
		}
		
		if (command.isIngameCommand() && !(sender instanceof Player)) {
			sender.sendMessage("§cThis command is an in-game only command!");
			return true;
		}

		if (!sender.hasPermission(command.getPermission())) {
			sender.sendMessage("§cI'm sorry, but I cannot allow you to do that. Insufficient permissions. <"
					+ command.getPermission() + ">");
			return true;
		}
		
		command.runCommand(sender, args);
		return true;
	}

}
