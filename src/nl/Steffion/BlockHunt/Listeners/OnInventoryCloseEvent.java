package nl.Steffion.BlockHunt.Listeners;

import java.util.ArrayList;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OnInventoryCloseEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryCloseEvent(InventoryCloseEvent event) {
		Inventory inv = event.getInventory();
		if (inv.getType().equals(InventoryType.CHEST)) {
			if (inv.getName().contains("DisguiseBlocks")) {
				String arenaname = inv
						.getItem(0)
						.getItemMeta()
						.getDisplayName()
						.replaceAll(
								MessageM.replaceAll("%NDisguiseBlocks of arena: %A"),
								"");

				Arena arena = null;
				for (Arena arena2 : W.arenaList) {
					if (arena2.arenaName.equalsIgnoreCase(arenaname)) {
						arena = arena2;
					}
				}

				ArrayList<ItemStack> blocks = new ArrayList<ItemStack>();
				for (ItemStack item : inv.getContents()) {
					if (item != null) {
						if (!item.getType().equals(Material.PAPER)) {
							blocks.add(item);
						}
					}
				}

				arena.disguiseBlocks = blocks;
				save(arena);
			}
		}
	}

	public void save(Arena arena) {
		W.arenas.getFile().set(arena.arenaName, arena);
		W.arenas.save();
		ArenaHandler.loadArenas();
	}
}
