package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.InventoryHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OnInventoryClickEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		if (inv.getType().equals(InventoryType.CHEST)) {
			if (inv.getName().startsWith("\u00A7r")) {
				event.setCancelled(true);
				ItemStack item = event.getCurrentItem();
				String arenaname = inv.getName().replaceAll(
						MessageM.replaceAll("\u00A7r%N&lSettings of: %A"), "");
				if (item == null)
					return;
				if (item.getType().equals(Material.AIR))
					return;
				if (!item.getItemMeta().hasDisplayName())
					return;
				if (item.getType().equals(Material.GOLD_NUGGET)) {
					if (item.getItemMeta().getDisplayName()
							.contains("maxPlayers")) {
						updownButton(item, arenaname, player, "maxPlayers",
								Bukkit.getMaxPlayers(), 2, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("minPlayers")) {
						updownButton(item, arenaname, player, "minPlayers",
								Bukkit.getMaxPlayers() - 1, 2, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("amountSeekersOnStart")) {
						updownButton(item, arenaname, player,
								"amountSeekersOnStart", W.arenas.getFile()
										.getInt(arenaname + ".maxPlayers") - 1,
								1, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("timeInLobbyUntilStart")) {
						updownButton(item, arenaname, player,
								"timeInLobbyUntilStart", 1000, 5, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("waitingTimeSeeker")) {
						updownButton(item, arenaname, player,
								"waitingTimeSeeker", 1000, 5, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("gameTime")) {
						updownButton(item, arenaname, player, "gameTime", 1000,
								5, 1, 1);
					}

					W.arenas.save();
					InventoryHandler.openPanel(player, arenaname);
				}
			}
		}
	}

	public static void updownButton(ItemStack item, String arenaname,
			Player player, String option, int max, int min, int add, int remove) {
		if (item.getItemMeta().getDisplayName()
				.contains((String) W.messages.get(ConfigC.button_add2))) {
			if (W.arenas.getFile().getInt(arenaname + "." + option) < max) {
				W.arenas.getFile().set(
						arenaname + "." + option,
						W.arenas.getFile().getInt(arenaname + "." + option)
								+ add);
			} else {
				MessageM.sendFMessage(player, ConfigC.error_tooHighNumber,
						true, "max-" + max);
			}
		} else if (item.getItemMeta().getDisplayName()
				.contains((String) W.messages.get(ConfigC.button_remove2))) {
			if (W.arenas.getFile().getInt(arenaname + "." + option) > min) {
				W.arenas.getFile().set(
						arenaname + "." + option,
						W.arenas.getFile().getInt(arenaname + "." + option)
								- remove);
			} else {
				MessageM.sendFMessage(player, ConfigC.error_tooLowNumber, true,
						"min-" + min);
			}
		}
	}
}
