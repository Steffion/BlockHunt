package nl.Steffion.BlockHunt.Managers;

import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.W;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageM {
	/**
	 * Steffion's Engine - Made by Steffion.
	 * 
	 * You're allowed to use this engine for own usage, you're not allowed to
	 * republish the engine. Using this for your own plugin is allowed when a
	 * credit is placed somewhere in the plugin.
	 * 
	 * Thanks for your cooperate!
	 * 
	 * @author Steffion
	 */

	/**
	 * Send a message to a player. Also replaces the "%player%" variable to the
	 * player's name.
	 * 
	 * @param player
	 *            The player receiving the message.
	 * @param message
	 *            Message which needs to be send to the player.
	 * @param vars
	 *            Variables. Seperated with a - . Ex: "playerName-" +
	 *            player.getName();
	 */
	public static void sendMessage(Player player, String message,
			String... vars) {
		if (player == null) {
			Bukkit.getConsoleSender().sendMessage(
					MessageM.replaceAll(
							message.replaceAll("%player%", "Console"), vars));
		} else {
			player.sendMessage(MessageM.replaceAll(
					message.replaceAll("%player%", player.getName()), vars));
		}
	}

	/**
	 * Send a message to a player from a Config. Also replaces the "%player%"
	 * variable to the player's name.
	 * 
	 * @param player
	 *            The player receiving the message.
	 * @param location
	 *            Location in the config of the message being send.
	 * @param vars
	 *            Variables. Seperated with a - . Ex: "playerName-" +
	 *            player.getName();
	 */
	public static void sendFMessage(Player player, ConfigC location,
			String... vars) {
		if (player == null) {
			Bukkit.getConsoleSender().sendMessage(
					MessageM.replaceAll(
							location.config.getFile().get(location.location)
									.toString()
									.replaceAll("%player%", "Console"), vars));
		} else {
			player.sendMessage(MessageM.replaceAll(
					location.config.getFile().get(location.location).toString()
							.replaceAll("%player%", player.getName()), vars));
		}
	}

	/**
	 * Send a message to all players online. Also replaces the "%player%"
	 * variable to the player's name.
	 * 
	 * @param message
	 *            Message which needs to be send to the player.
	 * @param vars
	 *            Variables. Seperated with a - . Ex: "playerName-" +
	 *            player.getName();
	 */
	public static void broadcastMessage(String message, String... vars) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			// String pMessage = message.replaceAll("%player%",
			// player.getName());
			player.sendMessage(MessageM.replaceAll(
					message.replaceAll("%player%", player.getName()), vars));
		}
		// message = message.replaceAll("%player%", "Console");
		Bukkit.getConsoleSender().sendMessage(
				MessageM.replaceAll(message.replaceAll("%player%", "Console"),
						vars));
	}

	/**
	 * Send a message to all players online from a Config. Also replaces the
	 * "%player%" variable to the player's name.
	 * 
	 * @param location
	 *            Location in the config of the message being send.
	 * @param vars
	 *            Variables . Seperated with a - . Ex: "playerName-" +
	 *            player.getName();
	 */
	public static void broadcastFMessage(ConfigC location, String... vars) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			// String pMessage =
			// location.config.getFile().get(location.location)
			// .toString().replaceAll("%player%", player.getName());
			player.sendMessage(MessageM.replaceAll(
					location.config.getFile().get(location.location).toString()
							.replaceAll("%player%", player.getName()), vars));
		}
		// String message = location.config.getFile().get(location.location)
		// .toString().replaceAll("%player%", "Console");
		Bukkit.getConsoleSender().sendMessage(
				MessageM.replaceAll(
						location.config.getFile().get(location.location)
								.toString().replaceAll("%player%", "Console"),
						vars));
	}

	/**
	 * Replace all variables.
	 * 
	 * @param message
	 *            Message which needs to be replaced.
	 * @param vars
	 *            Variables. Seperated with a - . Ex: "playerName-" +
	 *            player.getName();
	 * @return
	 */
	public static String replaceAll(String message, String... vars) {
		return MessageM.replaceColours(MessageM.replaceColourVars(MessageM
				.replaceVars(message, vars)));
	}

	/**
	 * Replace default Minecraft colour codes.
	 * 
	 * @param message
	 *            Message which needs to be replaced.
	 * @return Colour replaced message.
	 */
	public static String replaceColours(String message) {
		return message.replaceAll("(&([a-fk-or0-9]))", "\u00A7$2");
	}

	/**
	 * Replace colour codes of this plugin.
	 * 
	 * @param message
	 *            Message which needs to be replaced.
	 * @return Colour replaced message.
	 */
	public static String replaceColourVars(String message) {
		message = message.replaceAll("%N", CType.NORMAL());
		message = message.replaceAll("%W", CType.WARNING());
		message = message.replaceAll("%E", CType.ERROR());
		message = message.replaceAll("%A", CType.ARG());
		message = message.replaceAll("%H", CType.HEADER());
		message = message.replaceAll("%TAG", CType.TAG());
		return message;
	}

	/**
	 * Replace custom variables.
	 * 
	 * @param message
	 *            Message which needs to be replaced.
	 * @param vars
	 *            Variables. Seperated with a - . Ex: "playerName-" +
	 *            player.getName();
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
					+ (String) W.config.get(ConfigC.chat_tag)
					+ (String) W.config.get(ConfigC.chat_normal);
		}
	}
}
