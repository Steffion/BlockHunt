package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDhelp extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.help, true)) {
			int amountCommands = 0;
			for (CommandC command : CommandC.values()) {
				if (command.usage != "-") {
					amountCommands = amountCommands + 1;
				}
			}

			int maxPages = Math.round(amountCommands / 3);
			if (maxPages <= 0) {
				maxPages = 1;
			}

			if (args.length == 1) {
				int page = 1;
				MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
						"header-" + W.pluginName + " %Nhelp page %A" + page
								+ "%N/%A" + maxPages);
				int i = 1;
				for (CommandC command : CommandC.values()) {
					if (i <= 4) {
						if (command.usage != "-") {
							if (PlayerM.hasPerm(player, command.perm, false)) {
								MessageM.sendMessage(
										player,
										"%A/"
												+ command.usage
												+ "%N - "
												+ W.messages.getFile().get(
														command.help
																.getLocation()),
										false);
							} else {
								MessageM.sendMessage(
										player,
										"%W/"
												+ command.usage
												+ "%N - "
												+ W.messages.getFile().get(
														command.help
																.getLocation()),
										false);
							}
							i = i + 1;
						}
					}
				}

				MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
						"header-&oHelp Page");
			} else {
				int page = 1;
				try {
					page = Integer.valueOf(args[1]);
				} catch (NumberFormatException e) {
					page = 1;
				}

				if (maxPages < page) {
					maxPages = page;
				}

				MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
						"header-" + W.pluginName + " %Nhelp page %A" + page
								+ "%N/%A" + maxPages);
				int i = 1;
				for (CommandC command : CommandC.values()) {
					if (i <= (page * 4) + 4) {
						if (command.usage != "-") {
							if (i >= ((page - 1) * 4) + 1
									&& i <= ((page - 1) * 4) + 4) {
								if (PlayerM
										.hasPerm(player, command.perm, false)) {
									MessageM.sendMessage(
											player,
											"%A/"
													+ command.usage
													+ "%N - "
													+ W.messages
															.getFile()
															.get(command.help
																	.getLocation()),
											false);
								} else {
									MessageM.sendMessage(
											player,
											"%W/"
													+ command.usage
													+ "%N - "
													+ W.messages
															.getFile()
															.get(command.help
																	.getLocation()),
											false);
								}
							}
							i = i + 1;
						}
					}
				}

				MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
						"header-&oHelp Page");
			}
		}
		return true;
	}
}
