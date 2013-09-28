package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDtokens extends DefaultCMD {
	/**
	 * Steffion's Engine - Made by Steffion.
	 * 
	 * You're allowed to use this engine for own usage, you're not allowed to
	 * republish the engine. Using this for your own plugin is allowed when a
	 * credit is placed somewhere in the plugin.
	 * 
	 * Thanks for your cooperate!
	 * 
	 * @author Steffion
	 */

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (args.length <= 3) {
			MessageM.sendFMessage(player, ConfigC.error_notEnoughArguments,
					"syntax-" + BlockHunt.CMDtokens.usage);
		} else {
			String option = args[1];
			String playerName = args[2];
			int amount = 0;
			try {
				amount = Integer.valueOf(args[3]);
			} catch (NumberFormatException e) {
				MessageM.sendFMessage(player, ConfigC.error_notANumber, "1-"
						+ args[3]);
				return true;
			}

			Player tokenPlayer = Bukkit.getPlayer(playerName);
			if (tokenPlayer == null) {
				MessageM.sendFMessage(player,
						ConfigC.error_tokensPlayerNotOnline, "playername-"
								+ playerName);
				return true;
			}

			if (option.equalsIgnoreCase("set")) {
				W.shop.getFile().set(tokenPlayer.getName() + ".tokens", amount);
				W.shop.save();
				MessageM.sendFMessage(player, ConfigC.normal_tokensChanged,
						"option-Set", "playername-" + tokenPlayer.getName(),
						"option2-to", "amount-" + amount);
			} else if (option.equalsIgnoreCase("add")) {
				int tokens = 0;
				if (W.shop.getFile().getInt(tokenPlayer.getName() + ".tokens") != 0) {
					tokens = W.shop.getFile().getInt(
							tokenPlayer.getName() + ".tokens");
				}
				W.shop.getFile().set(tokenPlayer.getName() + ".tokens",
						tokens + amount);
				W.shop.save();
				MessageM.sendFMessage(player, ConfigC.normal_tokensChanged,
						"option-Added", "playername-" + tokenPlayer.getName(),
						"option2-to", "amount-" + amount);
			} else if (option.equalsIgnoreCase("take")) {
				int tokens = 0;
				if (W.shop.getFile().getInt(tokenPlayer.getName() + ".tokens") != 0) {
					tokens = W.shop.getFile().getInt(
							tokenPlayer.getName() + ".tokens");
				}
				W.shop.getFile().set(tokenPlayer.getName() + ".tokens",
						tokens - amount);
				W.shop.save();
				MessageM.sendFMessage(player, ConfigC.normal_tokensChanged,
						"option-Took", "playername-" + tokenPlayer.getName(),
						"option2-from", "amount-" + amount);
			} else {
				MessageM.sendFMessage(player,
						ConfigC.error_tokensUnknownsetting, "option-" + option);
			}
		}
		return true;
	}
}
