package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.InventoryHandler;
import nl.Steffion.BlockHunt.Managers.PlayerM;
import nl.Steffion.BlockHunt.Managers.PlayerM.PermsC;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDshop extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (PlayerM.hasPerm(player, PermsC.shop, true)) {
			InventoryHandler.openShop(player);
		}
		return true;
	}
}
