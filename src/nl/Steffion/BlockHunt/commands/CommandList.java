package nl.Steffion.BlockHunt.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import nl.Steffion.BlockHunt.data.Arena;

/**
 * 
 * @author Steffion (Stef de Goey) 2016
 *
 */
public class CommandList extends Command {

	public CommandList() {
		super("blockhunt list", "blockhunt.list", false, "Shows a list of arenas.");
	}
	
	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		List<String> arenas = new ArrayList<String>();

		for (Arena arena : plugin.getArenaHandler().getArenas()) {
			if (arena.isSetup()) {
				arenas.add(arena.getName());
			} else {
				arenas.add("§7§o" + arena.getName() + "§r");
			}
		}

		Collections.sort(arenas);

		String line = "";

		sender.sendMessage("§9--------- §fBlockHunt: Arenas §9--------------------");

		for (String arenaName : arenas) {
			line += arenaName + ", ";
		}

		if (arenas.size() == 0) {
			sender.sendMessage("§7§oNo arenas found...");
		} else {
			sender.sendMessage(line.substring(0, line.length() - 2));
		}

		return true;
	}

}
