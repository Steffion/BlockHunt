package nl.Steffion.BlockHunt.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import nl.Steffion.BlockHunt.BlockHunt;

public class Arena {
	public enum ArenaState {
		INGAME, PREGAME, STARTING, WAITING;
	}

	private UUID		editor;
	private boolean		editorIsRenamingArena;
	private Location	hidersSpawn;
	private Location	lobbyLocation;
	private String		name;
	private List<UUID>	players;
	private BlockHunt	plugin;
	private Objective	scoreboard;
	private Location	seekersSpawn;
	private ArenaState	state;
	private List<Hider>	teamHiders;
	private List<UUID>	teamSeekers;
	private BukkitTask	thread;
	private int			timer;

	public Arena() {
		this(null);
		
		int arenaNumber = 1;
		while (true) {
			if (plugin.getArenas().getConfig().contains("Arena_" + arenaNumber)) {
				arenaNumber++;
				continue;
			}

			name = "Arena_" + arenaNumber;
			break;
		}
	}

	public Arena(String name) {
		plugin = BlockHunt.getPlugin();
		this.name = name;
		players = new ArrayList<>();
		teamHiders = new ArrayList<>();
		teamSeekers = new ArrayList<>();
		scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard().registerNewObjective("BlockHunt",
				"dummy");
		state = ArenaState.WAITING;
	}

	/**
	 * Adds a player to the arena.
	 *
	 * @param player
	 *            - player who needs to be added
	 */
	public void addPlayer(Player player) {
		players.add(player.getUniqueId());
		
		plugin.getPlayerHandler().storePlayerData(player);
		plugin.getPlayerHandler().getPlayerData(player).clear();

		player.teleport(lobbyLocation);
		
		startThread();
	}

	/**
	 * Add player to the seeker list.
	 *
	 * @param player
	 *            - player to be added to the seeker list.
	 */
	public void addSeeker(Player player) {
		player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
		player.getInventory().setItem(1, new ItemStack(Material.BOW));
		player.getInventory().setItem(7, new ItemStack(Material.ARROW, 64));
		
		if (state == ArenaState.PREGAME) {
			player.teleport(seekersSpawn);
		} else {
			player.teleport(hidersSpawn);
		}

		teamSeekers.add(player.getUniqueId());
	}

	/**
	 *
	 * @return The current editor of this arena, null if none.
	 */
	public Player getEditor() {
		return plugin.getServer().getPlayer(editor);
	}
	
	/**
	 *
	 * @param player
	 *            - get the {@link Hider} class of the player.
	 * @return The {@link Hider} class of the player, if not a hider returns
	 *         null.
	 */
	public Hider getHider(Player player) {
		for (Hider hider : teamHiders) {
			if (hider.getPlayer().getUniqueId() == player.getUniqueId()) return hider;
		}

		return null;
	}

	/**
	 *
	 * @return A list of {@link Hider}s.
	 */
	public List<Hider> getHiders() {
		return teamHiders;
	}
	
	/**
	 *
	 * @return The {@link Hider}'s spawn.
	 */
	public Location getHidersSpawn() {
		return hidersSpawn;
	}

	/**
	 *
	 * @return The lobby location.
	 */
	public Location getLobbyLocation() {
		return lobbyLocation;
	}
	
	/**
	 *
	 * @return The name of the arena.
	 */
	public String getName() {
		return name;
	}
	
	public List<Player> getPlayers() {
		return this.players.stream().map(uuid -> plugin.getServer().getPlayer(uuid)).collect(Collectors.toList());
	}
	
	/**
	 * <i>Different than getHiders(); because seekers don't have a separate
	 * class.</i>
	 *
	 * @return A list of {@link Player}'s who are seekers.
	 */
	public List<Player> getSeekers() {
		return teamSeekers.stream().map(uuid -> plugin.getServer().getPlayer(uuid)).collect(Collectors.toList());
	}
	
	/**
	 *
	 * @return The seeker's spawn.
	 */
	public Location getSeekersSpawn() {
		return seekersSpawn;
	}
	
	/**
	 *
	 * @return State of the arena.
	 */
	public ArenaState getState() {
		return state;
	}
	
	/**
	 *
	 * @return Boolean if the arena has started.
	 */
	public boolean hasStarted() {
		return (state == ArenaState.WAITING) || (state == ArenaState.STARTING);
	}
	
	/**
	 *
	 * @return Boolean if the editor is currently renaming the arena.
	 */
	public boolean isEditorRenamingArena() {
		return editorIsRenamingArena;
	}

	/**
	 *
	 * @return Boolean if the arena has all the required settings.
	 */
	public boolean isSetup() {
		return !((hidersSpawn == null) || (lobbyLocation == null) || (seekersSpawn == null));
	}
	
	/**
	 * Load the arena from the config file.
	 */
	public void load() {
		plugin.getArenas().load();
		ConfigurationSection arenas = plugin.getArenas().getConfig();

		if (arenas.getString(name + ".hidersSpawn.world") != null) {
			hidersSpawn = new Location(Bukkit.getWorld(arenas.getString(name + ".hidersSpawn.world")),
					arenas.getDouble(name + ".hidersSpawn.x"), arenas.getDouble(name + ".hidersSpawn.y"),
					arenas.getDouble(name + ".hidersSpawn.z"));
		}
		
		if (arenas.getString(name + ".lobbyLocation.world") != null) {
			lobbyLocation = new Location(Bukkit.getWorld(arenas.getString(name + ".lobbyLocation.world")),
					arenas.getDouble(name + ".lobbyLocation.x"), arenas.getDouble(name + ".lobbyLocation.y"),
					arenas.getDouble(name + ".lobbyLocation.z"));
		}
		
		if (arenas.getString(name + ".seekersSpawn.world") != null) {
			seekersSpawn = new Location(Bukkit.getWorld(arenas.getString(name + ".seekersSpawn.world")),
					arenas.getDouble(name + ".seekersSpawn.x"), arenas.getDouble(name + ".seekersSpawn.y"),
					arenas.getDouble(name + ".seekersSpawn.z"));
		}
	}
	
	/**
	 * Remove the editor. Used when the editor leaves the editing mode.
	 */
	public void removeEditor() {
		editor = null;
		editorIsRenamingArena = false;
	}

	/**
	 * Remove a player from the hider team.
	 *
	 * @param player
	 *            - player to be removed.
	 */
	public void removeHider(Player player) {
		for (int i = 0; i < teamHiders.size(); i++) {
			Hider hider = teamHiders.get(i);
			
			if (hider.getPlayer().getUniqueId() == player.getUniqueId()) {
				teamHiders.remove(i);
				break;
			}
		}
		
		DisguiseAPI.undisguiseToAll(player);
	}
	
	/**
	 * Remove a player from the arena.
	 *
	 * @param player
	 *            - Player to remove.
	 */
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
		removeHider(player);
		teamSeekers.remove(player.getUniqueId());
		
		plugin.getPlayerHandler().getPlayerData(player).restore();
		player.setScoreboard(plugin.getServer().getScoreboardManager().getMainScoreboard());
	}
	
	/**
	 * Reset some arena variables. Used after a game finished.
	 */
	protected void resetArena() {
		state = ArenaState.WAITING;
		teamHiders = new ArrayList<>();
		teamSeekers = new ArrayList<>();
	}
	
	/**
	 * Save the arena to the file.
	 */
	public void save() {
		plugin.getArenas().getConfig().set(name, "");

		if (hidersSpawn != null) {
			plugin.getArenas().getConfig().set(name + ".hidersSpawn.world", hidersSpawn.getWorld().getName());
			plugin.getArenas().getConfig().set(name + ".hidersSpawn.x", hidersSpawn.getBlockX());
			plugin.getArenas().getConfig().set(name + ".hidersSpawn.y", hidersSpawn.getBlockY());
			plugin.getArenas().getConfig().set(name + ".hidersSpawn.z", hidersSpawn.getBlockZ());
		}
		
		if (lobbyLocation != null) {
			plugin.getArenas().getConfig().set(name + ".lobbyLocation.world", lobbyLocation.getWorld().getName());
			plugin.getArenas().getConfig().set(name + ".lobbyLocation.x", lobbyLocation.getBlockX());
			plugin.getArenas().getConfig().set(name + ".lobbyLocation.y", lobbyLocation.getBlockY());
			plugin.getArenas().getConfig().set(name + ".lobbyLocation.z", lobbyLocation.getBlockZ());
		}
		
		if (seekersSpawn != null) {
			plugin.getArenas().getConfig().set(name + ".seekersSpawn.world", seekersSpawn.getWorld().getName());
			plugin.getArenas().getConfig().set(name + ".seekersSpawn.x", seekersSpawn.getBlockX());
			plugin.getArenas().getConfig().set(name + ".seekersSpawn.y", seekersSpawn.getBlockY());
			plugin.getArenas().getConfig().set(name + ".seekersSpawn.z", seekersSpawn.getBlockZ());
		}
		
		plugin.getArenas().save();
	}
	
	/**
	 * Set the editor of the arena.
	 *
	 * @param editor
	 */
	public void setEditor(Player editor) {
		this.editor = editor.getUniqueId();
	}
	
	/**
	 * Use this if the editor wants to rename the arena.
	 *
	 * @param editorRenamingArena
	 */
	public void setEditorRenamingArena(boolean editorRenamingArena) {
		editorIsRenamingArena = editorRenamingArena;
	}

	/**
	 * Set the hider spawn of the arena.
	 *
	 * @param hidersSpawn
	 */
	public void setHidersSpawn(Location hidersSpawn) {
		this.hidersSpawn = hidersSpawn;
	}

	/**
	 * Set the lobby location of the arena.
	 *
	 * @param lobbyLocation
	 */
	public void setLobbyLocation(Location lobbyLocation) {
		this.lobbyLocation = lobbyLocation;
	}
	
	/**
	 * Set the name of the arena.
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Set the seekers spawn of the arena.
	 *
	 * @param seekersSpawn
	 */
	public void setSeekersSpawn(Location seekersSpawn) {
		this.seekersSpawn = seekersSpawn;
	}
	
	public void startThread() {
		if (thread != null) return;
		thread = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (players.isEmpty()) {
                resetArena();
                stopThread();
            }

            if (state == ArenaState.WAITING) {
                if (players.size() >= ((int) plugin.getSettings().get("MINPLAYERS"))) {
                    state = ArenaState.STARTING;
                    timer = (int) plugin.getSettings().get("LOBBYTIME");
                }
            }

            if (state == ArenaState.STARTING) {
                if (players.size() < ((int) plugin.getSettings().get("MINPLAYERS"))) {
                    for (Player player : getPlayers()) {
                        player.setExp(0);
                    }

                    state = ArenaState.WAITING;
                    return;
                }

                timer--;

                switch (timer) {
                    case 10:
                        for (Player player : getPlayers()) {
                            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(0, Tone.G));
                        }
                        break;
                    case 5:
                        for (Player player : getPlayers()) {
                            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(0, Tone.B));
                        }
                        break;
                    case 4:
                        for (Player player : getPlayers()) {
                            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(0, Tone.B));
                        }
                        break;
                    case 3:
                        for (Player player : getPlayers()) {
                            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(1, Tone.C));
                        }
                        break;
                    case 2:
                        for (Player player : getPlayers()) {
                            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(1, Tone.C));
                        }
                        break;
                    case 1:
                        for (Player player : getPlayers()) {
                            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(1, Tone.D));
                        }
                        break;
                    case 0:
                        for (Player player : getPlayers()) {
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        }

                        state = ArenaState.PREGAME;
                        timer = (int) plugin.getSettings().get("SEEKERSWAITTIME");

                        int seekerAmount = (int) (Math.round(
                                players.size() * (double) plugin.getSettings().get("PRECENTAGE_SEEKERS")) + 1);

                        for (int i = 0; i < seekerAmount; i++) {
                            Random random = new Random();

                            UUID randomSeeker = players.get(random.nextInt(players.size()));

                            if (teamSeekers.contains(randomSeeker)) {
                                i--;
                                continue;
                            }

                            addSeeker(plugin.getServer().getPlayer(randomSeeker));
                        }

                        String seekers = "The seekers have been choosen: ";
                        for (Player seeker : getSeekers()) {
                            seekers += seeker.getName() + ", ";
                        }

                        seekers = seekers.substring(0, seekers.length() - 2);

                        for (Player player : getPlayers()) {
                            player.sendMessage(seekers);

                            if (!getSeekers().contains(player)) {
                                teamHiders.add(new Hider(player));
                            }
                        }

                        for (Hider hider : getHiders()) {
                            Material randomBlock = null;

                            while (true) {
                                @SuppressWarnings("unchecked")
                                ArrayList<Material> allowedBlocks = (ArrayList<Material>) plugin.getSettings()
                                        .get("ALLOWED_BLOCKS");
                                Random random = new Random();
                                String randomBlockName = allowedBlocks.get(random.nextInt(allowedBlocks.size()))
                                        .name();

                                try {
                                    randomBlock = Material.valueOf(randomBlockName);
                                    break;
                                } catch (IllegalArgumentException e) {
                                    plugin.getLogger().log(Level.WARNING,
                                            "There is no material called '" + randomBlockName
                                                    + "'! Please edit your ALLOWED_BLOCKS in the config.yml.");
                                }
                            }

                            DisguiseAPI.disguiseToAll(hider.getPlayer(),
                                    new MiscDisguise(DisguiseType.FALLING_BLOCK, randomBlock.getId(), 0));
                            hider.getPlayer().getInventory().setItem(7, new ItemStack(randomBlock.getId()));
                            hider.setBlock(randomBlock);
                            hider.getPlayer().teleport(hidersSpawn);
                        }

                        for (Player seeker : getSeekers()) {
                            seeker.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
                            seeker.teleport(seekersSpawn);
                        }

                        break;
                    default:
                        break;
                }

                for (Player player : getPlayers()) {
                    player.setExp((float) timer / ((int) plugin.getSettings().get("LOBBYTIME")));
                }
            }

            if (state == ArenaState.PREGAME) {
                timer--;

                if (timer == 0) {
                    state = ArenaState.INGAME;
                    timer = (int) plugin.getSettings().get("GAMETIME");

                    for (Player seeker : getSeekers()) {
                        seeker.teleport(hidersSpawn);
                    }

                    hidersSpawn.getWorld().strikeLightningEffect(hidersSpawn);
                }

                for (Player player : getPlayers()) {
                    player.setExp((float) timer / ((int) plugin.getSettings().get("SEEKERSWAITTIME")));
                }
            }

            if (state == ArenaState.INGAME) {
                timer--;

                if ((timer == 0) || teamHiders.isEmpty()) {
                    resetArena();

                    for (Player player : getPlayers()) {
                        DisguiseAPI.undisguiseToAll(player);
                        player.getInventory().setItem(0, null);
                        player.getInventory().setItem(1, null);
                        player.getInventory().setItem(7, null);
                        player.setExp(0);
                        player.setHealth(player.getMaxHealth());
                        player.teleport(lobbyLocation);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    }
                }

                for (Player player : getPlayers()) {
                    player.setExp((float) timer / ((int) plugin.getSettings().get("GAMETIME")));
                }
            }

            if ((state == ArenaState.PREGAME) || (state == ArenaState.INGAME)) {
                for (Hider hider : getHiders()) {
                    int hiderTimer = hider.getSolidBlockTimer();

                    hider.setSolidBlockTimer(hiderTimer + 1);

                    if (hiderTimer == 3) {
                        if (hider.getPlayer().getLocation().getBlock().getType() != Material.AIR) {
                            hider.getPlayer().sendMessage("You can't become a block here!");
                            hider.setSolidBlockTimer(0);
                        } else {
                            hider.setHideLocation(hider.getPlayer().getLocation());
                            hider.getPlayer().playSound(hider.getPlayer().getLocation(),
                                    Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0);

                            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                                if (onlinePlayer.equals(hider.getPlayer())) {
                                    continue;
                                }

                                onlinePlayer.sendBlockChange(hider.getHideLocation(), hider.getBlock(), (byte) 0);
                                onlinePlayer.hidePlayer(hider.getPlayer());
                            }
                        }
                    } else if (hiderTimer > 3) {
                        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                            if (onlinePlayer.equals(hider.getPlayer())) {
                                continue;
                            }

                            onlinePlayer.sendBlockChange(hider.getHideLocation(), hider.getBlock(), (byte) 0);
                            onlinePlayer.hidePlayer(hider.getPlayer());
                        }
                    }

                    hider.getPlayer().setExp(((float) (hiderTimer > 3 ? 3 : hiderTimer) / 3));
                }
            }

            updateScoreboard();
        }, 0, 20);
	}

	public void stopThread() {
		plugin.getServer().getScheduler().cancelTask(thread.getTaskId());
		thread = null;
	}

	/**
	 * Update scoreboard for all players in the arena.
	 */
	protected void updateScoreboard() {
		scoreboard.setDisplaySlot(DisplaySlot.SIDEBAR);
		scoreboard.setDisplayName("§9§lBlockHunt");

		for (String entry : scoreboard.getScoreboard().getEntries()) {
			scoreboard.getScoreboard().resetScores(entry);
		}

		List<String> scoreboardEntries = new ArrayList<>();

		scoreboardEntries.add("§lArena: §6" + name);
		scoreboardEntries.add("§lPlayers: §6" + players.size() + "/" + plugin.getSettings().get("MAXPLAYERS"));
		
		if (state == ArenaState.WAITING) {
			scoreboardEntries.add("§lMinimum required players: §6" + plugin.getSettings().get("MINPLAYERS"));
			scoreboardEntries.add("§6§lWaiting for players...");
		}

		if (state == ArenaState.STARTING) {
			String startingIn = "§6§lStarting in: §f§l" + timer + " §6seconds";
			if (timer == 1) {
				startingIn = startingIn.substring(0, startingIn.length() - 1);
			}

			scoreboardEntries.add(startingIn);
		}

		if ((state == ArenaState.PREGAME) || (state == ArenaState.INGAME)) {
			scoreboardEntries.add("§c§lHiders: §6" + teamHiders.size());
			scoreboardEntries.add("§b§lSeekers: §6" + teamSeekers.size());
		}

		if (state == ArenaState.PREGAME) {
			scoreboardEntries.add("§6§lSeekers will be free in:");
			
			String hidersFree = "§l" + timer + " §6seconds";
			if (timer == 1) {
				hidersFree = hidersFree.substring(0, hidersFree.length() - 1);
			}

			scoreboardEntries.add(hidersFree);
		}
		
		if (state == ArenaState.INGAME) {
			String timeLeft = "§6§lTime left: §f§l" + timer + " §6seconds";
			if (timer == 1) {
				timeLeft = timeLeft.substring(0, timeLeft.length() - 1);
			}

			scoreboardEntries.add(timeLeft);
		}
		
		for (int i = 0; i < scoreboardEntries.size(); i++) {
			if (scoreboardEntries.get(i).length() > 32) {
				scoreboardEntries.set(i, scoreboardEntries.get(i).substring(0, 32));
			}
			
			Score scoreboardEntry = scoreboard.getScore(scoreboardEntries.get(i));
			scoreboardEntry.setScore(scoreboardEntries.size() - i - 1);
		}
		
		for (Player player : getPlayers()) {
			player.setScoreboard(scoreboard.getScoreboard());
		}
	}
}