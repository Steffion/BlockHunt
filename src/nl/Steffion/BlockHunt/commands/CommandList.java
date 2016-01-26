package nl.Steffion.BlockHunt.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

public class CommandList extends Command {

	public CommandList() {
		super("blockhunt list", "blocklist.list", false, "Shows a list of arenas.");
	}
	
	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		List<String> arenas = new ArrayList<String>();

		for (Object arena : plugin.getArenas().getConfig().getKeys(false).toArray()) {
			arenas.add(arena.toString());
		}

		Collections.sort(arenas);

		String line = "";
		boolean first = true;
		int amountOnLine = 0;

		sender.sendMessage("§9--------- §fBlockHunt: Arenas §9--------------------");

		for (String arenaName : arenas) {
			if (first) {
				line = arenaName;
				first = false;
			} else {
				line = line + ", " + arenaName;
			}
			
			amountOnLine++;

			if (amountOnLine == 5) {
				sender.sendMessage(line);
				line = "";
				first = true;
				amountOnLine = 0;
			}
		}

		if ((line != null) && !line.isEmpty()) {
			sender.sendMessage(line);
		}

		if (arenas.size() == 0) {
			sender.sendMessage("§7§oNo arenas found...");
		}

		return true;
	}

}
