package nl.Steffion.BlockHunt;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import nl.Steffion.BlockHunt.configdefaults.SettingsDefaults;
import nl.Steffion.BlockHunt.configs.Config;
import nl.Steffion.BlockHunt.events.AsyncPlayerChatEvent;
import nl.Steffion.BlockHunt.events.BlockBreakEvent;
import nl.Steffion.BlockHunt.events.BlockPlaceEvent;
import nl.Steffion.BlockHunt.events.EntityDamageByEntityEvent;
import nl.Steffion.BlockHunt.events.EntityDamageEvent;
import nl.Steffion.BlockHunt.events.FoodLevelChangeEvent;
import nl.Steffion.BlockHunt.events.InventoryClickEvent;
import nl.Steffion.BlockHunt.events.PlayerDropItemEvent;
import nl.Steffion.BlockHunt.events.PlayerInteractEvent;
import nl.Steffion.BlockHunt.events.PlayerItemDamageEvent;
import nl.Steffion.BlockHunt.events.PlayerMoveEvent;
import nl.Steffion.BlockHunt.events.PlayerPickupItemEvent;
import nl.Steffion.BlockHunt.events.PlayerQuitEvent;

/**
 * The main class of BlockHunt.
 *
 * @author Steffion (Stef de Goey) 2016
 *
 */
public class BlockHunt extends JavaPlugin {
	public static boolean		DEBUG_MODE	= true;
	public static boolean		ENABLED		= true;

	private static BlockHunt	plugin;

	/**
	 * Return the JavaPlugin instance, useful for getting the server etc.
	 *
	 * @return JavaPlugin instance
	 */
	public static BlockHunt getPlugin() {
		return BlockHunt.plugin;
	}

	private ArenaHandler	arenaHandler;
	private Config			arenas;
	private CommandHandler	commandHandler;
	private PlayerHandler	playerHandler;
	private Config			settings;

	/**
	 * Returns the ArenaHandler, useful for anything related to arenas.
	 *
	 * @return The ArenaHandler
	 */
	public ArenaHandler getArenaHandler() {
		return arenaHandler;
	}

	/**
	 * Returns the arena config.
	 *
	 * @return arenas.yml config
	 */
	public Config getArenas() {
		return arenas;
	}

	/**
	 * Returns the ArenaHandler, useful for anything related to commands.
	 *
	 * @return The ArenaHandler
	 */
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	/**
	 * Returns the PlayerHandler, useful for anything related to players and
	 * their data.
	 *
	 * @return The PlayerHandler
	 */
	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	/**
	 * Returns the settings config.
	 *
	 * @return settings.yml config
	 */
	public Config getSettings() {
		return settings;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return commandHandler.handleCommand(sender, cmd, label, args);
	}

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (arenaHandler.getAllEditors().contains(player)) {
				arenaHandler.getArena(player).save();
				playerHandler.getPlayerData(player).restore();
				
				getLogger().log(Level.WARNING, "Player " + player.getName()
						+ " was still editing an arena. Let them leave first before you close the server/reload, this could corrupt player files or the arenas.");
			}

			if (arenaHandler.getAllPlayers().contains(player)) {
				playerHandler.getPlayerData(player).restore();
				player.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());
				
				getLogger().log(Level.WARNING, "Player " + player.getName()
						+ " was still in arena. Let them leave first before you close the server/reload, this could corrupt player files or the arenas.");
			}
		}

		getLogger().log(Level.INFO, "BlockHunt has successfully been disabled.");

		/*
		Prevents memeory leaks
		 */

		plugin = null;
	}

	@Override
	public void onEnable() {
		if (BlockHunt.DEBUG_MODE) {
			Bukkit.getScheduler().runTaskTimer(this, () -> {
                for (World world : Bukkit.getWorlds()) {
                    world.setTime(200);
                    world.setStorm(false);
                }
            }, 0, 20 * 60);

			for (Player player : Bukkit.getOnlinePlayers()) {
				player.setHealth(player.getMaxHealth());
				player.setFoodLevel(20);
			}
		}

		/*
		 * Plugin related
		 */
		BlockHunt.plugin = this;
		
		/*
		 * Config files
		 */
		arenas = new Config("arenas");
		settings = new Config("settings", SettingsDefaults.getValues());
		
		/*
		 * Handlers
		 */
		arenaHandler = new ArenaHandler();
		commandHandler = new CommandHandler();
		playerHandler = new PlayerHandler();

		/*
		Registering events here

		 */

		getServer().getPluginManager().registerEvents(new AsyncPlayerChatEvent(), this);
		getServer().getPluginManager().registerEvents(new BlockPlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new BlockBreakEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
		getServer().getPluginManager().registerEvents(new FoodLevelChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerDropItemEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerItemDamageEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerPickupItemEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);
		
		/*
		 * Check dependencies
		 */
		if (!new File("plugins/LibsDisguises.jar").exists()) {
			getLogger().log(Level.SEVERE, "Required dependency is not installed! (LibsDisguises)");

			BlockHunt.ENABLED = false;
		}
		
		if (!new File("plugins/ProtocolLib.jar").exists()) {
			getLogger().log(Level.SEVERE, "Required dependency is not installed! (ProtocolLib)");

			BlockHunt.ENABLED = false;
		}
		
		/*
		 * Plugin successful enabled?
		 */
		if (BlockHunt.ENABLED) {
			getLogger().log(Level.INFO, "BlockHunt has successfully been loaded!");
		} else {
			getLogger().log(Level.WARNING, "BlockHunt has NOT successfully been loaded!");
		}
	}

}
