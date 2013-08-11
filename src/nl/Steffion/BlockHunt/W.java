package nl.Steffion.BlockHunt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import nl.Steffion.BlockHunt.Managers.ConfigM;
import nl.Steffion.BlockHunt.Managers.MessageM;
import nl.Steffion.BlockHunt.Serializables.LocationSerializable;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class W {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	public static String pluginName = "BlockHunt";
	public static String pluginVersion = "v0.1.2_ALPHA";
	public static String engineVersion = "v1.1.0";
	public static String pluginAutors = "Steffion";
	public static String pluginMainPermission = pluginName + ".";

	public static ArrayList<String> newFiles = new ArrayList<String>();
	public static HashMap<Player, LocationSerializable> pos1 = new HashMap<Player, LocationSerializable>();
	public static HashMap<Player, LocationSerializable> pos2 = new HashMap<Player, LocationSerializable>();
	public static ConfigM config = new ConfigM("config", "");
	public static ConfigM messages = new ConfigM("messages", "");
	public static ConfigM arenas = new ConfigM("arenas", "");
	public static ConfigM signs = new ConfigM("signs", "");
	public static ArrayList<Arena> arenaList = new ArrayList<Arena>();
	public static Random random = new Random();
	public static DisguiseCraftAPI dcAPI;
	public static HashMap<Player, Integer> seekertime = new HashMap<Player, Integer>();

	public static HashMap<Player, Location> pLocation = new HashMap<Player, Location>();
	public static HashMap<Player, GameMode> pGameMode = new HashMap<Player, GameMode>();
	public static HashMap<Player, ItemStack[]> pInventory = new HashMap<Player, ItemStack[]>();
	public static HashMap<Player, ItemStack[]> pArmor = new HashMap<Player, ItemStack[]>();
	public static HashMap<Player, Float> pEXP = new HashMap<Player, Float>();
	public static HashMap<Player, Integer> pEXPL = new HashMap<Player, Integer>();
	public static HashMap<Player, Double> pHealth = new HashMap<Player, Double>();
	public static HashMap<Player, Integer> pFood = new HashMap<Player, Integer>();
	public static HashMap<Player, ItemStack> pBlock = new HashMap<Player, ItemStack>();

	public static HashMap<Player, Location> moveLoc = new HashMap<Player, Location>();
	public static HashMap<Player, Location> hiddenLoc = new HashMap<Player, Location>();
	public static HashMap<Player, Boolean> hiddenLocWater = new HashMap<Player, Boolean>();

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
