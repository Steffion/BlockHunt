package nl.Steffion.BlockHunt;

import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Managers.MessageM.CType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArenaHandler {
	public static void loadArenas() {
		W.arenaList.clear();
		for (String arenaName : W.arenas.getFile().getKeys(false)) {
			W.arenaList.add((Arena) W.arenas.getFile().get(arenaName));
		}
	}

	public static void sendMessage(Arena arena, String message, Boolean tag,
			String... vars) {
		for (Player player : arena.playersInArena) {
			String pMessage = message.replaceAll("%player%", player.getName());
			player.sendMessage(MessageM.replaceAll(CType.TAG(tag) + pMessage,
					vars));
		}
		message = message.replaceAll("%player%", "Console");
		Bukkit.getConsoleSender().sendMessage(
				MessageM.replaceAll(CType.TAG(tag) + message, vars));
	}

	public static void sendFMessage(Arena arena, ConfigC location, Boolean tag,
			String... vars) {
		for (Player player : arena.playersInArena) {
			String pMessage = location.config.getFile()
					.get(location.getLocation()).toString()
					.replaceAll("%player%", player.getName());
			player.sendMessage(MessageM.replaceAll(CType.TAG(tag) + pMessage,
					vars));
		}
		String message = location.config.getFile().get(location.getLocation())
				.toString().replaceAll("%player%", "Console");
		Bukkit.getConsoleSender().sendMessage(
				MessageM.replaceAll(CType.TAG(tag) + message, vars));
	}
}
