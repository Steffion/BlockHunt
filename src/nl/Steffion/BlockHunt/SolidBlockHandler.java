package nl.Steffion.BlockHunt;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SolidBlockHandler {
	@SuppressWarnings("deprecation")
	public static void makePlayerUnsolid(Player player) {
		ItemStack block = player.getInventory().getItem(8);
		Block pBlock = player.getLocation().getBlock();

		if (W.hiddenLoc.get(player) != null) {
			pBlock = W.hiddenLoc.get(player).getBlock();
		}

		block.setAmount(5);
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (!pl.equals(player)) {
				if (W.hiddenLocWater.get(player) != null) {
					if (W.hiddenLocWater.get(player)) {
						pl.sendBlockChange(pBlock.getLocation(),
								Material.STATIONARY_WATER, (byte) 0);
					} else {
						pl.sendBlockChange(pBlock.getLocation(), Material.AIR,
								(byte) 0);
					}
				} else {
					pl.sendBlockChange(pBlock.getLocation(), Material.AIR,
							(byte) 0);
				}

				W.hiddenLocWater.remove(player);
			}
		}

		player.playSound(player.getLocation(), Sound.BAT_HURT, 1, 1);
		block.removeEnchantment(Enchantment.DURABILITY);

		for (Player playerShow : Bukkit.getOnlinePlayers()) {
			playerShow.showPlayer(player);
		}

		MiscDisguise disguise = new MiscDisguise(DisguiseType.FALLING_BLOCK,
				block.getTypeId(), block.getDurability());
		DisguiseAPI.disguiseToAll(player, disguise);

		MessageM.sendFMessage(player, ConfigC.normal_ingameNoMoreSolid);
	}
}
