package nl.Steffion.BlockHunt.Serializables;

import java.util.HashMap;
import java.util.Map;

import nl.Steffion.BlockHunt.Managers.MessageM;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("BlockHuntLocation")
public class LocationSerializable extends Location implements
		ConfigurationSerializable {
	public LocationSerializable (World world, double x, double y, double z,
			float yaw, float pitch) {
		super(world, x, y, z, yaw, pitch);
	}

	public LocationSerializable (Location loc) {
		super(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(),
				loc.getPitch());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof LocationSerializable || o instanceof Location) {
			Location loc = (Location) o;
			return loc.getWorld().getName().equals(getWorld().getName())
					&& loc.getX() == getX() && loc.getY() == getY()
					&& loc.getZ() == getZ() && loc.getYaw() == getYaw()
					&& loc.getPitch() == getPitch();
		}
		return false;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("w", getWorld().getName());
		map.put("x", getX());
		map.put("y", getY());
		map.put("z", getZ());
		if (getYaw() != 0D)
			map.put("a", getYaw());
		if (getPitch() != 0D)
			map.put("p", getPitch());
		return map;
	}

	public static LocationSerializable deserialize(Map<String, Object> map) {
		World w = Bukkit.getWorld((String) M.g(map, "w", ""));
		if (w == null) {
			MessageM.sendMessage(
					null,
					"%EError deserializing LocationSerializable - world not found! (%A%w%%E)",
					"w-" + w);
			return null;
		}
		return new LocationSerializable(w, (Double) M.g(map, "x", 0D),
				(Double) M.g(map, "y", 0D), (Double) M.g(map, "z", 0D),
				((Double) M.g(map, "a", 0D)).floatValue(), ((Double) M.g(map,
						"p", 0D)).floatValue());
	}
}
