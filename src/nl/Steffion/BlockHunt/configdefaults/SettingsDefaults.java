package nl.Steffion.BlockHunt.configdefaults;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

/**
 *
 * @author Steffion (Stef de Goey) 2016
 *
 */
public class SettingsDefaults {
	public static ArrayList<Material>	ALLOWED_BLOCKS		= new ArrayList<Material>() {
																private static final long serialVersionUID = -5312436006124695800L;
																
																{
																	add(Material.BOOKSHELF);
																	add(Material.LOG);
																	add(Material.HAY_BLOCK);
																	add(Material.FURNACE);
																}
															};
	public static int					GAMETIME			= 300;
	public static int					LOBBYTIME			= 40;
	public static int					MAXPLAYERS			= 12;
	public static int					MINPLAYERS			= 3;
	public static double				PRECENTAGE_SEEKERS	= 0.2;
	public static int					SEEKERSWAITTIME		= 20;
	
	public static HashMap<String, Object> getValues() {
		SettingsDefaults defaultsClass = new SettingsDefaults();
		Field[] fields = defaultsClass.getClass().getDeclaredFields();
		HashMap<String, Object> defaults = new HashMap<>();

		for (Field f : fields) {
			try {
				defaults.put(f.getName(), f.get(defaultsClass));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return defaults;
	}

}
