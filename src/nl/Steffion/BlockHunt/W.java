package nl.Steffion.BlockHunt;

import java.util.ArrayList;

import nl.Steffion.BlockHunt.Managers.ConfigM;
import nl.Steffion.BlockHunt.Managers.MessageM;

public class W {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	public static String pluginName = "BlockHunt";
	public static String pluginVersion = "v1.0.0";
	public static String engineVersion = "v1.1.0";
	public static String pluginAutors = "Steffion";
	public static String pluginMainPermission = pluginName + ".";

	public static ArrayList<String> newFiles = new ArrayList<String>();
	public static ArrayList<String> previewWorlds = new ArrayList<String>();
	public static ConfigM config = new ConfigM("config", "");
	public static ConfigM messages = new ConfigM("messages", "");
	public static ConfigM note1 = new ConfigM("PLACE WORLD FOLDERS HERE!",
			"defaultArenas/");

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
