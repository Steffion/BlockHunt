package nl.Steffion.BlockHunt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import nl.Steffion.BlockHunt.commands.CommandHandler;
import nl.Steffion.BlockHunt.data.Arena;
import nl.Steffion.BlockHunt.data.Config;
import nl.Steffion.BlockHunt.data.PlayerData;
import nl.Steffion.BlockHunt.events.AsyncPlayerChatEvent;
import nl.Steffion.BlockHunt.events.BlockPlaceEvent;
import nl.Steffion.BlockHunt.events.EntityDamageEvent;
import nl.Steffion.BlockHunt.events.FoodLevelChangeEvent;
import nl.Steffion.BlockHunt.events.InventoryClickEvent;
import nl.Steffion.BlockHunt.events.PlayerDropItemEvent;
import nl.Steffion.BlockHunt.events.PlayerInteractEvent;
import nl.Steffion.BlockHunt.events.PlayerMoveEvent;
import nl.Steffion.BlockHunt.events.PlayerPickupItemEvent;

public class BlockHunt extends JavaPlugin {
	public static boolean		DEBUG_MODE	= true;

	private static BlockHunt	plugin;

	public static BlockHunt getPlugin() {
		return BlockHunt.plugin;
	}

	private ArenaHandler				arenaHandler;

	private Config						arenas;
	private CommandHandler				commandHandler;
	private HashMap<UUID, Arena>		editorsRenamingArena;
	private HashMap<UUID, PlayerData>	playerData;

	public ArenaHandler getArenaHandler() {
		return arenaHandler;
	}

	public Config getArenas() {
		return arenas;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	public HashMap<UUID, Arena> getEditorsRenamingArena() {
		return editorsRenamingArena;
	}
	
	public HashMap<UUID, PlayerData> getPlayerData() {
		return playerData;
	}
	
	public PlayerData getPlayerData(Player player) {
		return playerData.get(player.getUniqueId());
	}
	
	public void handleExeption(Exception e) {
		LocalDateTime time = LocalDateTime.now();

		getLogger().log(Level.SEVERE, "~~ ERROR ~~");
		getLogger().log(Level.SEVERE, e.getClass().getName() + ": " + e.getLocalizedMessage() + "!");
		getLogger().log(Level.SEVERE, "Contact the developer for asstiance!");
		getLogger().log(Level.SEVERE, "Include the file at: plugins/BlockHunt/errors/" + LocalDateTime.now() + ".txt");
		getLogger().log(Level.SEVERE, "~~ ERROR ~~");

		try {
			File file = new File("plugins/BlockHunt/errors/" + time + ".txt");
			file.getParentFile().mkdirs();
			file.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(new File("plugins/BlockHunt/errors/" + time + ".txt"), true);
			PrintStream ps = new PrintStream(fos);
			e.printStackTrace(ps);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return commandHandler.handleCommand(sender, cmd, label, args);
	}

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (arenaHandler.getAllEditors().contains(player)) {
				Arena arena = arenaHandler.getArena(player);
				arena.save();

				BlockHunt.plugin.getPlayerData(player).restore();
				
				getLogger().log(Level.WARNING, "Player " + player.getName()
						+ " was still editing an arena. Let them leave first before you close the server/reload, this could corrupt player files or the arenas.");
			}

			if (arenaHandler.getAllPlayers().contains(player)) {
				BlockHunt.plugin.getPlayerData(player).restore();
			}
		}

		getLogger().log(Level.INFO, "BlockHunt has succesfully been disabled.");
	}

	@Override
	public void onEnable() {
		if (BlockHunt.DEBUG_MODE) {
			for (World world : Bukkit.getWorlds()) {
				world.setTime(200);
				world.setStorm(false);
			}
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.setHealth(player.getMaxHealth());
				player.setFoodLevel(20);
				// player.setLevel(63);
				// player.setExp((float) 0.64);
				// player.getInventory().clear();
				// player.getInventory().addItem(new
				// ItemStack(Material.STONE_SLAB2, 45));
				// player.getInventory().setChestplate(new
				// ItemStack(Material.DIAMOND_CHESTPLATE));
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
		
		/*
		 * Handlers
		 */
		arenaHandler = new ArenaHandler();
		commandHandler = new CommandHandler();

		/*
		 * Storage variables
		 */
		playerData = new HashMap<UUID, PlayerData>();
		editorsRenamingArena = new HashMap<UUID, Arena>();
		
		/*
		 * Registering listeners
		 */
		getServer().getPluginManager().registerEvents(new AsyncPlayerChatEvent(), this);
		getServer().getPluginManager().registerEvents(new BlockPlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageEvent(), this);
		getServer().getPluginManager().registerEvents(new FoodLevelChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerDropItemEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerPickupItemEvent(), this);
		
		getLogger().log(Level.INFO, "BlockHunt has succesfully been loaded!");
	}
	
	public void removePlayerData(Player player) {
		playerData.remove(player.getUniqueId());
	}
	
	public void storePlayerData(Player player) {
		PlayerData backupPlayer = new PlayerData();
		backupPlayer.store(player);
		playerData.put(player.getUniqueId(), backupPlayer);
	}
	
}
