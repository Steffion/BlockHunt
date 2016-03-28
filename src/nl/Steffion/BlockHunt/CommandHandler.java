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

/**
 *
 * @author Steffion (Stef de Goey) 2016
 *
 */
public class CommandHandler {
	private BlockHunt plugin;
	
	CommandHandler() {
		plugin = BlockHunt.getPlugin();
	}
	
	/**
	 * Match a given command with a registered command.
	 *
	 * @param cmd
	 *            - The command, provided by Bukkit
	 * @param args
	 *            - The arguments provided with the command.
	 * @return The command class if a match was found. Returns null if no match
	 *         was found.
	 */
	private Command getCommandMatch(org.bukkit.command.Command cmd, String[] args) {
		if (cmd.getName().equalsIgnoreCase("blockhunt")) {
			if (args.length == 0) return new CommandHelp();

			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) return new CommandCreate();
				if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("e")) return new CommandEdit();
				if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) return new CommandHelp();
				if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("j")) return new CommandJoin();
				if (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("l")) return new CommandLeave();
				if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("li")) return new CommandList();
				if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) return new CommandRemove();
			}
		}

		return null;
	}
	
	/**
	 *
	 * @return A list of commands.
	 */
	public List<Command> getCommands() {
		List<Command> commands = new ArrayList<>();
		
		commands.add(new CommandHelp());
		
		commands.add(new CommandCreate());
		commands.add(new CommandEdit());
		commands.add(new CommandJoin());
		commands.add(new CommandLeave());
		commands.add(new CommandList());
		commands.add(new CommandRemove());
		
		return commands;
	}

	/**
	 * Handle the command given. Most likely this is being called from the
	 * onCommand in the main class.
	 *
	 * @param sender
	 *            - The CommandSender.
	 * @param cmd
	 *            - The command, provided by Bukkit.
	 * @param label
	 *            - The command label.
	 * @param args
	 *            - The arguments provided with the command.
	 * @return Boolean if the command was executed correctly.
	 */
	boolean handleCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if (!BlockHunt.ENABLED) {
			printMissingDependenciesWarning(sender);
			return true;
		}
		
		Command command = getCommandMatch(cmd, args);
		
		if (command == null) return false;

		// Display warning if the command is in-game only.
		if (command.isIngameCommand() && !(sender instanceof Player)) {
			sender.sendMessage("§cThis command is an in-game only command!");
			return true;
		}
		
		// Display a warning if the sender doesn't have the permission.
		if (!sender.hasPermission(command.getPermission())) {
			sender.sendMessage("§cI'm sorry, but I cannot allow you to do that. Insufficient permissions. <"
					+ command.getPermission() + ">");
			return true;
		}

		// Execute command.
		command.runCommand(sender, args);
		return true;
	}

	/**
	 * Displays a warning to the sender given. Useful if the plugin is not
	 * enabled.
	 *
	 * @param sender
	 *            - Sender you want to display the warning to.
	 */
	private void printMissingDependenciesWarning(CommandSender sender) {
		sender.sendMessage("§9--------- §fBlockHunt: §eWarning §9--------------------");
		sender.sendMessage("");
		sender.sendMessage("     §c§lBlockHunt is missing required dependencies!");
		sender.sendMessage(" §c§lMake sure all the required plugins are installed!");
		sender.sendMessage("");

		String install = "       §c§lReload the server: ";

		if (!new File("plugins/LibsDisguises.jar").exists()) {
			install += "LibsDisguises, ";
			install = install.replaceAll("Reload the server", "Install");
		}

		if (!new File("plugins/ProtocolLib.jar").exists()) {
			install += "ProtocolLib, ";
			install = install.replaceAll("Reload the server", "Install");
		}

		install = install.substring(0, install.length() - 2) + ".";
		
		sender.sendMessage(install);
		sender.sendMessage(" ");
		printPluginInfo(sender);
	}
	
	/**
	 * Displays the authors of the plugin and the plugin vesrion.
	 *
	 * @param sender
	 *            - Sender you want to display the message to.
	 */
	public void printPluginInfo(CommandSender sender) {
		String authors = "";
		String version = plugin.getDescription().getVersion();

		for (String author : plugin.getDescription().getAuthors()) {
			authors += author + "§7§o, §6§o";
		}

		authors = authors.substring(0, authors.length() - 6);

		sender.sendMessage("");
		sender.sendMessage("§7§oPlugin created by: §6§o" + authors + "§7§o!");
		sender.sendMessage("§7§oVersion: §6§o" + version);
	}
	
}
