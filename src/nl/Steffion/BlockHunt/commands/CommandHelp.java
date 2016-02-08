package nl.Steffion.BlockHunt.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

public class CommandHelp extends Command {

	public CommandHelp() {
		super("blockhunt help [n]", "blockhunt.help", false, "§7Use /blockhunt help [n] to get page n of help.");
	}

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		List<String> helpLines = new ArrayList<String>();

		for (Command command : plugin.getCommandHandler().getCommands()) {
			if (!(command instanceof CommandHelp)) {
				if (sender.hasPermission(command.getPermission())) {
					helpLines.add("§6/" + command.getUsage() + ": §f" + command.getHelp());
				}
			} else {
				helpLines.add(getHelp());
			}
		}
		
		int pageNumber;
		int maxPages = (helpLines.size() / 6) + 1;

		if (args.length < 2) {
			pageNumber = 1;
		} else {
			try {
				pageNumber = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				sender.sendMessage("§c'" + args[1] + "' is not a valid number");
				return true;
			}
		}

		if (pageNumber > maxPages) {
			pageNumber = maxPages;
		}

		sender.sendMessage(
				"§9--------- §fBlockHunt: Index (" + pageNumber + "/" + maxPages + ") §9--------------------");

		int linesLeft = 6;

		for (int index = (pageNumber - 1) * 6; index < (pageNumber * 6); index++) {
			if ((linesLeft <= 0) || (index >= helpLines.size())) {
				break;
			}

			String helpLine = helpLines.get(index);
			
			sender.sendMessage(helpLine);
			linesLeft--;
		}

		for (int i = 0; i < linesLeft; i++) {
			sender.sendMessage(" ");
		}

		String authors = "";
		String version = plugin.getDescription().getVersion();

		for (String author : plugin.getDescription().getAuthors()) {
			authors += author + "§7§o, §6§o";
		}

		authors = authors.substring(0, authors.length() - 6);

		sender.sendMessage(" ");
		sender.sendMessage("§7§oPlugin created by: §6§o" + authors + "§7§o!");
		sender.sendMessage("§7§oVersion: §6§o" + version);
		return true;
	}
}
