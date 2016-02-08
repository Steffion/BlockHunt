package nl.Steffion.BlockHunt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.commands.Command;
import nl.Steffion.BlockHunt.commands.CommandCreate;
import nl.Steffion.BlockHunt.commands.CommandEdit;
import nl.Steffion.BlockHunt.commands.CommandHelp;
import nl.Steffion.BlockHunt.commands.CommandJoin;
import nl.Steffion.BlockHunt.commands.CommandLeave;
import nl.Steffion.BlockHunt.commands.CommandList;
import nl.Steffion.BlockHunt.commands.CommandRemove;

public class CommandHandler {
	private CommandCreate	commandCreate;
	private CommandEdit		commandEdit;
	private CommandHelp		commandHelp;
	private CommandJoin		commandJoin;
	private CommandLeave	commandLeave;
	private CommandList		commandList;
	private CommandRemove	commandRemove;
	private List<Command>	commands	= new ArrayList<Command>();
	private BlockHunt		plugin;
							
	public CommandHandler() {
		plugin = BlockHunt.getPlugin();
		commandCreate = new CommandCreate();
		commandEdit = new CommandEdit();
		commandHelp = new CommandHelp();
		commandJoin = new CommandJoin();
		commandLeave = new CommandLeave();
		commandList = new CommandList();
		commandRemove = new CommandRemove();

		commands.add(commandHelp);

		commands.add(commandCreate);
		commands.add(commandEdit);
		commands.add(commandJoin);
		commands.add(commandLeave);
		commands.add(commandList);
		commands.add(commandRemove);
	}

	private Command getCommandMatch(org.bukkit.command.Command cmd, String[] args) {
		if (cmd.getName().equalsIgnoreCase("blockhunt")) {
			if (args.length == 0) return commandHelp;
			
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) return commandCreate;
				if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("e")) return commandEdit;
				if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) return commandHelp;
				if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("j")) return commandJoin;
				if (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("l")) return commandLeave;
				if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("li")) return commandList;
				if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) return commandRemove;
			}
		}
		
		return null;
	}

	public List<Command> getCommands() {
		return commands;
	}
	
	public boolean handleCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if (!BlockHunt.ENABLED) {
			sender.sendMessage("§9--------- §fBlockHunt: §eWarning §9--------------------");
			sender.sendMessage("");
			sender.sendMessage("     §c§lBlockHunt is missing required dependencies!");
			sender.sendMessage(" §c§lMake sure all the required plugins are installed!");
			sender.sendMessage("");
			
			String install = "       §c§lInstall: ";
			
			if (!new File("plugins/LibsDisguises.jar").exists()) {
				install += "LibsDisguises, ";
			}
			
			if (!new File("plugins/ProtocolLib.jar").exists()) {
				install += "ProtocolLib, ";
			}
			
			install = install.substring(0, install.length() - 2) + ".";

			sender.sendMessage(install);
			sender.sendMessage("");
			
			String authors = "";
			String version = plugin.getDescription().getVersion();
			
			for (String author : plugin.getDescription().getAuthors()) {
				authors += author + "§7§o, §6§o";
			}
			
			authors = authors.substring(0, authors.length() - 6);
			
			sender.sendMessage("");
			sender.sendMessage("§7§oPlugin created by: §6§o" + authors + "§7§o!");
			sender.sendMessage("§7§oVersion: §6§o" + version);
			return true;
		}

		Command command = getCommandMatch(cmd, args);

		if (command == null) return false;
		
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
