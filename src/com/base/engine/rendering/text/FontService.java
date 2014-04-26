package com.base.engine.rendering.text;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;

public class FontService {
	
	private static HashMap<String, Font> fonts = new HashMap<String, Font>();
	
	public static Font getFont() throws FontFormatException, IOException {
		return getFont("Times New Roman", java.awt.Font.PLAIN, 20);
	}
	
	public static Font getFont(String name, int style, int size) throws FontFormatException, IOException {
		String key = name + "__" + style + "__" + size;
		if (fonts.containsKey(key))
			return fonts.get(key);
		fonts.put(key, new Font(name, style, size));
		return fonts.get(key);
	}

}
