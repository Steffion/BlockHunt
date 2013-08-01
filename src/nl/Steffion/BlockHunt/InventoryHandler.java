package nl.Steffion.BlockHunt;

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
		String shorten = arenaname;
		if (W.arenas.getFile().getString(arenaname) != null) {
			if (shorten.length() > 6)
				shorten = shorten.substring(0, 6);
			Inventory panel = Bukkit
					.createInventory(
							null,
							27,
							MessageM.replaceAll("\u00A7r%N&lSettings of: %A"
									+ shorten));
			ItemStack maxPlayers_UP = new ItemStack(Material.GOLD_NUGGET, 1);
			ItemStack maxPlayers = new ItemStack(Material.PISTON_MOVING_PIECE,
					W.arenas.getFile().getInt(arenaname + ".maxPlayers"));
			ItemStack maxPlayers_DOWN = new ItemStack(Material.GOLD_NUGGET, 1);

			ItemStack minPlayers_UP = new ItemStack(Material.GOLD_NUGGET, 1);
			ItemStack minPlayers = new ItemStack(Material.PISTON_MOVING_PIECE,
					W.arenas.getFile().getInt(arenaname + ".minPlayers"));
			ItemStack minPlayers_DOWN = new ItemStack(Material.GOLD_NUGGET, 1);

			ItemStack amountSeekersOnStart_UP = new ItemStack(
					Material.GOLD_NUGGET, 1);
			ItemStack amountSeekersOnStart = new ItemStack(
					Material.PISTON_MOVING_PIECE, W.arenas.getFile().getInt(
							arenaname + ".amountSeekersOnStart"));
			ItemStack amountSeekersOnStart_DOWN = new ItemStack(
					Material.GOLD_NUGGET, 1);

			ItemStack timeInLobbyUntilStart_UP = new ItemStack(
					Material.GOLD_NUGGET, 1);
			ItemStack timeInLobbyUntilStart = new ItemStack(
					Material.PISTON_MOVING_PIECE, W.arenas.getFile().getInt(
							arenaname + ".timeInLobbyUntilStart"));
			ItemStack timeInLobbyUntilStart_DOWN = new ItemStack(
					Material.GOLD_NUGGET, 1);

			ItemStack waitingTimeSeeker_UP = new ItemStack(
					Material.GOLD_NUGGET, 1);
			ItemStack waitingTimeSeeker = new ItemStack(
					Material.PISTON_MOVING_PIECE, W.arenas.getFile().getInt(
							arenaname + ".waitingTimeSeeker"));
			ItemStack waitingTimeSeeker_DOWN = new ItemStack(
					Material.GOLD_NUGGET, 1);

			ItemStack gameTime_UP = new ItemStack(Material.GOLD_NUGGET, 1);
			ItemStack gameTime = new ItemStack(Material.PISTON_MOVING_PIECE,
					W.arenas.getFile().getInt(arenaname + ".gameTime"));
			ItemStack gameTime_DOWN = new ItemStack(Material.GOLD_NUGGET, 1);

			//

			updownButton(panel, arenaname, "maxPlayers", "1", maxPlayers_UP,
					maxPlayers, maxPlayers_DOWN, 0, 9, 18);

			updownButton(panel, arenaname, "minPlayers", "1", minPlayers_UP,
					minPlayers, minPlayers_DOWN, 1, 10, 19);

			updownButton(panel, arenaname, "amountSeekersOnStart", "1",
					amountSeekersOnStart_UP, amountSeekersOnStart,
					amountSeekersOnStart_DOWN, 3, 12, 21);

			updownButton(panel, arenaname, "timeInLobbyUntilStart",
					"1 %Nsecond", timeInLobbyUntilStart_UP,
					timeInLobbyUntilStart, timeInLobbyUntilStart_DOWN, 5, 14,
					23);

			updownButton(panel, arenaname, "waitingTimeSeeker", "1 %Nsecond",
					waitingTimeSeeker_UP, waitingTimeSeeker,
					waitingTimeSeeker_DOWN, 6, 15, 24);

			updownButton(panel, arenaname, "gameTime", "1 %Nsecond",
					gameTime_UP, gameTime, gameTime_DOWN, 7, 16, 25);

			player.openInventory(panel);
		} else {
			MessageM.sendFMessage(player, ConfigC.error_noArena, true, "name-"
					+ arenaname);
		}
	}

	public static void updownButton(Inventory panel, String arenaname,
			String option, String addremove, ItemStack UP, ItemStack BUTTON,
			ItemStack DOWN, int up, int button, int down) {
		ItemMeta UP_IM = UP.getItemMeta();
		UP_IM.setDisplayName(MessageM.replaceAll(
				(String) W.messages.get(ConfigC.button_add), "1-" + addremove,
				"2-" + option));
		UP.setItemMeta(UP_IM);

		ItemMeta BUTTON_IM = BUTTON.getItemMeta();
		BUTTON_IM.setDisplayName(MessageM.replaceAll(
				(String) W.messages.get(ConfigC.button_setting), "1-" + option,
				"2-" + W.arenas.getFile().getString(arenaname + "." + option)));
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
