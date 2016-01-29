package nl.Steffion.BlockHunt.commands;

import org.bukkit.command.CommandSender;

public class CommandHelp extends Command {

	public CommandHelp() {
		super("blockhunt help [n]", "blockhunt.help", false, "§7Use /blockhunt help [n] to get page n of help.");
	}

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		int pageNumber;
		int maxPages = (plugin.getCommandHandler().getCommands().size() / 6) + 1;
		
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

		for (int index = (pageNumber - 1) * 6; index < (pageNumber * 6); index++) {
			if (index >= plugin.getCommandHandler().getCommands().size()) {
				sender.sendMessage(" ");
				continue;
			}
			
			Command command = plugin.getCommandHandler().getCommands().get(index);
			
			if (!sender.hasPermission(command.getPermission())) {
				index--;
				continue;
			}
			
			if (command instanceof CommandHelp) {
				sender.sendMessage(command.getHelp());
			} else {
				sender.sendMessage("§6/" + command.getUsage() + ": §f" + command.getHelp());
			}
		}
		
		String authors = "";
		String version = plugin.getDescription().getVersion();
		boolean first = true;
		
		for (String author : plugin.getDescription().getAuthors()) {
			if (first) {
				authors = authors + author;
				first = false;
			} else {
				authors = authors + ", " + author;
			}
		}
		
		sender.sendMessage(" ");
		sender.sendMessage("§7§oPlugin created by: §6§o" + authors + "§7§o!");
		sender.sendMessage("§7§oVersion: §6§o" + version);
		return true;
	}
}
