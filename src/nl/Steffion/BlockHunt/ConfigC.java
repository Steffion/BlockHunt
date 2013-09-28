package nl.Steffion.BlockHunt;

import nl.Steffion.BlockHunt.Managers.ConfigM;

public enum ConfigC {
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

	chat_tag ("[" + BlockHunt.pdfFile.getName() + "] ", W.config),
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
	commandEnabled_tokens (true, W.config),

	autoUpdateCheck (true, W.config),
	autoDownloadUpdate (false, W.config),

	wandIDname ("STICK", W.config),
	wandName ("%A&l" + BlockHunt.pdfFile.getName() + "%N's selection wand",
			W.config),
	wandDescription (new String[] {
			"%NUse this item to select an arena for your arena.",
			"%ALeft-Click%N to select point #1.",
			"%ARight-Click%N to select point #2.",
			"%NUse the create command to define your arena.",
			"%A/" + BlockHunt.pdfFile.getName() + " <help|h>" }, W.config),

	shop_title ("%H&lBlockHunt %NShop", W.config),
	shop_price ("%NPrice: %A%amount% %Ntokens.", W.config),
	shop_blockChooserEnabled (true, W.config),
	shop_blockChooserIDname ("BOOK", W.config),
	shop_blockChooserPrice (3000, W.config),
	shop_blockChooserName ("%H&lBlock Chooser", W.config),
	shop_blockChooserDescription (new String[] {
			"%NUse this item before the arena starts.",
			"%ARight-Click%N in the lobby and choose",
			"%Nthe block you want to be!", "&6Unlimited uses." }, W.config),
	shop_BlockHuntPassEnabled (true, W.config),
	shop_BlockHuntPassIDname ("NAME_TAG", W.config),
	shop_BlockHuntPassPrice (150, W.config),
	shop_BlockHuntPassName ("%H&lBlockHunt Pass", W.config),
	shop_BlockHuntPassDescription (new String[] {
			"%NUse this item before the arena starts.",
			"%ARight-Click%N in the lobby and choose",
			"%Nif you want to be a Hider or a Seeker!", "&61 use.",
			"&c&lCOMING SOON!" }, W.config),

	sign_LEAVE (new String[] { "%H[" + BlockHunt.pdfFile.getName() + "%H]",
			"&4LEAVE", "&8Right-Click", "&8To leave." }, W.config),
	sign_WAITING (new String[] { "%H[" + BlockHunt.pdfFile.getName() + "%H]",
			"%A%arenaname%", "%A%players%%N/%A%maxplayers%", "&8Waiting..." },
			W.config),
	sign_STARTING (new String[] { "%H[" + BlockHunt.pdfFile.getName() + "%H]",
			"%A%arenaname%", "%A%players%%N/%A%maxplayers%",
			"&2Start: %A%timeleft%" }, W.config),
	sign_INGAME (new String[] { "%H[" + BlockHunt.pdfFile.getName() + "%H]",
			"%A%arenaname%", "%A%players%%N/%A%maxplayers%",
			"%EIngame: %A%timeleft%" }, W.config),

	scoreboard_enabled (true, W.config),
	scoreboard_title ("%H[" + BlockHunt.pdfFile.getName() + "]", W.config),
	scoreboard_timeleft ("%ATime left:", W.config),
	scoreboard_seekers ("%NSeekers:", W.config),
	scoreboard_hiders ("%NHiders:", W.config),

	log_enabledPlugin ("%TAG%N%name%&a&k + %N%version% is now Enabled. Made by %A%autors%%N.",
			W.messages),
	log_disabledPlugin ("%TAG%N%name%&c&k - %N%version% is now Disabled. Made by %A%autors%%N.",
			W.messages),

	help_info ("%NDisplays the plugin's info.", W.messages),
	help_help ("%NShows a list of commands.", W.messages),
	help_reload ("%NReloads all configs.", W.messages),
	help_join ("%NJoins a " + BlockHunt.pdfFile.getName() + " game.",
			W.messages),
	help_leave ("%NLeave a " + BlockHunt.pdfFile.getName() + " game.",
			W.messages),
	help_list ("%NShows a list of available arenas.", W.messages),
	help_shop ("%NOpens the " + BlockHunt.pdfFile.getName() + " shop.",
			W.messages),
	help_start ("%NForces an arena to start.", W.messages),
	help_wand ("%NGives you the wand selection tool.", W.messages),
	help_create ("%NCreates an arena from your selection.", W.messages),
	help_set ("%NOpens a panel to set settings.", W.messages),
	help_setwarp ("%NSets warps for your arena.", W.messages),
	help_remove ("%NDeletes an Arena.", W.messages),
	help_tokens ("%NChange someones tokens.", W.messages),

	button_add ("%NAdd %A%1%%N to %A%2%%N", W.messages),
	button_add2 ("Add", W.messages),
	button_setting ("%NSetting %A%1%%N is now: %A%2%%N.", W.messages),
	button_remove ("%NRemove %A%1%%N from %A%2%%N", W.messages),
	button_remove2 ("Remove", W.messages),

	normal_reloadedConfigs ("%TAG&aReloaded all configs!", W.messages),
	normal_joinJoinedArena ("%TAG%A%playername%%N joined your arena. (%A%1%%N/%A%2%%N)",
			W.messages),
	normal_leaveYouLeft ("%TAG%NYou left the arena! Thanks for playing ;)!",
			W.messages),
	normal_leaveLeftArena ("%TAG%A%playername%%N left your arena. (%A%1%%N/%A%2%%N)",
			W.messages),
	normal_startForced ("%TAG%NYou forced to start arena '%A%arenaname%%N'!",
			W.messages),
	normal_wandGaveWand ("%TAG%NHere you go ;)! &o(Use the %A&o%type%%N&o!)",
			W.messages),
	normal_wandSetPosition ("%TAG%NSet position %A#%number%%N to location: (%A%x%%N, %A%y%%N, %A%z%%N).",
			W.messages),
	normal_createCreatedArena ("%TAG%NCreated an arena with the name '%A%name%%N'.",
			W.messages),
	normal_lobbyArenaIsStarting ("%TAG%NThe arena will start in %A%1%%N second(s)!",
			W.messages),
	normal_lobbyArenaStarted ("%TAG%NThe arena has been started! The seeker is coming to find you in %A%secs%%N seconds!",
			W.messages),
	normal_ingameSeekerChoosen ("%TAG%NPlayer %A%seeker%%N has been choosen as seeker!",
			W.messages),
	normal_ingameBlock ("%TAG%NYou're disguised as a(n) '%A%block%%N' block.",
			W.messages),
	normal_ingameArenaEnd ("%TAG%NThe arena will end in %A%1%%N second(s)!",
			W.messages),
	normal_ingameGivenSword ("%TAG%NYou were given a sword!", W.messages),
	normal_HiderDied ("%TAG%NHider %A%playername%%N died! %A%left%%N hider(s) remain...",
			W.messages),
	normal_SeekerDied ("%TAG%NSeeker %A%playername%%N died! He will respawn in %A%secs%%N seconds!",
			W.messages),
	normal_winSeekers ("%TAG%NThe %ASEEKERS%N have won!", W.messages),
	normal_winHiders ("%TAG%NThe %AHIDERS%N have won!", W.messages),
	normal_setwarpWarpSet ("%TAG%NSet warp '%A%warp%%N' to your location!",
			W.messages),
	normal_addedToken ("%TAG%A%amount%%N tokens were added to your account!",
			W.messages),
	normal_removeRemovedArena ("%TAG%NRemoved arena '%A%name%%N'!", W.messages),
	normal_tokensChanged ("%TAG%N%option% %A%amount%%N tokens %option2% %A%playername%%N.",
			W.messages),
	normal_ingameNowSolid ("%TAG%NYou're now a solid '%A%block%%N' block!",
			W.messages),
	normal_ingameNoMoreSolid ("%TAG%NYou're no longer a solid block!",
			W.messages),
	normal_shopBoughtItem ("%TAG%NYou've bought the '%A%itemname%%N' item!",
			W.messages),
	normal_shopChoosenBlock ("%TAG%NYou've choosen to be a(n) '%A%block%%N' block!",
			W.messages),

	warning_lobbyNeedAtleast ("%TAG%WYou need atleast %A%1%%W player(s) to start the game!",
			W.messages),
	warning_ingameNEWSeekerChoosen ("%TAG%WThe last seeker left and a new seeker has been choosen!",
			W.messages),
	warning_unableToCommand ("%TAG%WSorry but that command is disabled in the arena.",
			W.messages),
	warning_ingameNoSolidPlace ("%TAG%WThat's not a valid place to become solid!",
			W.messages),
	warning_arenaStopped ("%TAG%WThe arena has been forced to stop!",
			W.messages),

	error_noPermission ("%TAG%EYou don't have the permissions to do that!",
			W.messages),
	error_notANumber ("%TAG%E'%A%1%%E' is not a number!", W.messages),
	error_commandNotEnabled ("%TAG%EThis command has been disabled!",
			W.messages),
	error_commandNotFound ("%TAG%ECouldn't find the command. Try %A/"
			+ BlockHunt.pdfFile.getName() + " help %Efor more info.",
			W.messages),
	error_notEnoughArguments ("%TAG%EYou're missing arguments, correct syntax: %A%syntax%",
			W.messages),
	error_libsDisguisesNotInstalled ("%TAG%EThe plugin '%ALib's Disguises%E' is required to run this plugin! Intall it or it won't work!",
			W.messages),
	error_protocolLibNotInstalled ("%TAG%EThe plugin '%AProtocolLib%E' is required to run this plugin! Intall it or it won't work!",
			W.messages),
	error_noArena ("%TAG%ENo arena found with the name '%A%name%%E'.",
			W.messages),
	error_onlyIngame ("%TAG%EThis is an only in-game command!", W.messages),
	error_joinAlreadyJoined ("%TAG%EYou've already joined an arena!",
			W.messages),
	error_joinNoBlocksSet ("%TAG%EThere are none blocks set for this arena. Notify the administrator.",
			W.messages),
	error_joinWarpsNotSet ("%TAG%EThere are no warps set for this arena. Notify the administrator.",
			W.messages),
	error_joinArenaIngame ("%TAG%EThis game has already started.", W.messages),
	error_joinFull ("%TAG%EUnable to join this arena. It's full!", W.messages),
	error_leaveNotInArena ("%TAG%EYou're not in an arena!", W.messages),
	error_createSelectionFirst ("%TAG%EMake a selection first. Use the wand command: %A/"
			+ BlockHunt.pdfFile.getName() + " <wand|w>%E.",
			W.messages),
	error_createNotSameWorld ("%TAG%EMake your selection points in the same world!",
			W.messages),
	error_setTooHighNumber ("%TAG%EThat amount is too high! Max amount is: %A%max%%E.",
			W.messages),
	error_setTooLowNumber ("%TAG%EThat amount is too low! Minimal amount is: %A%min%%E.",
			W.messages),
	error_setNotABlock ("%TAG%EThat is not a block!", W.messages),
	error_setwarpWarpNotFound ("%TAG%EWarp '%A%warp%%E' is not valid!",
			W.messages),
	error_tokensPlayerNotOnline ("%TAG%ENo player found with the name '%A%playername%%E'!",
			W.messages),
	error_tokensUnknownsetting ("%TAG%E'%A%option%%E' is not a known option!",
			W.messages),
	error_shopNeedMoreTokens ("%TAG%EYou need more tokens before you can buy this item.",
			W.messages);

	public Object value;
	public ConfigM config;
	public String location;

	/**
	 * Makes an object from the list above.
	 * 
	 * @param value
	 *            Setting in the config file.
	 * @param config
	 *            The config file.
	 */
	private ConfigC (Object value, ConfigM config) {
		this.value = value;
		this.config = config;
		this.location = this.name().replaceAll("_", ".");
	}
}
