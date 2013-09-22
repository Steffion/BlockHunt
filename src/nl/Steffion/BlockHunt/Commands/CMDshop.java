package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.InventoryHandler;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDshop extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		InventoryHandler.openShop(player);
		return true;
	}
}
