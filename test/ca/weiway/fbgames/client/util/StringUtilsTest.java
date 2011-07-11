package ca.weiway.fbgames.client.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void stripPunctionAndSpaceTest() {
		String input = "Enslaved: Odyssey to the West";
		assertEquals("EnslavedOdysseytotheWest", StringUtils.stripPunctionAndSpace(input));
		
		input = "Mass Effect 2";
		assertEquals("MassEffect2", StringUtils.stripPunctionAndSpace(input));
	}
}
