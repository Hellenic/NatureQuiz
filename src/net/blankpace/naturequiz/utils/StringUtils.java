package net.blankpace.naturequiz.utils;

import java.util.Locale;

public class StringUtils {
	
	public static boolean isEmpty(String value)
	{
		if (value == null || value.isEmpty())
			return true;
		
		return false;
	}

	public static String capitalize(String name)
	{
		if (isEmpty(name))
			return name;
		
		String firstLetter = name.substring(0, 1).toUpperCase(Locale.getDefault());
		String theRest = name.substring(1);
		
		return firstLetter + theRest;
	}
}
