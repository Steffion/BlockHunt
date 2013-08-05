package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.ConfigC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeathEvent implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();

		for (Arena arena : W.arenaList) {
			if (arena.playersInArena.contains(player)) {
				event.setDeathMessage(null);
				event.getDrops().clear();
				W.dcAPI.undisguisePlayer(player);

				if (!arena.seekers.contains(player)) {
					arena.seekers.add(player);
					ArenaHandler
							.sendFMessage(
									arena,
									ConfigC.normal_HiderDied,
									true,
									"playername-" + player.getName(),
									"left-"
											+ (arena.playersInArena.size() - arena.seekers
													.size()));
				} else {
					ArenaHandler.sendFMessage(arena, ConfigC.normal_SeekerDied,
							true, "playername-" + player.getName(), "secs-"
									+ arena.waitingTimeSeeker);
				}
			}
		}
	}
}
