package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.SignsHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class OnSignChangeEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onSignChangeEvent(SignChangeEvent event) {
		Player player = event.getPlayer();
		String[] lines = event.getLines();
		if (lines[0] != null) {
			if (lines[0].equalsIgnoreCase("[" + W.pluginName + "]")) {
				if (PlayerM.hasPerm(player, PermsC.signcreate, true)) {
					SignsHandler.createSign(lines, new LocationSerializable(
							event.getBlock().getLocation()));
				}
			}
		}
	}
}
