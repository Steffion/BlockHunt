package nl.Steffion.BlockHunt.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.data.Arena;

public class CommandLeave extends Command {

	public CommandLeave() {
		super("blockhunt leave", "blockhunt.leave", true, "Leave the current lobby/arena.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if (plugin.getEditors().containsKey(player.getUniqueId())) {
			Arena arena = plugin.getEditors().get(player.getUniqueId());

			if (arena.getHidersSpawn() != null) {
				player.sendBlockChange(arena.getHidersSpawn(), Material.AIR, (byte) 0);
			}
			
			if (arena.getLobbyLocation() != null) {
				player.sendBlockChange(arena.getLobbyLocation(), Material.AIR, (byte) 0);
			}

			if (arena.getSeekersSpawn() != null) {
				player.sendBlockChange(arena.getSeekersSpawn(), Material.AIR, (byte) 0);
			}

			arena.save();

			plugin.getPlayerData(player).restore();
			plugin.getEditorsRenamingArena().remove(player.getUniqueId());
			plugin.getEditors().remove(player.getUniqueId());

			player.sendMessage("You left the editor mode of '" + arena.getName() + "'.");
			return true;
		}

		player.sendMessage("§cYou are not in a lobby/arena!");
		return true;
	}

}
