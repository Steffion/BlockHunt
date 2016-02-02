package nl.Steffion.BlockHunt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.data.Arena;

public class ArenaHandler {
	private List<Arena>	arenas	= new ArrayList<Arena>();
	private BlockHunt	plugin;
						
	public ArenaHandler() {
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
	
	public List<Player> getAllEditors() {
		List<Player> editors = new ArrayList<Player>();

		for (Arena arena : arenas) {
			if (arena.getEditor() != null) {
				editors.add(arena.getEditor());
			}
		}
		
		return editors;
	}

	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<Player>();

		for (Arena arena : arenas) {
			players.addAll(arena.getPlayers());
		}
		
		return players;
	}
	
	public Arena getArena(Player player) {
		for (Arena arena : arenas) {
			if ((arena.getEditor() != null) && arena.getEditor().equals(player)) {
				return arena;
			}

			if (arena.getPlayers().contains(player)) {
				return arena;
			}
		}

		return null;
	}
	
	public List<Arena> getArenas() {
		return arenas;
	}

	public void setEditor(Arena arena, Player player) {
		for (int i = 0; i < arenas.size(); i++) {
			if (arenas.get(i).equals(arena)) {
				arenas.get(i).setEditor(player);
			}
		}
	}

	public Arena getArena(String arenaName) {
		for (Arena arena : arenas) {
			if (arena.getName().equalsIgnoreCase(arenaName)) {
				return arena;
			}
		}
		
		return null;
	}

	public Arena createNewArena() {
		Arena newArena = new Arena();
		newArena.save();
		
		arenas.add(newArena);
		
		return newArena;
	}
	
}
