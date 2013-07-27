package nl.Steffion.BlockHunt.Commands;

import java.io.File;
import java.util.ArrayList;

import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDlist extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.list, true)) {
			MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
					"header-" + W.pluginName);
			File arenaworldFolder = new File("plugins/" + W.pluginName
					+ "/defaultArenas/");
			ArrayList<String> arenas = new ArrayList<String>();
			for (String file : arenaworldFolder.list()) {
				File arenaCheck = new File(arenaworldFolder, file);
				if (arenaCheck.isDirectory()) {
					arenas.add(arenaCheck.getName());
				}
			}
			if (arenas.size() >= 1) {
				for (String arena : arenas) {
					MessageM.sendMessage(player, "%A" + arena, false);
				}
			} else {
				MessageM.sendMessage(player, "&7&oNo arenas available...",
						false);
				MessageM.sendMessage(player, "&7&oAdd arenas in the '"
						+ W.pluginName + "/defaultArenas/' folder.", false);
				MessageM.sendMessage(
						player,
						"&7&oWhile adding maps, be sure you REMOVE the uid.dat file. To prevent crashes.",
						false);
			}
			MessageM.sendFMessage(player, ConfigC.chat_headerhigh, false,
					"header-&oArenas list");
		}
		return true;
	}

	public static boolean isAnArena(String worldname) {
		File arenaworldFolder = new File("plugins/" + W.pluginName
				+ "/defaultArenas/");

		ArrayList<String> arenas = new ArrayList<String>();
		for (String file : arenaworldFolder.list()) {
			File arenaCheck = new File(arenaworldFolder, file);
			if (arenaCheck.isDirectory()) {
				arenas.add(arenaCheck.getName());
			}
		}

		if (arenas.contains(worldname)) {
			return true;
		}
		return false;
	}
}
