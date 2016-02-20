package nl.Steffion.BlockHunt.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nl.Steffion.BlockHunt.data.Arena;

public class CommandJoin extends Command {
	
	public CommandJoin() {
		super("blockhunt join <arena>", "blockhunt.join", true, "Join an arena.");
	}

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if (plugin.getArenaHandler().getAllEditors().contains(player)) {
			player.sendMessage("§cYou are already editing an arena!");
			return true;
		}
		
		if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
			player.sendMessage("§cYou are already playing in an arena!");
			return true;
		}
		
		if (args.length < 2) {
			player.sendMessage("§cUsage: /" + getUsage());
			return true;
		}
		
		String arenaName = "";

		for (int i = 1; i < args.length; i++) {
			arenaName = arenaName + args[i] + " ";
		}
		
		arenaName = arenaName.substring(0, arenaName.length() - 1);
		
		Arena arena = null;

		for (Arena possibleArena : plugin.getArenaHandler().getArenas()) {
			if (possibleArena.getName().equalsIgnoreCase(arenaName)) {
				arena = possibleArena;
			}
		}

		if (arena == null) {
			player.sendMessage("§cNo arena exists with the name '" + arenaName + "'");
			return true;
		}
		
		if (!arena.isSetup()) {
			player.sendMessage("§cThis arena is not completely setup.");
			return true;
		}
		
		if (arena.hasStarted()) {
			player.sendMessage("§cThis arena is already in-game!");
			return true;
		}
		
		arena.addPlayer(player);
		
		ItemMeta im;
		ArrayList<String> lore = new ArrayList<String>();
		
		/*
		 * Exit item
		 */
		ItemStack exit = new ItemStack(Material.WOOD_DOOR);
		im = exit.getItemMeta();
		im.setDisplayName("§cExit arena");
		lore.clear();
		lore.add("§7Right-click to exit the arena.");
		im.setLore(lore);
		exit.setItemMeta(im);
		player.getInventory().setItem(8, exit);
		
		player.sendMessage("You've joined '" + arena.getName() + "'.");
		return true;
	}
	
}
