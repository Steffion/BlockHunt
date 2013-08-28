package nl.Steffion.BlockHunt.Managers;

import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Commands.*;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

public enum CommandC {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	INFO ("BlockHunt%_",
			"BlockHunt%_",
			new CMDinfo(),
			ConfigC.commandEnabled_info,
			PermsC.info,
			ConfigC.help_info,
			0,
			"-"),
	INFO2 ("BlockHunt%info_",
			"BlockHunt%i_",
			new CMDinfo(),
			ConfigC.commandEnabled_info,
			PermsC.info,
			ConfigC.help_info,
			1,
			W.pluginName + " [info|i]"),
	HELP ("BlockHunt%help_",
			"BlockHunt%h_",
			new CMDhelp(),

			ConfigC.commandEnabled_help,
			PermsC.help,
			ConfigC.help_help,
			1,
			W.pluginName + " <help|h> [pagenumber]"),
	RELOAD ("BlockHunt%reload_",
			"BlockHunt%r_",
			new CMDreload(),
			ConfigC.commandEnabled_reload,
			PermsC.reload,
			ConfigC.help_reload,
			1,
			W.pluginName + " <reload|r>"),
	JOIN ("BlockHunt%join_",
			"BlockHunt%j_",
			new CMDjoin(),
			ConfigC.commandEnabled_join,
			PermsC.join,
			ConfigC.help_join,
			1,
			W.pluginName + " <join|j> <arenaname>"),
	LEAVE ("BlockHunt%leave_",
			"BlockHunt%l_",
			new CMDleave(),
			ConfigC.commandEnabled_leave,
			PermsC.leave,
			ConfigC.help_leave,
			1,
			W.pluginName + " <leave|l>"),
	LIST ("BlockHunt%list_",
			"BlockHunt%li_",
			new CMDlist(),
			ConfigC.commandEnabled_list,
			PermsC.list,
			ConfigC.help_list,
			1,
			W.pluginName + " <list|li>"),
	SHOP ("BlockHunt%shop_",
			"BlockHunt%sh_",
			new CMDshop(),
			ConfigC.commandEnabled_shop,
			PermsC.shop,
			ConfigC.help_shop,
			1,
			W.pluginName + " <shop|sh>"),
	START ("BlockHunt%start_",
			"BlockHunt%go_",
			new CMDstart(),
			ConfigC.commandEnabled_start,
			PermsC.start,
			ConfigC.help_start,
			1,
			W.pluginName + " <start|go> <arenaname>"),
	WAND ("BlockHunt%wand_",
			"BlockHunt%w_",
			new CMDwand(),
			ConfigC.commandEnabled_wand,
			PermsC.create,
			ConfigC.help_wand,
			1,
			W.pluginName + " <wand|w>"),
	CREATE ("BlockHunt%create_",
			"BlockHunt%c_",
			new CMDcreate(),
			ConfigC.commandEnabled_create,
			PermsC.create,
			ConfigC.help_create,
			1,
			W.pluginName + " <create|c> <arenaname>"),
	SET ("BlockHunt%set_",
			"BlockHunt%s_",
			new CMDset(),
			ConfigC.commandEnabled_set,
			PermsC.set,
			ConfigC.help_set,
			1,
			W.pluginName + " <set|s> <arenaname>"),
	SETWARP ("BlockHunt%setwarp_",
			"BlockHunt%sw_",
			new CMDsetwarp(),
			ConfigC.commandEnabled_setwarp,
			PermsC.setwarp,
			ConfigC.help_setwarp,
			1,
			W.pluginName + " <setwarp|sw> <lobby|hiders|seekers> <arenaname>"),
	REMOVE ("BlockHunt%remove_",
			"BlockHunt%delete_",
			new CMDremove(),
			ConfigC.commandEnabled_remove,
			PermsC.remove,
			ConfigC.help_remove,
			1,
			W.pluginName + " <remove|delete> <arenaname>"),
	NOT_FOUND ("%_",
			"%_",
			new CMDnotfound(),
			null,
			PermsC.info,
			ConfigC.help_info,
			0,
			"-");

	public String command;
	public String alias;
	public DefaultCMD cmd;
	public ConfigC enabled;
	public PermsC perm;
	public ConfigC help;
	public int minLenght;
	public String usage;

	private CommandC (String command, String alias, DefaultCMD cmd,
			ConfigC enabled, PermsC perm, ConfigC help, int minLenght,
			String usage) {
		this.command = command;
		this.alias = alias;
		this.cmd = cmd;
		this.enabled = enabled;
		this.perm = perm;
		this.help = help;
		this.minLenght = minLenght;
		this.usage = usage;
	}
}