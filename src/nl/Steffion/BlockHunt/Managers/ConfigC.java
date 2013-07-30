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
	commandEnabled_wand (true, W.config),

	wandID (280, W.config),
	wandName ("%A&l" + W.pluginName + "%N's selection wand", W.config),
	wandDescription (new String[] {
			"%NUse this item to select an arena for your arena.",
			"%ALeft-Click%N to select point #1.",
			"%ARight-Click%N to select point #2.",
			"%NUse the create command to define your arena.",
			"%A/" + W.pluginName + " <help|h>" }, W.config),

	log_Enabled ("%N%name%&a&k + %N%version% is now Enabled. Made by %A%autors%%N.",
			W.messages),
	log_Disabled ("%N%name%&c&k - %N%version% is now Disabled. Made by %A%autors%%N.",
			W.messages),

	help_info ("%NDisplays the plugin's info.", W.messages),
	help_help ("%NShows a list of commands.", W.messages),
	help_reload ("%NReloads all configs.", W.messages),
	help_wand ("%NGives you the wand selection tool.", W.messages),

	normal_reloadedConfigs ("&aReloaded all configs!", W.messages),
	normal_gaveWand ("%NHere you go ;)! &o(Use the %A&o%type%%N&o!)",
			W.messages),
	normal_setPosition ("%NSet position %A#%number%%N to location: %pos%.",
			W.messages),

	error_noPermission ("%EYou don't have the permissions to do that!",
			W.messages),
	error_commandNotEnabled ("%EThis command has been disabled!", W.messages),
	error_commandNotFound ("%ECouldn't find the command. Try %A/"
			+ W.pluginName + " help %Efor more info.", W.messages),
	error_notEnoughArguments ("%EYou're missing arguments, correct syntax: %A/%syntax%",
			W.messages),
	error_noArena ("%ENo arena found with the name '%A%name%%E'.", W.messages),
	error_onlyIngame ("%EThis is an only in-game command!", W.messages);

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
