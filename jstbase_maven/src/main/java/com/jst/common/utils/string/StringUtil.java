package com.jst.common.utils.string;

public class StringUtil {
	
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}
	
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		}
		else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}
	
	
	public static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str.trim())) {
			return true;
		}
		return false;
	}
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}	
	

}
