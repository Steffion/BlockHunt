package nl.Steffion.BlockHunt.Listeners;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.W;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnEntityDamageByEntityEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		Entity ent = event.getEntity();
		Player player = null;
		if (ent instanceof Player) {
			player = (Player) ent;
		}

		if (player != null) {
			for (Arena arena : W.arenaList) {
				if (arena.playersInArena.contains(player)) {
					if (arena.gameState == ArenaState.WAITING
							|| arena.gameState == ArenaState.STARTING) {
						event.setCancelled(true);
					} else {
						if (arena.seekers.contains(player)
								&& arena.seekers.contains(event.getDamager())) {
							event.setCancelled(true);
						} else if (arena.playersInArena.contains(player)
								&& arena.playersInArena.contains(event
										.getDamager())
								&& !arena.seekers.contains(event.getDamager())
								&& !arena.seekers.contains(player)) {
							event.setCancelled(true);
						} else {
							player.getWorld().playEffect(player.getLocation(),
									Effect.BOW_FIRE, 0);

							if (event.getDamage() >= player.getHealth()) {
								player.setHealth(20);
								event.setCancelled(true);

								W.dcAPI.undisguisePlayer(player);
								W.pBlock.remove(player);

								if (!arena.seekers.contains(player)) {
									arena.seekers.add(player);
									ArenaHandler
											.sendFMessage(
													arena,
													ConfigC.normal_HiderDied,
													true,
													"playername-"
															+ player.getName(),
													"left-"
															+ (arena.playersInArena
																	.size() - arena.seekers
																	.size()));
								} else {
									ArenaHandler.sendFMessage(arena,
											ConfigC.normal_SeekerDied, true,
											"playername-" + player.getName(),
											"secs-" + arena.waitingTimeSeeker);
								}

								player.getInventory().clear();
								player.updateInventory();

								if (arena.seekers.size() >= arena.playersInArena
										.size()) {
									ArenaHandler.seekersWin(arena);
								} else {
									W.dcAPI.undisguisePlayer(player);
									W.seekertime.put(player,
											arena.waitingTimeSeeker);
									player.teleport(arena.seekersWarp);
									player.setGameMode(GameMode.SURVIVAL);
								}
							}
						}
					}
				}
			}
		}
	}
}
