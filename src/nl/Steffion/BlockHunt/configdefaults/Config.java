package nl.Steffion.BlockHunt.configdefaults;

import java.util.HashMap;

public enum Config {
	GENERAL_MINPLAYERS(3);

	public static HashMap<String, Object> getValues() {
		HashMap<String, Object> values = new HashMap<String, Object>();

		for (Config content : Config.values()) {
			values.put(content.key, content.value);
		}
		
		return values;
	}

	private String	key;
	private Object	value;

	private Config(Object value) {
		key = name();
		this.value = value;
	}
	
}
