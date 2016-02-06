package nl.Steffion.BlockHunt.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import nl.Steffion.BlockHunt.data.Arena;

public class CommandList extends Command {

	public CommandList() {
		super("blockhunt list", "blocklist.list", false, "Shows a list of arenas.");
	}
	
	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		List<String> arenas = new ArrayList<String>();

		for (Arena arena : plugin.getArenaHandler().getArenas()) {
			arenas.add(arena.getName());
		}

		Collections.sort(arenas);

		String line = "";
		int amountOnLine = 0;

		sender.sendMessage("§9--------- §fBlockHunt: Arenas §9--------------------");

		for (String arenaName : arenas) {
			line += arenaName + ", ";
			
			amountOnLine++;

			if (amountOnLine == 5) {
				sender.sendMessage(line);
				line = "";
				amountOnLine = 0;
			}
		}

		if ((line != null) && !line.isEmpty()) {
			sender.sendMessage(line.substring(0, line.length() - 2));
		}

		if (arenas.size() == 0) {
			sender.sendMessage("§7§oNo arenas found...");
		}

		return true;
	}

}
