package nl.Steffion.BlockHunt.Managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileM {
	/*
	 * Made by @author Steffion, © 2013.
	 */

	public static void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
				// Bukkit.broadcastMessage("Directory copied from " + src
				// + "  to " + dest);
			}
			String files[] = src.list();

			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				if (!srcFile.getName().equals("uid.dat")) {
					copyFolder(srcFile, destFile);
				}
			}

		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			// Bukkit.broadcastMessage("File copied from " + src + " to " +
			// dest);
		}
	}

	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {

			if (file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();

				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}

				if (file.list().length == 0) {
					file.delete();
				}
			}
		} else {
			file.delete();
		}
	}
}
