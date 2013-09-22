package nl.Steffion.BlockHunt;

public class PermissionsC {
	/**
	 * Steffion's Engine - Made by Steffion.
	 * 
	 * You're allowed to use this engine for own usage, you're not allowed to
	 * republish the engine. Using this for your own plugin is allowed when a
	 * credit is placed somewhere in the plugin.
	 * 
	 * Thanks for your cooperate!
	 * 
	 * @author Steffion
	 */

	public static String main = BlockHunt.pdfFile.getName().toLowerCase() + ".";

	public enum PType {
		ALL, PLAYER, MODERATOR, ADMIN, OP;
	}

	public enum Permissions {
		info (main + "info", PType.ALL),
		help (main + "help", PType.ALL),
		reload (main + "reload", PType.ADMIN),
		join (main + "join", PType.PLAYER),
		joinfull (main + "joinfull", PType.MODERATOR),
		joinsign (main + "joinsign", PType.PLAYER),
		leave (main + "leave", PType.PLAYER),
		list (main + "list", PType.PLAYER),
		shop (main + "shop", PType.PLAYER),
		start (main + "start", PType.MODERATOR),
		create (main + "create", PType.ADMIN),
		set (main + "set", PType.MODERATOR),
		setwarp (main + "setwarp", PType.MODERATOR),
		signcreate (main + "signcreate", PType.MODERATOR),
		remove (main + "remove", PType.ADMIN),
		tokens (main + "tokens", PType.ADMIN);

		public String perm;
		public PType type;

		private Permissions (String perm, PType type) {
			this.perm = perm;
			this.type = type;
		}
	}
}
