package nl.Steffion.BlockHunt.Managers;

import nl.Steffion.BlockHunt.W;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageM {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	/**
	 * Send a message to a player.
	 * 
	 * @param player
	 * @param message
	 * @param tag
	 * @param vars
	 */
	public static void sendMessage(Player player, String message, Boolean tag,
			String... vars) {
		if (player == null) {
			Bukkit.getConsoleSender().sendMessage(
					MessageM.replaceAll(CType.TAG(tag) + message, vars));
		} else {
			player.sendMessage(MessageM.replaceAll(CType.TAG(tag) + message,
					vars));
		}
	}

	/**
	 * Send a message to a player from a Config.
	 * 
	 * @param player
	 * @param location
	 * @param tag
	 * @param vars
	 */
	public static void sendFMessage(Player player, ConfigC location,
			Boolean tag, String... vars) {
		if (player == null) {
			Bukkit.getConsoleSender().sendMessage(
					MessageM.replaceAll(
							CType.TAG(tag)
									+ location.config.getFile().get(
											location.getLocation()), vars));
		} else {
			player.sendMessage(MessageM.replaceAll(CType.TAG(tag)
					+ location.config.getFile().get(location.getLocation()),
					vars));
		}
	}

	/**
	 * Sends a message to all online players. Replaces %player% tag to the
	 * Player's name or "Console".
	 * 
	 * @param message
	 * @param tag
	 * @param vars
	 */
	public static void broadcastMessage(String message, Boolean tag,
			String... vars) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			String pMessage = message.replaceAll("%player%", player.getName());
			player.sendMessage(MessageM.replaceAll(CType.TAG(tag) + pMessage,
					vars));
		}
		message = message.replaceAll("%player%", "Console");
		Bukkit.getConsoleSender().sendMessage(
				MessageM.replaceAll(CType.TAG(tag) + message, vars));
	}

	/**
	 * Sends a message to all online players from a Config. Replaces %player%
	 * tag to the Player's name or "Console".
	 * 
	 * @param location
	 * @param tag
	 * @param vars
	 */
	public static void broadcastFMessage(ConfigC location, Boolean tag,
			String... vars) {
		for (Player player : Bukkit.getOnlinePlayers()) {
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

	/**
	 * Replace all variable codes.
	 * 
	 * @param message
	 * @param vars
	 * @return
	 */
	public static String replaceAll(String message, String... vars) {
		return MessageM.replaceColours(MessageM.replaceColourVars(MessageM
				.replaceVars(message, vars)));
	}

	/**
	 * Replace all colour codes.
	 * 
	 * @param message
	 * @return
	 */
	public static String replaceColours(String message) {
		return message.replaceAll("(&([a-fk-or0-9]))", "\u00A7$2");
	}

	/**
	 * Replace Colour codes from config.
	 * 
	 * @param message
	 * @return
	 */
	public static String replaceColourVars(String message) {
		message = message.replaceAll("%N", CType.NORMAL());
		message = message.replaceAll("%W", CType.WARNING());
		message = message.replaceAll("%E", CType.ERROR());
		message = message.replaceAll("%A", CType.ARG());
		message = message.replaceAll("%H", CType.HEADER());
		return message;
	}

	/**
	 * Replace all variables.
	 * 
	 * @param message
	 * @param vars
	 * @return Replaced String.
	 */
	public static String replaceVars(String message, String... vars) {
		for (String var : vars) {
			String[] split = var.split("-");
			message = message.replaceAll("%" + split[0] + "%", split[1]);
		}
		return message;
	}

	public static class CType {

		public static String NORMAL() {
			return (String) W.config.get(ConfigC.chat_normal);
		}

		public static String WARNING() {
			return (String) W.config.get(ConfigC.chat_warning);
		}

		public static String ERROR() {
			return (String) W.config.get(ConfigC.chat_error);
		}

		public static String ARG() {
			return (String) W.config.get(ConfigC.chat_arg);
		}

		public static String HEADER() {
			return (String) W.config.get(ConfigC.chat_header);
		}

		public static String TAG() {
			return (String) W.config.get(ConfigC.chat_header)
					+ (String) W.config.get(ConfigC.chat_tag);
		}

		public static String TAG(Boolean enabled) {
			if (enabled) {
				return (String) W.config.get(ConfigC.chat_header)
						+ (String) W.config.get(ConfigC.chat_tag);
			} else {
				return "";
			}
		}
	}
}
