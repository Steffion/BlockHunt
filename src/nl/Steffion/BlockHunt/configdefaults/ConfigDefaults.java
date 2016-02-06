package nl.Steffion.BlockHunt.configdefaults;

import java.util.HashMap;

public enum ConfigDefaults {
	GENERAL_MAXPLAYERS(12), GENERAL_MINPLAYERS(3);

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
		this.value = value;
	}
	
}
