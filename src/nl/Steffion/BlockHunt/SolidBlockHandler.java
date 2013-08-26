package nl.Steffion.BlockHunt;

import java.util.LinkedList;

import nl.Steffion.BlockHunt.Managers.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

public class SolidBlockHandler {
	public static void makePlayerUnsolid(Player player, ItemStack block) {
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

		LinkedList<String> data = new LinkedList<String>();
		data.add("blockID:" + block.getTypeId());
		data.add("blockData:" + block.getDurability());
		Disguise disguise = new Disguise(W.dcAPI.newEntityID(), data,
				DisguiseType.FallingBlock);
		if (W.dcAPI.isDisguised(player)) {
			W.dcAPI.changePlayerDisguise(player, disguise);
		} else {
			W.dcAPI.disguisePlayer(player, disguise);
		}

		MessageM.sendFMessage(player, ConfigC.normal_ingameNoMoreSolid, true);
	}
}
