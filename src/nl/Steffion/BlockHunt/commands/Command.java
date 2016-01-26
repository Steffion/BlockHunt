package nl.Steffion.BlockHunt.commands;

import org.bukkit.command.CommandSender;

import nl.Steffion.BlockHunt.BlockHunt;

public abstract class Command {
	private String		help;
	private boolean		ingameCommand;
	private String		permission;
	protected BlockHunt	plugin;
	private String		usage;

	public Command(String usage, String permission, boolean ingameCommand, String help) {
		plugin = BlockHunt.getPlugin();
		
		this.usage = usage;
		this.permission = permission;
		this.ingameCommand = ingameCommand;
		this.help = help;
	}

	public boolean checkPermission(CommandSender sender) {
		if (!sender.hasPermission(permission)) {
			sender.sendMessage(
					"§cI'm sorry, but I cannot allow you to do that. Insufficient permissions. <" + permission + ">");
			return false;
		}

		return true;
	}

	public String getHelp() {
		return help;
	}

	public String getPermission() {
		return permission;
	}

	public String getUsage() {
		return usage;
	}

	public boolean isIngameCommand() {
		return ingameCommand;
	}

	public abstract boolean runCommand(CommandSender sender, String[] args);

	public void setHelp(String help) {
		this.help = help;
	}

	public void setIngameCommand(boolean ingameCommand) {
		this.ingameCommand = ingameCommand;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

}
