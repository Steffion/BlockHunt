package nl.Steffion.BlockHunt.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.data.Arena;

/**
 * 
 * @author Steffion (Stef de Goey) 2016
 *
 */
public class CommandLeave extends Command {

	public CommandLeave() {
		super("blockhunt leave", "blockhunt.leave", true, "Leave the current lobby/arena.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		if (plugin.getArenaHandler().getAllEditors().contains(player)) {
			Arena arena = plugin.getArenaHandler().getArena(player);

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
			arena.removeEditor();

			plugin.getPlayerHandler().getPlayerData(player).restore();
			
			player.sendMessage("You left the editor mode of '" + arena.getName() + "'.");
			return true;
		}
		
		if (plugin.getArenaHandler().getAllPlayers().contains(player)) {
			plugin.getArenaHandler().getArena(player).removePlayer(player);

			player.sendMessage("You left the arena.");
			return true;
		}

		player.sendMessage("§cYou are not in a lobby/arena!");
		return true;
	}

}
