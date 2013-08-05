package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnPlayerCommandPreprocessEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocessEvent(
			PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();

		for (Arena arena : W.arenaList) {
			if (arena.playersInArena.contains(player)) {
				String m = event.getMessage();
				if (m.startsWith("/blockhunt") || m.startsWith("/bh")
						|| m.startsWith("/seekandfind")
						|| m.startsWith("/seekandfind") || m.startsWith("/saf")
						|| m.startsWith("/sf") || m.startsWith("/hideandseek")
						|| m.startsWith("/has") || m.startsWith("/hs")
						|| m.startsWith("/ban") || m.startsWith("/kick")
						|| m.startsWith("/tempban") || m.startsWith("/mute")
						|| m.startsWith("/reload")) {
					return;
				}
				MessageM.sendFMessage(player, ConfigC.warning_unableToCommand,
						true);
				event.setCancelled(true);
			}
		}
	}
}
