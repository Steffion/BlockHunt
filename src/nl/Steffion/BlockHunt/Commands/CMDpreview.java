package nl.Steffion.BlockHunt.Commands;

import java.io.File;
import java.io.IOException;

import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.CommandC;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.FileM;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDpreview extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.preview, true)) {
			if (args.length == 1) {
				MessageM.sendFMessage(player, ConfigC.error_notEnoughArguments,
						true, "syntax-" + CommandC.PREVIEW.usage);
			} else {
				File arenaworldFolder = new File("plugins/" + W.pluginName
						+ "/defaultArenas/" + args[1]);
				if (!arenaworldFolder.exists()) {
					MessageM.sendFMessage(player, ConfigC.error_noArena, true,
							"name-" + args[1]);
					return true;
				}

				boolean notFound = true;
				int subID = 1;
				while (notFound) {
					File destFolder = new File("plugins/" + W.pluginName
							+ "/loadedArenas/" + args[1] + "_" + subID);
					if (!destFolder.exists()) {
						MessageM.sendFMessage(player,
								ConfigC.normal_previewWorld, true);
						try {
							FileM.copyFolder(arenaworldFolder, destFolder);
							W.previewWorlds.add(destFolder.getPath());
							MessageM.broadcastMessage(destFolder.getPath(),
									false);
						} catch (IOException e) {
							e.printStackTrace();
						}

						WorldCreator wc = new WorldCreator(destFolder.getPath());
						Bukkit.getServer().createWorld(wc);

						World world = Bukkit.getWorld(destFolder.getPath());
						player.teleport(world.getSpawnLocation());

						notFound = false;

						MessageM.sendFMessage(player,
								ConfigC.normal_previewWorldDone, true);
					} else {
						subID = subID + 1;
					}
				}
			}
		}
		return true;
	}
}
