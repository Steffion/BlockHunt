package nl.Steffion.BlockHunt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.data.Arena;

/**
 *
 * @author Steffion (Stef de Goey) 2016
 * 
 */
public class ArenaHandler {
	private List<Arena>	arenas	= new ArrayList<>();
	private BlockHunt	plugin;
	
	ArenaHandler() {
		plugin = BlockHunt.getPlugin();

		/*
		 * Load arenas
		 */
		for (String arenaName : plugin.getArenas().getConfig().getValues(false).keySet()) {
			Arena arena = new Arena(arenaName);
			arena.load();
			
			arenas.add(arena);
		}
	}
	
	/**
	 * Build the arena name. It loops through all the args and makes it one
	 * string.
	 *
	 * @param args
	 *            - The args to build.
	 * @param startIndex
	 *            - Index to start at.
	 * @return The arena name.
	 */
	public String buildName(String[] args, int startIndex) {
		String name = "";
		
		for (int i = startIndex; i < args.length; i++) {
			name = name + args[i] + " ";
		}

		return name.substring(0, name.length() - 1);
	}

	/**
	 * Generate a new arena with an unique name and save it directly.
	 *
	 * @return The arena newly created.
	 */
	public Arena createNewArena() {
		Arena newArena = new Arena();
		newArena.save();
		
		arenas.add(newArena);
		
		return newArena;
	}

	/**
	 * Delete an arena.
	 * 
	 * @param arenaName
	 *            - name of arena which needs to be deleted.
	 */
	public void deleteArena(String arenaName) {
		plugin.getArenas().getConfig().set(arenaName, null);
		plugin.getArenas().save();

		arenas.remove(getArena(arenaName));
	}
	
	/**
	 *
	 * @return A list of all editors in all arenas.
	 */
	public List<Player> getAllEditors() {
		return arenas.stream().filter(arena -> arena.getEditor()
				!= null).map(Arena::getEditor).collect(Collectors.toList());
	}
	
	/**
	 *
	 * @return A list of all players in all arenas.
	 */
	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<>();

		for (Arena arena : arenas) {
			players.addAll(arena.getPlayers());
		}
		
		return players;
	}

	/**
	 *
	 * @param player
	 *            - Player you want to know the arena of.
	 * @return The arena this player is currently playing in or editing. Returns
	 *         null if not in any arena.
	 */
	public Arena getArena(Player player) {
		for (Arena arena : arenas) {
			if ((arena.getEditor() != null) && arena.getEditor().equals(player)) return arena;
			if (arena.getPlayers().contains(player)) return arena;
		}

		return null;
	}

	/**
	 *
	 * @param arenaName
	 *            - Arena name you want to get.
	 * @return The arena which matches the name. Returns null if there is no
	 *         match.
	 */
	public Arena getArena(String arenaName) {
		for (Arena arena : arenas) {
			if (arena.getName().equalsIgnoreCase(arenaName)) return arena;
		}
		
		return null;
	}

	/**
	 *
	 * @return A list of all arenas.
	 */
	public List<Arena> getArenas() {
		return arenas;
	}

	/**
	 * Set an editor of an arena.
	 *
	 * @param arena
	 *            - Arena you want to set the editor of.
	 * @param player
	 *            - Player you want to make editor.
	 */
	public void setEditor(Arena arena, Player player) {
		for (int i = 0; i < arenas.size(); i++) {
			if (arenas.get(i).equals(arena)) {
				arenas.get(i).setEditor(player);
			}
		}
	}
	
}
