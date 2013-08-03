package nl.Steffion.BlockHunt;

import nl.Steffion.BlockHunt.Arena.ArenaType;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryHandler {

	public static void openPanel(Player player, String arenaname) {

		Arena arena = null;
		for (Arena arena2 : W.arenaList) {
			if (arena2.arenaName.equalsIgnoreCase(arenaname)) {
				arena = arena2;
			}
		}

		if (arena != null) {
			String shorten = arena.arenaName;
			arenaname = arena.arenaName;
			if (shorten.length() > 6)
				shorten = shorten.substring(0, 6);
			Inventory panel = Bukkit
					.createInventory(
							null,
							27,
							MessageM.replaceAll("\u00A7r%N&lSettings of: %A"
									+ shorten));

			ItemStack arenaNameNote = new ItemStack(Material.PAPER, 1);
			ItemMeta arenaNameNote_IM = arenaNameNote.getItemMeta();
			arenaNameNote_IM.setDisplayName(MessageM
					.replaceAll("%NSettings of arena: %A" + arena.arenaName));
			arenaNameNote.setItemMeta(arenaNameNote_IM);
			panel.setItem(0, arenaNameNote);

			//

			ItemStack maxPlayers_UP = new ItemStack(Material.GOLD_NUGGET, 1);
			ItemStack maxPlayers = new ItemStack(Material.PISTON_MOVING_PIECE,
					arena.maxPlayers);
			ItemStack maxPlayers_DOWN = new ItemStack(Material.GOLD_NUGGET, 1);

			ItemStack minPlayers_UP = new ItemStack(Material.GOLD_NUGGET, 1);
			ItemStack minPlayers = new ItemStack(Material.PISTON_MOVING_PIECE,
					arena.minPlayers);
			ItemStack minPlayers_DOWN = new ItemStack(Material.GOLD_NUGGET, 1);

			ItemStack amountSeekersOnStart_UP = new ItemStack(
					Material.GOLD_NUGGET, 1);
			ItemStack amountSeekersOnStart = new ItemStack(
					Material.PISTON_MOVING_PIECE, arena.amountSeekersOnStart);
			ItemStack amountSeekersOnStart_DOWN = new ItemStack(
					Material.GOLD_NUGGET, 1);

			ItemStack timeInLobbyUntilStart_UP = new ItemStack(
					Material.GOLD_NUGGET, 1);
			ItemStack timeInLobbyUntilStart = new ItemStack(
					Material.PISTON_MOVING_PIECE, arena.timeInLobbyUntilStart);
			ItemStack timeInLobbyUntilStart_DOWN = new ItemStack(
					Material.GOLD_NUGGET, 1);

			ItemStack waitingTimeSeeker_UP = new ItemStack(
					Material.GOLD_NUGGET, 1);
			ItemStack waitingTimeSeeker = new ItemStack(
					Material.PISTON_MOVING_PIECE, arena.waitingTimeSeeker);
			ItemStack waitingTimeSeeker_DOWN = new ItemStack(
					Material.GOLD_NUGGET, 1);

			ItemStack gameTime_UP = new ItemStack(Material.GOLD_NUGGET, 1);
			ItemStack gameTime = new ItemStack(Material.PISTON_MOVING_PIECE,
					arena.gameTime);
			ItemStack gameTime_DOWN = new ItemStack(Material.GOLD_NUGGET, 1);

			//

			updownButton(panel, arena, ArenaType.maxPlayers, "maxPlayers", "1",
					maxPlayers_UP, maxPlayers, maxPlayers_DOWN, 1, 10, 19);

			updownButton(panel, arena, ArenaType.minPlayers, "minPlayers", "1",
					minPlayers_UP, minPlayers, minPlayers_DOWN, 2, 11, 20);

			updownButton(panel, arena, ArenaType.amountSeekersOnStart,
					"amountSeekersOnStart", "1", amountSeekersOnStart_UP,
					amountSeekersOnStart, amountSeekersOnStart_DOWN, 4, 13, 22);

			updownButton(panel, arena, ArenaType.timeInLobbyUntilStart,
					"timeInLobbyUntilStart", "1 %Nsecond",
					timeInLobbyUntilStart_UP, timeInLobbyUntilStart,
					timeInLobbyUntilStart_DOWN, 6, 15, 24);

			updownButton(panel, arena, ArenaType.waitingTimeSeeker,
					"waitingTimeSeeker", "1 %Nsecond", waitingTimeSeeker_UP,
					waitingTimeSeeker, waitingTimeSeeker_DOWN, 7, 16, 25);

			updownButton(panel, arena, ArenaType.gameTime, "gameTime",
					"1 %Nsecond", gameTime_UP, gameTime, gameTime_DOWN, 8, 17,
					26);

			player.openInventory(panel);
		} else {
			MessageM.sendFMessage(player, ConfigC.error_noArena, true, "name-"
					+ arenaname);
		}
	}

	public static void updownButton(Inventory panel, Arena arena, ArenaType at,
			String option, String addremove, ItemStack UP, ItemStack BUTTON,
			ItemStack DOWN, int up, int button, int down) {
		ItemMeta UP_IM = UP.getItemMeta();
		UP_IM.setDisplayName(MessageM.replaceAll(
				(String) W.messages.get(ConfigC.button_add), "1-" + addremove,
				"2-" + option));
		UP.setItemMeta(UP_IM);

		int setting = 0;
		switch (at) {
		case maxPlayers:
			setting = arena.maxPlayers;
			break;
		case minPlayers:
			setting = arena.minPlayers;
			break;
		case amountSeekersOnStart:
			setting = arena.amountSeekersOnStart;
			break;
		case timeInLobbyUntilStart:
			setting = arena.timeInLobbyUntilStart;
			break;
		case waitingTimeSeeker:
			setting = arena.waitingTimeSeeker;
			break;
		case gameTime:
			setting = arena.gameTime;
			break;
		}

		ItemMeta BUTTON_IM = BUTTON.getItemMeta();
		BUTTON_IM.setDisplayName(MessageM.replaceAll(
				(String) W.messages.get(ConfigC.button_setting), "1-" + option,
				"2-" + setting));
		BUTTON.setItemMeta(BUTTON_IM);

		ItemMeta DOWN_IM = DOWN.getItemMeta();
		DOWN_IM.setDisplayName(MessageM.replaceAll(
				(String) W.messages.get(ConfigC.button_remove), "1-"
						+ addremove, "2-" + option));
		DOWN.setItemMeta(DOWN_IM);

		panel.setItem(up, UP);
		panel.setItem(button, BUTTON);
		panel.setItem(down, DOWN);
	}
}
