package nl.Steffion.BlockHunt;

import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockHunt extends JavaPlugin implements Listener {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	public void onEnable() {
		W.newFiles();
		getServer().getPluginManager().registerEvents(this, this);
		MessageM.sendFMessage(null, ConfigC.log_Enabled, true, "name-"
				+ W.pluginName, "version-" + W.pluginVersion, "autors-"
				+ W.pluginAutors);
	}

	public void onDisable() {
		MessageM.sendFMessage(null, ConfigC.log_Disabled, true, "name-"
				+ W.pluginName, "version-" + W.pluginVersion, "autors-"
				+ W.pluginAutors);
	}

	/**
	 * Build a string.
	 * 
	 * @param input
	 * @param startArg
	 * @return
	 */
	public static String argsBuild(String[] input, int startArg) {
		if (input.length - startArg <= 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder(input[startArg]);
		for (int i = ++startArg; i < input.length; i++) {
			sb.append(' ').append(input[i]);
		}
		return sb.toString();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		for (CommandC command : CommandC.values()) {
			String[] commandArgsSplit = command.command.split("%");
			String[] argsSplit = commandArgsSplit[1].split("_");
			String[] argsSplitAlias = command.alias.split("%")[1].split("_");

			if (cmd.getName().equalsIgnoreCase(commandArgsSplit[0])) {
				int i = 0;
				boolean equals = true;

				if (command.minLenght == 0) {
					if (args.length == 0) {
						equals = true;
					} else {
						equals = false;
					}
				} else {
					if (args.length >= command.minLenght) {
						for (String arg : argsSplit) {
							for (String arga : argsSplitAlias) {
								if (!arg.equalsIgnoreCase(args[i])
										&& !arga.equalsIgnoreCase(args[i])) {
									equals = false;
								}
								i = i + 1;
							}
						}
					} else {
						equals = false;
					}
				}

				if (equals) {
					if (W.config.getFile().getBoolean(
							command.enabled.getLocation())) {
						command.cmd.exectue(player, cmd, label, args);
					} else {
						MessageM.sendFMessage(player,
								ConfigC.error_commandNotEnabled, true);
					}

					return true;
				}
			}
		}
		CommandC.NOT_FOUND.cmd.exectue(player, cmd, label, args);
		return true;
	}
}
