package nl.Steffion.BlockHunt.configdefaults;

import java.util.ArrayList;
import java.util.HashMap;

public enum ConfigDefaults {
	ALLOWED_BLOCKS("ALLOWED_BLOCKS"), GAMETIME(300), LOBBYTIME(40), MAXPLAYERS(12), MINPLAYERS(3), PRECENTAGE_SEEKERS(
			0.2), SEEKERSWAITTIME(20);

	public static HashMap<String, Object> getValues() {
		HashMap<String, Object> values = new HashMap<String, Object>();

		for (ConfigDefaults content : ConfigDefaults.values()) {
			values.put(content.key, content.value);
		}
		
		return values;
	}
	
	private String	key;
	private Object	value;

	private ConfigDefaults(Object value) {
		key = name();
		
		ArrayList<String> array = new ArrayList<String>();
		
		try {
			switch ((String) value) {
				case "ALLOWED_BLOCKS":
					array.add("BOOKSHELF");
					array.add("LOG");
					array.add("HAY_BLOCK");
					array.add("FURNACE");
					
					this.value = array;
					break;
				default:
					this.value = value;
					break;
			}
		} catch (ClassCastException e) {
			this.value = value;
		}
	}

}
