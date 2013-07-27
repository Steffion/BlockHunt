package nl.Steffion.BlockHunt.Managers;

import nl.Steffion.BlockHunt.W;

public enum ConfigC {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	chat_tag ("[" + W.pluginName + "] ", W.config),
	chat_normal ("&b", W.config),
	chat_warning ("&c", W.config),
	chat_error ("&c", W.config),
	chat_arg ("&e", W.config),
	chat_header ("&9", W.config),
	chat_headerhigh ("%H_______.[ %A%header%%H ]._______", W.config),

	commandEnabled_info (true, W.config),
	commandEnabled_help (true, W.config),
	commandEnabled_reload (true, W.config),

	log_Enabled ("%N%name%&a&k + %N%version% is now Enabled. Made by %A%autors%%N.",
			W.messages),
	log_Disabled ("%N%name%&c&k - %N%version% is now Disabled. Made by %A%autors%%N.",
			W.messages),

	help_info ("%NDisplays the plugin's info.", W.messages),
	help_help ("%NShows a list of commands.", W.messages),
	help_reload ("%NReloads all configs.", W.messages),

	normal_reloadedConfigs ("&aReloaded all configs!", W.messages),

	error_noPermission ("%EYou don't have the permissions to do that!",
			W.messages),
	error_commandNotEnabled ("%EThis command has been disabled!", W.messages),
	error_commandNotFound ("%ECouldn't find the command. Try %A/"
			+ W.pluginName + " help %Efor more info.", W.messages),
	error_notEnoughArguments ("%EYou're missing arguments, correct syntax: %A/%syntax%",
			W.messages);

	Object value;
	ConfigM config;

	private ConfigC (Object value, ConfigM config) {
		this.value = value;
		this.config = config;
	}

	public String getLocation() {
		return this.name().replaceAll("_", ".");

	}
}
