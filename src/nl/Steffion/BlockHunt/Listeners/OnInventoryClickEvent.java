package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.Arena.ArenaType;
import nl.Steffion.BlockHunt.ArenaHandler;
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

				Arena arena = null;
				for (Arena arena2 : W.arenaList) {
					if (arena2.arenaName.equalsIgnoreCase(arenaname)) {
						arena = arena2;
					}
				}

				if (item == null)
					return;
				if (item.getType().equals(Material.AIR))
					return;
				if (!item.getItemMeta().hasDisplayName())
					return;
				if (item.getType().equals(Material.GOLD_NUGGET)) {
					if (item.getItemMeta().getDisplayName()
							.contains("maxPlayers")) {
						updownButton(player, item, arena, ArenaType.maxPlayers,
								arena.maxPlayers, Bukkit.getMaxPlayers(), 2, 1,
								1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("minPlayers")) {
						updownButton(player, item, arena, ArenaType.minPlayers,
								arena.minPlayers, Bukkit.getMaxPlayers() - 1,
								2, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("amountSeekersOnStart")) {
						updownButton(player, item, arena,
								ArenaType.amountSeekersOnStart,
								arena.amountSeekersOnStart,
								arena.maxPlayers - 1, 1, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("timeInLobbyUntilStart")) {
						updownButton(player, item, arena,
								ArenaType.timeInLobbyUntilStart,
								arena.timeInLobbyUntilStart, 1000, 5, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("waitingTimeSeeker")) {
						updownButton(player, item, arena,
								ArenaType.waitingTimeSeeker,
								arena.waitingTimeSeeker, 1000, 5, 1, 1);
					} else if (item.getItemMeta().getDisplayName()
							.contains("gameTime")) {
						updownButton(player, item, arena, ArenaType.gameTime,
								arena.gameTime, 1000, 5, 1, 1);
					}

					save(arena);
					InventoryHandler.openPanel(player, arena.arenaName);
				}
			}
		}
	}

	public void save(Arena arena) {
		W.arenas.getFile().set(arena.arenaName, arena);
		W.arenas.save();
		ArenaHandler.loadArenas();
	}

	public static void updownButton(Player player, ItemStack item, Arena arena,
			ArenaType at, int option, int max, int min, int add, int remove) {
		if (item.getItemMeta().getDisplayName()
				.contains((String) W.messages.get(ConfigC.button_add2))) {
			if (option < max) {

				// W.arenas.getFile().set(arenaname + "." + option, option +
				// add);
				switch (at) {
				case maxPlayers:
					arena.maxPlayers = option + add;
					break;
				case minPlayers:
					arena.minPlayers = option + add;
					break;
				case amountSeekersOnStart:
					arena.amountSeekersOnStart = option + add;
					break;
				case timeInLobbyUntilStart:
					arena.timeInLobbyUntilStart = option + add;
					break;
				case waitingTimeSeeker:
					arena.waitingTimeSeeker = option + add;
					break;
				case gameTime:
					arena.gameTime = option + add;
					break;
				}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_setTooHighNumber,
						true, "max-" + max);
			}
		} else if (item.getItemMeta().getDisplayName()
				.contains((String) W.messages.get(ConfigC.button_remove2))) {
			if (option > min) {
				// W.arenas.getFile().set(arenaname + "." + option,
				// option - remove);
				switch (at) {
				case maxPlayers:
					arena.maxPlayers = option - remove;
					break;
				case minPlayers:
					arena.minPlayers = option - remove;
					break;
				case amountSeekersOnStart:
					arena.amountSeekersOnStart = option - remove;
					break;
				case timeInLobbyUntilStart:
					arena.timeInLobbyUntilStart = option - remove;
					break;
				case waitingTimeSeeker:
					arena.waitingTimeSeeker = option - remove;
					break;
				case gameTime:
					arena.gameTime = option - remove;
					break;
				}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_setTooLowNumber,
						true, "min-" + min);
			}
		}
	}
}
