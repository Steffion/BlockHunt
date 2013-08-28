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
	commandEnabled_join (true, W.config),
	commandEnabled_leave (true, W.config),
	commandEnabled_list (true, W.config),
	commandEnabled_shop (true, W.config),
	commandEnabled_start (true, W.config),
	commandEnabled_wand (true, W.config),
	commandEnabled_create (true, W.config),
	commandEnabled_set (true, W.config),
	commandEnabled_setwarp (true, W.config),
	commandEnabled_remove (true, W.config),

	autoUpdateCheck (true, W.config),
	autoDownloadUpdate (false, W.config),

	wandID (280, W.config),
	wandName ("%A&l" + W.pluginName + "%N's selection wand", W.config),
	wandDescription (new String[] {
			"%NUse this item to select an arena for your arena.",
			"%ALeft-Click%N to select point #1.",
			"%ARight-Click%N to select point #2.",
			"%NUse the create command to define your arena.",
			"%A/" + W.pluginName + " <help|h>" }, W.config),

	shop_price ("%NPrice: %A%amount% %Ntokens.", W.config),
	shop_blockChooserEnabled (true, W.config),
	shop_blockChooserID (345, W.config),
	shop_blockChooserPrice (3000, W.config),
	shop_blockChooserName ("%H&lBlock Chooser", W.config),
	shop_blockChooserDescription (new String[] {
			"%NUse this item before the arena starts.",
			"%ARight-Click%N in the lobby and choose",
			"%Nthe block you want to be!", "&6Unlimited uses." }, W.config),

	sign_LEAVE (new String[] { "%H[" + W.pluginName + "%H]", "&4LEAVE",
			"&8Right-Click", "&8To leave." }, W.config),
	sign_WAITING (new String[] { "%H[" + W.pluginName + "%H]", "%A%arenaname%",
			"%A%players%%N/%A%maxplayers%", "&8Waiting..." }, W.config),
	sign_STARTING (new String[] { "%H[" + W.pluginName + "%H]",
			"%A%arenaname%", "%A%players%%N/%A%maxplayers%",
			"&2Start: %A%timeleft%" }, W.config),
	sign_INGAME (new String[] { "%H[" + W.pluginName + "%H]", "%A%arenaname%",
			"%A%players%%N/%A%maxplayers%", "%EIngame: %A%timeleft%" },
			W.config),

	scoreboard_timeleft ("%ATime left:", W.config),
	scoreboard_seekers ("%NSeekers:", W.config),
	scoreboard_hiders ("%NHiders:", W.config),

	log_Enabled ("%N%name%&a&k + %N%version% is now Enabled. Made by %A%autors%%N.",
			W.messages),
	log_Disabled ("%N%name%&c&k - %N%version% is now Disabled. Made by %A%autors%%N.",
			W.messages),

	help_info ("%NDisplays the plugin's info.", W.messages),
	help_help ("%NShows a list of commands.", W.messages),
	help_reload ("%NReloads all configs.", W.messages),
	help_join ("%NJoins a " + W.pluginName + " game.", W.messages),
	help_leave ("%NLeave a " + W.pluginName + " game.", W.messages),
	help_list ("%NShows a list of available arenas.", W.messages),
	help_shop ("%NOpens the " + W.pluginName + " shop.", W.messages),
	help_start ("%NForces an arena to start.", W.messages),
	help_wand ("%NGives you the wand selection tool.", W.messages),
	help_create ("%NCreates an arena from your selection.", W.messages),
	help_set ("%NOpens a panel to set settings.", W.messages),
	help_setwarp ("%NSets warps for your arena.", W.messages),
	help_remove ("%NDeletes an Arena.", W.messages),

	button_add ("%NAdd %A%1%%N to %A%2%%N", W.messages),
	button_add2 ("Add", W.messages),
	button_setting ("%NSetting %A%1%%N is now: %A%2%%N.", W.messages),
	button_remove ("%NRemove %A%1%%N from %A%2%%N", W.messages),
	button_remove2 ("Remove", W.messages),

	normal_reloadedConfigs ("&aReloaded all configs!", W.messages),
	normal_joinJoinedArena ("%A%playername%%N joined your arena. (%A%1%%N/%A%2%%N)",
			W.messages),
	normal_leaveYouLeft ("%NYou left the arena! Thanks for playing ;)!",
			W.messages),
	normal_leaveLeftArena ("%A%playername%%N left your arena. (%A%1%%N/%A%2%%N)",
			W.messages),
	normal_startForced ("%NYou forced to start arena '%A%arenaname%%N'!",
			W.messages),
	normal_wandGaveWand ("%NHere you go ;)! &o(Use the %A&o%type%%N&o!)",
			W.messages),
	normal_wandSetPosition ("%NSet position %A#%number%%N to location: (%A%x%%N, %A%y%%N, %A%z%%N).",
			W.messages),
	normal_createCreatedArena ("%NCreated an arena with the name '%A%name%%N'.",
			W.messages),
	normal_lobbyArenaIsStarting ("%NThe arena will start in %A%1%%N second(s)!",
			W.messages),
	normal_lobbyArenaStarted ("%NThe arena has been started! The seeker is coming to find you in %A%secs%%N seconds!",
			W.messages),
	normal_ingameSeekerChoosen ("%NPlayer %A%seeker%%N has been choosen as seeker!",
			W.messages),
	normal_ingameBlock ("%NYou're disguised as a(n) '%A%block%%N' block.",
			W.messages),
	normal_ingameArenaEnd ("%NThe arena will end in %A%1%%N second(s)!",
			W.messages),
	normal_ingameGivenSword ("%NYou were given a sword!", W.messages),
	normal_HiderDied ("%NHider %A%playername%%N died! %A%left%%N hider(s) remain...",
			W.messages),
	normal_SeekerDied ("%NSeeker %A%playername%%N died! He will respawn in %A%secs%%N seconds!",
			W.messages),
	normal_winSeekers ("%NThe %ASEEKERS%N have won!", W.messages),
	normal_winHiders ("%NThe %AHIDERS%N have won!", W.messages),
	normal_setwarpWarpSet ("%NSet warp '%A%warp%%N' to your location!",
			W.messages),
	normal_removeRemovedArena ("%NRemoved arena '%A%name%%N'!", W.messages),
	normal_ingameNowSolid ("%NYou're now a solid '%A%block%%N' block!",
			W.messages),
	normal_ingameNoMoreSolid ("%NYou're no longer a solid block!", W.messages),
	normal_ShopBoughtItem ("%NYou've bought the '%A%itemname%%N' item!",
			W.messages),
	normal_ShopChoosenBlock ("%NYou've choosen to be a(n) '%A%block%%N' block!",
			W.messages),

	warning_lobbyNeedAtleast ("%WYou need atleast %A%1%%W player(s) to start the game!",
			W.messages),
	warning_ingameNEWSeekerChoosen ("%WThe last seeker left and a new seeker has been choosen!",
			W.messages),
	warning_unableToCommand ("%WSorry but that command is disabled in the arena.",
			W.messages),
	warning_ingameNoSolidPlace ("%WThat's not a valid place to become solid!",
			W.messages),
	warning_arenaStopped ("%WThe arena has been forced to stop!", W.messages),

	error_noPermission ("%EYou don't have the permissions to do that!",
			W.messages),
	error_commandNotEnabled ("%EThis command has been disabled!", W.messages),
	error_commandNotFound ("%ECouldn't find the command. Try %A/"
			+ W.pluginName + " help %Efor more info.", W.messages),
	error_notEnoughArguments ("%EYou're missing arguments, correct syntax: %A/%syntax%",
			W.messages),
	error_disguiseCraftNotInstalled ("%EThe plugin '%ADisguiseCraft%E' is required to run this plugin! Intall it or it won't work!",
			W.messages),
	error_protocolLibNotInstalled ("%EThe plugin '%AProtocolLib%E' is required to run this plugin! Intall it or it won't work!",
			W.messages),
	error_noArena ("%ENo arena found with the name '%A%name%%E'.", W.messages),
	error_onlyIngame ("%EThis is an only in-game command!", W.messages),
	error_joinAlreadyJoined ("%EYou've already joined an arena!", W.messages),
	error_joinNoBlocksSet ("%EThere are none blocks set for this arena. Notify the administrator.",
			W.messages),
	error_joinWarpsNotSet ("%EThere are no warps set for this arena. Notify the administrator.",
			W.messages),
	error_joinArenaIngame ("%EThis game has already started.", W.messages),
	error_joinFull ("%EUnable to join this arena. It's full!", W.messages),
	error_leaveNotInArena ("%EYou're not in an arena!", W.messages),
	error_createSelectionFirst ("%EMake a selection first. Use the wand command: %A/"
			+ W.pluginName + " <wand|w>%E.",
			W.messages),
	error_createNotSameWorld ("%EMake your selection points in the same world!",
			W.messages),
	error_setTooHighNumber ("%EThat amount is too high! Max amount is: %A%max%%E.",
			W.messages),
	error_setTooLowNumber ("%EThat amount is too low! Minimal amount is: %A%min%%E.",
			W.messages),
	error_setNotABlock ("%EThat is not a block!", W.messages),
	error_setwarpWarpNotFound ("%EWarp '%A%warp%%E' is not valid!", W.messages),
	error_ShopNeedMoreTokens ("%EYou need more tokens before you can buy this item.",
			W.messages);

	public Object value;
	public ConfigM config;

	private ConfigC (Object value, ConfigM config) {
		this.value = value;
		this.config = config;
	}

	public String getLocation() {
		return this.name().replaceAll("_", ".");

	}
}
