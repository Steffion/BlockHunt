package nl.Steffion.BlockHunt.Commands;

import java.util.ArrayList;
import java.util.List;

import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.W;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CMDwand extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			ItemStack wand = new ItemStack(
					Material.getMaterial((String) W.config
							.get(ConfigC.wandIDname)));
			ItemMeta im = wand.getItemMeta();
			im.setDisplayName(MessageM.replaceAll((String) W.config
					.get(ConfigC.wandName)));
			W.config.load();
			List<String> lores = W.config.getFile().getStringList(
					ConfigC.wandDescription.location);
			List<String> lores2 = new ArrayList<String>();
			for (String lore : lores) {
				lores2.add(MessageM.replaceAll(lore));
			}

			im.setLore(lores2);
			wand.setItemMeta(im);
			player.getInventory().addItem(wand);
			player.playSound(player.getLocation(), Sound.ORB_PICKUP, 5, 0);
			MessageM.sendFMessage(player, ConfigC.normal_wandGaveWand, "type-"
					+ wand.getType().toString().replaceAll("_", " ")
							.toLowerCase());
		} else {
			MessageM.sendFMessage(player, ConfigC.error_onlyIngame);
		}
		return true;
	}
}
