package ca.weiway.fbgames.client.util;

public class StringUtils {
	public static String getSubtractedGameName(String gameName, int desiredLength) {
		if(gameName.length() > desiredLength) {
			String gameNameSub = gameName.substring(0, desiredLength);
			return gameNameSub.substring(0, gameNameSub.lastIndexOf(" ")) + " ...";
		}
		StringBuffer buffer = new StringBuffer(gameName);
		int length = gameName.length();
		while(length < 10) {
			buffer.append("&nbsp;");
			length++;
		}
		return buffer.toString();
	}
	
	public static String parseHTMLEscapeChars(String input) {
		return input.replace("\'", "&#146;")
					.replace("\"", "&#148;");	
	}
	
	public static Boolean isStringEmpty(String target) {
		return target == null || target.isEmpty();
	}
}
