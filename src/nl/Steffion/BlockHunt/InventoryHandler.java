package nl.Steffion.BlockHunt;

import java.util.ArrayList;
import java.util.List;

import nl.Steffion.BlockHunt.Arena.ArenaType;
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
							54,
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

			ItemStack disguiseBlocks_NOTE = new ItemStack(Material.BOOK, 1);
			ItemMeta disguiseBlocks_NOTE_IM = disguiseBlocks_NOTE.getItemMeta();
			disguiseBlocks_NOTE_IM.setDisplayName(MessageM
					.replaceAll("%NSet the %AdisguiseBlocks%N."));
			disguiseBlocks_NOTE.setItemMeta(disguiseBlocks_NOTE_IM);
			panel.setItem(37, disguiseBlocks_NOTE);

			ItemStack timeUntilHidersSword_UP = new ItemStack(
					Material.GOLD_NUGGET, 1);
			ItemStack timeUntilHidersSword = new ItemStack(
					Material.PISTON_MOVING_PIECE, arena.timeUntilHidersSword);
			ItemStack timeUntilHidersSword_DOWN = new ItemStack(
					Material.GOLD_NUGGET, 1);

			ItemStack hidersTokenWin_UP = new ItemStack(Material.GOLD_NUGGET, 1);
			ItemStack hidersTokenWin = new ItemStack(
					Material.PISTON_MOVING_PIECE, arena.hidersTokenWin);
			ItemStack hidersTokenWin_DOWN = new ItemStack(Material.GOLD_NUGGET,
					1);

			ItemStack seekersTokenWin_UP = new ItemStack(Material.GOLD_NUGGET,
					1);
			ItemStack seekersTokenWin = new ItemStack(
					Material.PISTON_MOVING_PIECE, arena.seekersTokenWin);
			ItemStack seekersTokenWin_DOWN = new ItemStack(
					Material.GOLD_NUGGET, 1);

			ItemStack killTokens_UP = new ItemStack(Material.GOLD_NUGGET, 1);
			ItemStack killTokens = new ItemStack(Material.PISTON_MOVING_PIECE,
					arena.killTokens);
			ItemStack killTokens_DOWN = new ItemStack(Material.GOLD_NUGGET, 1);

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

			updownButton(panel, arena, ArenaType.timeUntilHidersSword,
					"timeUntilHidersSword", "1 %Nsecond",
					timeUntilHidersSword_UP, timeUntilHidersSword,
					timeUntilHidersSword_DOWN, 30, 39, 48);

			updownButton(panel, arena, ArenaType.hidersTokenWin,
					"hidersTokenWin", "1 %Ntoken", hidersTokenWin_UP,
					hidersTokenWin, hidersTokenWin_DOWN, 32, 41, 50);

			updownButton(panel, arena, ArenaType.seekersTokenWin,
					"seekersTokenWin", "1 %Ntoken", seekersTokenWin_UP,
					seekersTokenWin, seekersTokenWin_DOWN, 33, 42, 51);

			updownButton(panel, arena, ArenaType.killTokens, "killTokens",
					"1 %Ntoken", killTokens_UP, killTokens, killTokens_DOWN,
					34, 43, 52);

			player.openInventory(panel);
		} else {
			MessageM.sendFMessage(player, ConfigC.error_noArena, "name-"
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
		case timeUntilHidersSword:
			setting = arena.timeUntilHidersSword;
			break;
		case hidersTokenWin:
			setting = arena.hidersTokenWin;
			break;
		case seekersTokenWin:
			setting = arena.seekersTokenWin;
			break;
		case killTokens:
			setting = arena.killTokens;
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

	public static void openDisguiseBlocks(Arena arena, Player player) {
		String arenaname = arena.arenaName;
		Inventory panel = Bukkit.createInventory(null, 36,
				MessageM.replaceAll("%N&lDisguiseBlocks"));

		ItemStack arenaNameNote = new ItemStack(Material.PAPER, 1);
		ItemMeta arenaNameNote_IM = arenaNameNote.getItemMeta();
		arenaNameNote_IM.setDisplayName(MessageM
				.replaceAll("%NDisguiseBlocks of arena: %A" + arenaname));
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(MessageM
				.replaceAll("%NPlace the DisguiseBlocks inside this inventory."));
		arenaNameNote_IM.setLore(lores);
		arenaNameNote.setItemMeta(arenaNameNote_IM);
		panel.setItem(0, arenaNameNote);
		if (arena.disguiseBlocks != null) {
			for (int i = arena.disguiseBlocks.size(); i > 0; i = i - 1) {
				panel.setItem(i, arena.disguiseBlocks.get(i - 1));
			}
		}
		player.openInventory(panel);
	}

	public static void openShop(Player player) {
		Inventory shop = Bukkit.createInventory(
				null,
				9,
				MessageM.replaceAll("\u00A7r"
						+ W.config.get(ConfigC.shop_title)));
		if (W.shop.getFile().get(player.getName() + ".tokens") == null) {
			W.shop.getFile().set(player.getName() + ".tokens", 0);
			W.shop.save();
		}
		int playerTokens = W.shop.getFile()
				.getInt(player.getName() + ".tokens");
		List<String> lores = new ArrayList<String>();
		List<String> lores2 = new ArrayList<String>();

		ItemStack shopTokens = new ItemStack(Material.EMERALD, 1);
		ItemMeta shopTokens_IM = shopTokens.getItemMeta();
		shopTokens_IM.setDisplayName(MessageM.replaceAll("%N&lTokens: %A"
				+ playerTokens));
		shopTokens.setItemMeta(shopTokens_IM);

		ItemStack shopBlockChooser = new ItemStack(
				Material.getMaterial((String) W.config
						.get(ConfigC.shop_blockChooserIDname)), 1);
		ItemMeta shopBlockChooser_IM = shopBlockChooser.getItemMeta();
		shopBlockChooser_IM.setDisplayName(MessageM
				.replaceAll((String) W.config
						.get(ConfigC.shop_blockChooserName)));
		lores = W.config.getFile().getStringList(
				ConfigC.shop_blockChooserDescription.location);
		lores2 = new ArrayList<String>();
		for (String lore : lores) {
			lores2.add(MessageM.replaceAll(lore));
		}

		lores2.add(MessageM.replaceAll(
				(String) W.config.get(ConfigC.shop_price),
				"amount-" + W.config.get(ConfigC.shop_blockChooserPrice)));

		shopBlockChooser_IM.setLore(lores2);
		shopBlockChooser.setItemMeta(shopBlockChooser_IM);

		ItemStack shopBlockHuntPass = new ItemStack(
				Material.getMaterial((String) W.config
						.get(ConfigC.shop_BlockHuntPassIDname)), 1);
		ItemMeta shopBlockHuntPass_IM = shopBlockHuntPass.getItemMeta();
		shopBlockHuntPass_IM.setDisplayName(MessageM
				.replaceAll((String) W.config
						.get(ConfigC.shop_BlockHuntPassName)));
		lores = W.config.getFile().getStringList(
				ConfigC.shop_BlockHuntPassDescription.location);
		lores2 = new ArrayList<String>();
		for (String lore : lores) {
			lores2.add(MessageM.replaceAll(lore));
		}

		lores2.add(MessageM.replaceAll(
				(String) W.config.get(ConfigC.shop_price),
				"amount-" + W.config.get(ConfigC.shop_BlockHuntPassPrice)));

		shopBlockHuntPass_IM.setLore(lores2);
		shopBlockHuntPass.setItemMeta(shopBlockHuntPass_IM);

		shop.setItem(0, shopTokens);
		if ((Boolean) W.config.get(ConfigC.shop_blockChooserEnabled) == true
				&& (Boolean) W.shop.getFile().get(
						player.getName() + ".blockchooser") == null) {
			shop.setItem(1, shopBlockChooser);
		}
		if ((Boolean) W.config.get(ConfigC.shop_BlockHuntPassEnabled) == true
				&& (Boolean) W.shop.getFile().get(
						player.getName() + ".blockhuntpass") == null) {
			shop.setItem(2, shopBlockHuntPass);
		}
		player.openInventory(shop);
	}
}
