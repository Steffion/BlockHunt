package nl.Steffion.BlockHunt.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nl.Steffion.BlockHunt.data.Arena;

public class CommandEdit extends Command {
	
	public CommandEdit() {
		super("blockhunt edit <arena>", "blockhunt.edit", true, "Edit an arena.");
	}
	
	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if (plugin.getArenaHandler().getAllEditors().contains(player)) {
			player.sendMessage("§cYou are already editing an arena!");
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
		
		if (plugin.getArenaHandler().getArena(arenaName) == null) {
			player.sendMessage("§cNo arena exists with the name '" + arenaName + "'");
			return true;
		}

		Arena arena = plugin.getArenaHandler().getArena(arenaName);
		
		if (arena.getEditor() != null) {
			player.sendMessage("§c" + arena.getEditor().getName() + " is already editing this arena!");
			return true;
		}
		
		plugin.storePlayerData(player);
		plugin.getPlayerData(player).clear();
		plugin.getArenaHandler().setEditor(arena, player);

		player.setAllowFlight(true);

		if (arena.getHidersSpawn() != null) {
			player.teleport(arena.getHidersSpawn());
		}

		ItemMeta im;
		ArrayList<String> lore = new ArrayList<String>();
		
		/*
		 * Naming tool
		 */
		ItemStack namingTool = new ItemStack(Material.NAME_TAG);
		im = namingTool.getItemMeta();
		im.setDisplayName("§7Rename arena");
		lore.clear();
		lore.add("§7Right-click to edit the arena name.");
		im.setLore(lore);
		namingTool.setItemMeta(im);
		player.getInventory().setItem(0, namingTool);
		
		/*
		 * Hiders spawn item
		 */
		ItemStack hidersSpawn = new ItemStack(Material.WOOL, 1, (short) 5);
		im = hidersSpawn.getItemMeta();
		im.setDisplayName("§aHider's spawn");
		lore.clear();
		lore.add("§7Place this block to set the hider's spawn.");
		lore.add("§cThis is a required setting.");
		im.setLore(lore);
		hidersSpawn.setItemMeta(im);
		player.getInventory().setItem(1, hidersSpawn);
		
		/*
		 * Lobby location item
		 */
		ItemStack lobbyLocation = new ItemStack(Material.WOOL, 1, (short) 13);
		im = lobbyLocation.getItemMeta();
		im.setDisplayName("§2Lobby location");
		lore.clear();
		lore.add("§7Place this block to set the lobby location.");
		lore.add("§cThis is a required setting.");
		im.setLore(lore);
		lobbyLocation.setItemMeta(im);
		player.getInventory().setItem(2, lobbyLocation);
		
		/*
		 * Seekers spawn item
		 */
		ItemStack seekersSpawn = new ItemStack(Material.WOOL, 1, (short) 4);
		im = seekersSpawn.getItemMeta();
		im.setDisplayName("§eSeeker's spawn");
		lore.clear();
		lore.add("§7Place this block to set the seeker's spawn.");
		lore.add("§cThis is a required setting.");
		im.setLore(lore);
		seekersSpawn.setItemMeta(im);
		player.getInventory().setItem(3, seekersSpawn);
		
		/*
		 * Remove item
		 */
		ItemStack delete = new ItemStack(Material.BARRIER);
		im = delete.getItemMeta();
		im.setDisplayName("§c§lDELETE ARENA IMMEDIATELY");
		lore.clear();
		lore.add("§7Right-click to delete the arena §c§lIMMEDIATELY.");
		lore.add("§c§lTHERE IS NO RETURN!");
		im.setLore(lore);
		delete.setItemMeta(im);
		player.getInventory().setItem(7, delete);
		
		/*
		 * Exit item
		 */
		ItemStack exit = new ItemStack(Material.WOOD_DOOR);
		im = exit.getItemMeta();
		im.setDisplayName("§cExit editor mode");
		lore.clear();
		lore.add("§7Right-click to exit the editor mode.");
		im.setLore(lore);
		exit.setItemMeta(im);
		player.getInventory().setItem(8, exit);

		player.sendMessage("You're now editing arena: '" + arena.getName() + "'.");
		player.sendMessage("Use the tools in your inventory to configure the arena.");
		player.sendMessage("Hover the items in your inventory to get help.");
		player.sendMessage("The experiance bar will fill up when you set required settings.");
		return true;
	}
	
}
