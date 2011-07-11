package ca.weiway.fbgames.client.util;

public class StringUtils {
	public static String getSubtractedGameName(String gameName,
			int desiredLength) {
		if (gameName.length() > desiredLength) {
			String gameNameSub = gameName.substring(0, desiredLength);
			return gameNameSub.substring(0, gameNameSub.lastIndexOf(" "))
					+ " ...";
		}
		StringBuffer buffer = new StringBuffer(gameName);
		int length = gameName.length();
		while (length < 10) {
			buffer.append("&nbsp;");
			length++;
		}
		return buffer.toString();
	}

	public static String parseHTMLEscapeChars(String input) {
		return input.replace("\'", "&#146;").replace("\"", "&#148;");
	}

	public static Boolean isStringEmpty(String input) {
		return input == null || input.isEmpty();
	}

	public static String stripPunctionAndSpace(String input) {
		if (input == null) {
			return null;
		}

		StringBuffer returnValue = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if(isAsciiAlpha(ch) || isAsciiNumeric(ch)) {
				returnValue.append(ch);
			}
		}
		return returnValue.toString();
	}

	/**
	 * <p>
	 * Checks whether the character is ASCII 7 bit numeric.
	 * </p>
	 * 
	 * <pre>
	 *   CharUtils.isAsciiNumeric('a')  = false
	 *   CharUtils.isAsciiNumeric('A')  = false
	 *   CharUtils.isAsciiNumeric('3')  = true
	 *   CharUtils.isAsciiNumeric('-')  = false
	 *   CharUtils.isAsciiNumeric('\n') = false
	 *   CharUtils.isAsciiNumeric('&copy;') = false
	 * </pre>
	 * 
	 * @param ch
	 *            the character to check
	 * @return true if between 48 and 57 inclusive
	 */
	public static boolean isAsciiNumeric(char ch) {
		return ch >= '0' && ch <= '9';
	}

	/**
	 * <p>
	 * Checks whether the character is ASCII 7 bit alphabetic.
	 * </p>
	 * 
	 * <pre>
	 *   CharUtils.isAsciiAlpha('a')  = true
	 *   CharUtils.isAsciiAlpha('A')  = true
	 *   CharUtils.isAsciiAlpha('3')  = false
	 *   CharUtils.isAsciiAlpha('-')  = false
	 *   CharUtils.isAsciiAlpha('\n') = false
	 *   CharUtils.isAsciiAlpha('&copy;') = false
	 * </pre>
	 * 
	 * @param ch
	 *            the character to check
	 * @return true if between 65 and 90 or 97 and 122 inclusive
	 */
	public static boolean isAsciiAlpha(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
	}
}
