package nl.Steffion.BlockHunt;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockHunt extends JavaPlugin {

	public static final boolean DEBUG_MODE = true;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("blockhunt")) {
			return false;
		}
		
		if (args.length == 0) {
			Bukkit.dispatchCommand(sender, "blockhunt help");

			return true;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("help")) {
				Bukkit.dispatchCommand(sender, "blockhunt help 1");
				
				return true;
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("help")) {
				int pageNumber;
				int maxPages = 1;
				
				try {
					pageNumber = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					sender.sendMessage("§c'" + args[1] + "' is not a valid number");
					return true;
				}

				if (pageNumber > maxPages) {
					pageNumber = maxPages;
				}

				sender.sendMessage("§9--------- §fBlockHunt: Index (" + pageNumber + "/1) §9--------------------");
				sender.sendMessage("§7Use /blockhunt help [n] to get page n of help.");
				// sender.sendMessage("§6/blockhunt help [n]: §fConsult this
				// help page");
				
				return true;
			}
		}

		return false;
	}

	@Override
	public void onDisable() {
		getLogger().log(Level.INFO, "BlockHunt has succesfully been disabled.");
	}
	
	@Override
	public void onEnable() {
		if (BlockHunt.DEBUG_MODE) {
			for (World world : Bukkit.getWorlds()) {
				world.setTime(200);
				world.setStorm(false);
			}
		}

		getLogger().log(Level.INFO, "BlockHunt has succesfully been loaded!");
	}
	
}
