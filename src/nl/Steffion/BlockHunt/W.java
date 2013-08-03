package nl.Steffion.BlockHunt;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import nl.Steffion.BlockHunt.Managers.ConfigM;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

public class W {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	public static String pluginName = "BlockHunt";
	public static String pluginVersion = "v0.1.0_ALPHA";
	public static String engineVersion = "v1.1.0";
	public static String pluginAutors = "Steffion";
	public static String pluginMainPermission = pluginName + ".";

	public static ArrayList<String> newFiles = new ArrayList<String>();
	public static HashMap<Player, LocationSerializable> pos1 = new HashMap<Player, LocationSerializable>();
	public static HashMap<Player, LocationSerializable> pos2 = new HashMap<Player, LocationSerializable>();
	public static ConfigM config = new ConfigM("config", "");
	public static ConfigM messages = new ConfigM("messages", "");
	public static ConfigM arenas = new ConfigM("arenas", "");
	public static ArrayList<Arena> arenaList = new ArrayList<Arena>();

	public static void newFiles() {
		ConfigM.setDefaults();
		for (String Filename : newFiles) {
			MessageM.sendMessage(null,
					"%WCouldn't find '%A%Filename%.yml%W' creating new one.",
					true, "Filename-" + Filename);
		}
		newFiles.clear();
	}
}
