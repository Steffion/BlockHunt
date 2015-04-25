package nl.Steffion.BlockHunt.util;

import java.util.logging.Level;

import nl.Steffion.BlockHunt.BlockHunt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Messager implements Listener {
	/**
	 * Replace default Minecraft colour codes.
	 *
	 * @param message
	 *            - Message which needs to be replaced.
	 * @return Colour replaced message.
	 */
	public static String replaceColours(final String message) {
		return message.replaceAll("(&([a-fk-or0-9]))", "\u00A7$2");
	}

	/**
	 * Send a locale message to the console.
	 *
	 * @param level
	 *            - One of the message level identifiers, e.g., SEVERE.
	 * @param config
	 *            - The config file to get the message from.
	 * @param path
	 *            - The path in the messages file.
	 * @param replaceVars
	 *            - OPTIONAL Replace one or more variables in the message. Use
	 *            '%name%' in the config files.<br>
	 *            It replaces the first value with the second value, e.g.:
	 *            "name", test.getName()
	 */
	public static void sendConsoleMessage(final Level level,
			final Config config, final String path, final String... replaceVars) {
		String locale = BlockHunt.locale.getString("users.CONSOLE.language");

		if (config.getString(locale + "." + path) == null) {
			locale = BlockHunt.locale.getString("general.defaultLanguage");
			if (config.getString(locale + "." + path) == null) {
				Messager.sendConsoleMessage(Level.SEVERE, BlockHunt.messages,
						"messager.missingValue", "setting", path, "config",
						config.configName);
				return;
			}
		}

		if (BlockHunt.locale.getBoolean("general.forceLanguage")) {
			if (config.getString(BlockHunt.locale
					.getString("general.defaultLanguage") + "." + path) == null) {
				locale = "GB";
			} else {
				locale = BlockHunt.locale.getString("general.defaultLanguage");
			}
		}

		String message = config.getString(locale + "." + path).replaceAll(
				"(&([a-fk-or0-9]))", "");

		if (replaceVars != null) {
			Integer counter = 0;

			for (int i = 0; i < (replaceVars.length / 2); i++) {
				message = message.replaceAll("%" + replaceVars[counter] + "%",
						replaceVars[counter + 1]);
				counter = counter + 2;
			}
		}

		BlockHunt.plugin.getLogger().log(level, message);
	}

	/**
	 * Send a locale message to a player.<br>
	 * <br>
	 * <b>DO NOT</b> use this method to send to the console.<br>
	 * Use {@link #sendConsoleMessage(Level, Config, String, String...)}
	 * instead.
	 *
	 * @param player
	 *            - The player to send the locale message to.
	 * @param config
	 *            - The config file to get the message from.
	 * @param path
	 *            - The path in the messages file.
	 * @param replaceVars
	 *            - OPTIONAL Replace one or more variables in the message. Use
	 *            '%name%' in the config files.<br>
	 *            It replaces the first value with the second value, e.g.:
	 *            "name", test.getName()
	 */
	public static void sendMessage(final Player player, final Config config,
			final String path, final String... replaceVars) {
		if (player == null) {
			Messager.sendConsoleMessage(Level.SEVERE, BlockHunt.messages,
					"messager.wrongMethod");
			return;
		}

		String locale = BlockHunt.locale.getString("users."
				+ player.getUniqueId().toString() + ".language");

		if (config.getString(locale + "." + path) == null) {
			locale = BlockHunt.locale.getString("general.defaultLanguage");
			if (config.getString(locale + "." + path) == null) {
				Messager.sendMessage(player, BlockHunt.messages,
						"messager.missingValue", "setting", path, "config",
						config.configName);
				return;
			}
		}

		if (BlockHunt.locale.getBoolean("general.forceLanguage")) {
			if (config.getString(BlockHunt.locale
					.getString("general.defaultLanguage") + "." + path) == null) {
				locale = "GB";
			} else {
				BlockHunt.locale.getString("general.defaultLanguage");
			}
		}

		String message = config.getString(locale + "." + path);

		if (replaceVars != null) {
			Integer counter = 0;

			for (int i = 0; i < (replaceVars.length / 2); i++) {
				message = message.replaceAll("%" + replaceVars[counter] + "%",
						replaceVars[counter + 1]);
				counter = counter + 2;
			}
		}

		player.sendMessage(Messager.replaceColours(message));
	}

	/*
	 * The join event to check and save the player's language.
	 */
	@EventHandler
	public void onPlayerJoinEvent(final PlayerJoinEvent event) {
		if (!Locale.initiated) {
			Locale.initiateLocaleSystem();
		}

		final Player player = event.getPlayer();
		BlockHunt.locale.set("users." + player.getUniqueId().toString()
				+ ".name", player.getName());
		BlockHunt.locale.set("users." + player.getUniqueId().toString()
				+ ".language", Locale.getCountryCode(player));
		BlockHunt.locale.saveConfig();

		// TODO BlockHunt - remove this test.
		Messager.sendMessage(player, BlockHunt.messages, "test");

		// TODO BlockHunt - add a language chooser, using JSON messages/buttons
	}
}
