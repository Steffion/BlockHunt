package nl.Steffion.BlockHunt.playerdata;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockHuntPlayer {
	private boolean		allowFlight;
	private ItemStack[]	armorContents;
	private float		exp;
	private int			foodLevel;
	private double		health;
	private ItemStack[]	inventoryContents;
	private int			level;
	private UUID		uuid;

	public void clear() {
		Player player = Bukkit.getPlayer(uuid);

		player.setAllowFlight(false);
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.setExp(0);
		player.setFoodLevel(20);
		player.setHealth(20);

		for (int i = 0; i < inventoryContents.length; i++) {
			player.getInventory().setItem(i, null);
		}

		player.setLevel(0);
	}

	public void restore() {
		Player player = Bukkit.getPlayer(uuid);
		
		player.setAllowFlight(allowFlight);
		player.getInventory().setHelmet(armorContents[3]);
		player.getInventory().setChestplate(armorContents[2]);
		player.getInventory().setLeggings(armorContents[1]);
		player.getInventory().setBoots(armorContents[0]);
		player.setExp(exp);
		player.setFoodLevel(foodLevel);
		player.setHealth(health);
		
		for (int i = 0; i < inventoryContents.length; i++) {
			player.getInventory().setItem(i, inventoryContents[i]);
		}

		player.setLevel(level);
	}

	public void store(Player player) {
		allowFlight = player.getAllowFlight();
		armorContents = player.getInventory().getArmorContents();
		exp = player.getExp();
		foodLevel = player.getFoodLevel();
		health = player.getHealth();
		inventoryContents = player.getInventory().getContents();
		level = player.getLevel();
		uuid = player.getUniqueId();
	}
	
}
