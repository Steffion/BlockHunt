package nl.Steffion.BlockHunt.commands;

import java.util.HashMap;

import org.bukkit.command.CommandSender;

public class CommandHelp extends Command {

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		HashMap<String, String> help = new HashMap<String, String>();

		help.put("§7Use /blockhunt help [n] to get page n of help.", null);
		// help.put("§6/blockhunt help [n]: §fConsult this help page.",
		// "blockhunt.help");

		int pageNumber;
		int maxPages = (help.size() / 9) + 1;
		int index;
		
		try {
			pageNumber = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			sender.sendMessage("§c'" + args[1] + "' is not a valid number");
			return true;
		}

		if (pageNumber > maxPages) {
			pageNumber = maxPages;
		}

		sender.sendMessage(
				"§9--------- §fBlockHunt: Index (" + pageNumber + "/" + maxPages + ") §9--------------------");

		index = (pageNumber - 1) * 9;
		
		for (String helpLine : help.keySet()) {
			String perm = help.get(helpLine);
			
			if ((perm != null) && !sender.hasPermission(perm)) {
				index--;
				continue;
			}
			
			sender.sendMessage(helpLine);
			
			if (index >= (pageNumber * 9)) {
				break;
			}
		}
		
		return true;
	}
}
