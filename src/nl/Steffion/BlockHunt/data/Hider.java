package nl.Steffion.BlockHunt.data;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.BlockHunt;

/**
 * 
 * @author Steffion (Stef de Goey) 2016
 *
 */
public class Hider {
	private Material	block;
	private Location	hideLocation;
	private UUID		player;
	private BlockHunt	plugin;
	private int			solidBlockTimer;

	public Hider(Player player) {
		plugin = BlockHunt.getPlugin();
		
		this.player = player.getUniqueId();
	}

	public Material getBlock() {
		return block;
	}

	public Location getHideLocation() {
		return hideLocation;
	}

	public Player getPlayer() {
		return plugin.getServer().getPlayer(player);
	}
	
	public int getSolidBlockTimer() {
		return solidBlockTimer;
	}
	
	@SuppressWarnings("deprecation")
	public void revealHider() {
		for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
			onlinePlayer.sendBlockChange(hideLocation, Material.AIR, (byte) 0);
			onlinePlayer.showPlayer(getPlayer());
		}
		
		hideLocation.getWorld().playSound(hideLocation, Sound.ENTITY_PLAYER_ATTACK_CRIT, 5, 0);
		solidBlockTimer = 0;
	}
	
	public void setBlock(Material block) {
		this.block = block;
	}
	
	public void setHideLocation(Location hideLocation) {
		this.hideLocation = hideLocation;
	}
	
	public void setPlayer(Player player) {
		this.player = player.getUniqueId();
	}

	public void setSolidBlockTimer(int solidBlockTimer) {
		this.solidBlockTimer = solidBlockTimer;
	}
	
}
