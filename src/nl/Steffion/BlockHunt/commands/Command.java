package nl.Steffion.BlockHunt.commands;

import org.bukkit.command.CommandSender;

import nl.Steffion.BlockHunt.BlockHunt;

/**
 * This is the abstract class of a command.
 *
 * @author Steffion (Stef de Goey) 2016
 *
 */
public abstract class Command {
	private String		help;
	private boolean		ingameCommand;
	private String		permission;
	protected BlockHunt	plugin;
	private String		usage;
	
	protected Command(String usage, String permission, boolean ingameCommand, String help) {
		plugin = BlockHunt.getPlugin();
		
		this.usage = usage;
		this.permission = permission;
		this.ingameCommand = ingameCommand;
		this.help = help;
	}

	/**
	 *
	 * @return The help string of this command.
	 */
	protected String getHelp() {
		return help;
	}

	/**
	 *
	 * @return The permission (String) of this command.
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 *
	 * @return The usage of this command.
	 */
	protected String getUsage() {
		return usage;
	}

	/**
	 *
	 * @return Boolean if this is an in-game only command.
	 */
	public boolean isIngameCommand() {
		return ingameCommand;
	}

	/**
	 * Execute the command.
	 *
	 * @param sender
	 *            - The person who executes it.
	 * @param args
	 *            - The arguments provided with the command.
	 * @return Boolean if the command was executed correctly.
	 */
	public abstract boolean runCommand(CommandSender sender, String[] args);

}
