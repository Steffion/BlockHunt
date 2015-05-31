package nl.Steffion.BlockHunt.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

@SuppressWarnings("rawtypes")
// Don't know why it doesn't like raw types with the classes.
public class Reflection {

	public static String getVersion() {
		String name = Bukkit.getServer().getClass().getPackage().getName();
		String version = name.substring(name.lastIndexOf('.') + 1) + ".";
		return version;
	}

	public static Class<?> getNMSClass(String className) {
		String fullName = "net.minecraft.server." + getVersion() + className;
		Class c = null;
		try {
			c = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public static Class<?> getOBCClass(String className) {
		String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
		Class c = null;
		try {
			c = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public static Object getHandle(Object obj) {
		try {
			return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(
					obj, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Field getField(Class<?> c, String name) {
		try {
			Field field = c.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method getMethod(Class<?> c, String name, Class<?>[] args) {
		for (Method m : c.getMethods())
			if ((m.getName().equals(name))
					&& ((args.length == 0) || (ClassListEqual(args,
							m.getParameterTypes())))) {
				m.setAccessible(true);
				return m;
			}
		return null;
	}

	public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length)
			return false;
		for (int i = 0; i < l1.length; i++)
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		return equal;
	}
}