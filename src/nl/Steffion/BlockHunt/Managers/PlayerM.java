package nl.Steffion.BlockHunt.Managers;

import nl.Steffion.BlockHunt.W;

import org.bukkit.entity.Player;

public class PlayerM {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	public static String main = W.pluginMainPermission;

	public enum PType {
		ALL, PLAYER, MODERATOR, ADMIN, OP;
	}

	public enum PermsC {
		info (main + "info", PType.ALL),
		help (main + "help", PType.ALL),
		reload (main + "reload", PType.MODERATOR),
		create (main + "create", PType.ADMIN),
		set (main + "set", PType.MODERATOR),
		join (main + "join", PType.PLAYER);

		public String perm;
		public PType type;

		private PermsC (String perm, PType type) {
			this.perm = perm;
			this.type = type;
		}
	}

	/**
	 * Check if the player has a permission.
	 * 
	 * @param player
	 * @param perm
	 * @param message
	 * @return
	 */
	public static boolean hasPerm(Player player, PermsC perm, Boolean message) {
		PType type = perm.type;
		if (player == null) {
			return true;
		}

		if (type == PType.ALL) {
			return true;
		} else if (type == PType.OP) {
			if (player.isOp()) {
				return true;
			}
		} else if (type == PType.ADMIN) {
			if (player.hasPermission(main + "admin")) {
				return true;
			}
		} else if (type == PType.MODERATOR) {
			if (player.hasPermission(main + "moderator")) {
				return true;
			}
		} else if (type == PType.PLAYER) {
			if (player.hasPermission(main + "player")) {
				return true;
			}
		}

		if (player.hasPermission("*")) {
			return true;
		} else if (player.hasPermission(main + "*")) {
			return true;
		} else if (player.hasPermission(main + perm)) {
			return true;
		} else if (player.hasPermission(main + perm + "*")) {
			return true;
		} else {
			if (message) {
				MessageM.sendFMessage(player, ConfigC.error_noPermission, true);
			}
		}
		return false;
	}

	/**
	 * Check if the player has a permission.
	 * 
	 * @param player
	 * @param perm
	 * @param type
	 * @param message
	 * @return
	 */
	public static boolean hasPermRaw(Player player, String perm, PType type,
			Boolean message) {
		if (player == null) {
			return true;
		}

		if (type == PType.ALL) {
			return true;
		} else if (type == PType.OP) {
			if (player.isOp()) {
				return true;
			}
		} else if (type == PType.ADMIN) {
			if (player.hasPermission(main + "admin")) {
				return true;
			}
		} else if (type == PType.MODERATOR) {
			if (player.hasPermission(main + "moderator")) {
				return true;
			}
		} else if (type == PType.PLAYER) {
			if (player.hasPermission(main + "player")) {
				return true;
			}
		}

		if (player.hasPermission("*")) {
			return true;
		} else if (player.hasPermission(main + "*")) {
			return true;
		} else if (player.hasPermission(main + perm)) {
			return true;
		} else if (player.hasPermission(main + perm + "*")) {
			return true;
		} else {
			if (message) {
				MessageM.sendFMessage(player, ConfigC.error_noPermission, true);
			}
		}
		return false;
	}
}
