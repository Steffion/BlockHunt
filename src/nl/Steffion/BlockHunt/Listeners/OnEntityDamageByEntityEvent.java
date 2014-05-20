package nl.Steffion.BlockHunt.Listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.libraryaddict.disguise.DisguiseAPI;
import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.ArenaHandler;
import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnEntityDamageByEntityEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		Player player = null;
		if (event.getEntity() instanceof Player) {
			player = (Player) event.getEntity();
		}

		Player damager = null;
		String damagername = "unknown";
		if (event.getDamager() instanceof Player) {
			damager = (Player) event.getDamager();
			damagername = damager.getName();
		}

		if (player != null) {
			for (Arena arena : W.arenaList) {
				if (arena.playersInArena.contains(player)) {
					if (arena.gameState == ArenaState.WAITING || arena.gameState == ArenaState.STARTING) {
						event.setCancelled(true);
					} else {
						if (arena.seekers.contains(player) && arena.seekers.contains(event.getDamager())) {
							event.setCancelled(true);
						} else if (arena.playersInArena.contains(player) && arena.playersInArena.contains(event.getDamager())
								&& !arena.seekers.contains(event.getDamager()) && !arena.seekers.contains(player)) {
							event.setCancelled(true);
						} else {
							player.getWorld().playSound(player.getLocation(), Sound.HURT_FLESH, 1, 1);

							if (event.getDamage() >= player.getHealth()) {
								player.setHealth(20);
								event.setCancelled(true);

								DisguiseAPI.undisguiseToAll(player);
								W.pBlock.remove(player);

								if (!arena.seekers.contains(player)) {
									if (W.shop.getFile().get(damager.getName() + ".tokens") == null) {
										W.shop.getFile().set(damager.getName() + ".tokens", 0);
										W.shop.save();
									}
									int damagerTokens = W.shop.getFile().getInt(damager.getName() + ".tokens");
									W.shop.getFile().set(damager.getName() + ".tokens", damagerTokens + arena.killTokens);
									W.shop.save();

									MessageM.sendFMessage(damager, ConfigC.normal_addedToken, "amount-" + arena.killTokens);

									if (W.shop.getFile().get(player.getName() + ".tokens") == null) {
										W.shop.getFile().set(player.getName() + ".tokens", 0);
										W.shop.save();
									}
									int playerTokens = W.shop.getFile().getInt(player.getName() + ".tokens");
									float addingTokens = ((float) arena.hidersTokenWin - (((float) arena.timer / (float) arena.gameTime) * (float) arena.hidersTokenWin));
									W.shop.getFile().set(player.getName() + ".tokens", playerTokens + (int) addingTokens);
									W.shop.save();

									MessageM.sendFMessage(player, ConfigC.normal_addedToken, "amount-" + (int) addingTokens);

									arena.seekers.add(player);
									ArenaHandler.sendFMessage(arena, ConfigC.normal_ingameHiderDied, "playername-" + player.getName(), "killer-" + damagername);

									int hidercount = (arena.playersInArena.size() - arena.seekers.size());
									if ((hidercount <= 3) && (hidercount > 0)) {
										List<String> hiders = new ArrayList<String>();
										for (Player p : arena.playersInArena) {
											if (!arena.seekers.contains(p)) {
												hiders.add(p.getName());
											}
										}
										Collections.sort(hiders);

										ArenaHandler.sendFMessage(arena, ConfigC.normal_ingameHidersLeft, "left-" + StringUtils.join(hiders.toArray(), ", "));
									} else {
										ArenaHandler.sendFMessage(arena, ConfigC.normal_ingameHidersLeft, "left-" + hidercount);
									}
								}

								player.getInventory().clear();
								player.updateInventory();

								if (arena.seekers.size() >= arena.playersInArena.size()) {
									ArenaHandler.seekersWin(arena);
								} else {
									DisguiseAPI.undisguiseToAll(player);
									W.seekertime.put(player, arena.waitingTimeSeeker);
									player.teleport(arena.seekersWarp);
									player.setGameMode(GameMode.SURVIVAL);
									player.setWalkSpeed(0.25F);

									// Fix for client not showing players after
									// they join
									for (Player otherplayer : arena.playersInArena) {
										if (otherplayer.canSee(player))
											otherplayer.showPlayer(player); // Make
																			// new
																			// player
																			// visible
																			// to
																			// others
										if (player.canSee(otherplayer))
											player.showPlayer(otherplayer); // Make
																			// other
																			// players
																			// visible
																			// to
																			// new
																			// player
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
