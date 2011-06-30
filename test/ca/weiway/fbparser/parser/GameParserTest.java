package ca.weiway.fbparser.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.weiway.fbgames.server.parser.GameParser;
import ca.weiway.fbgames.server.parser.GameParserJSoupImpl;
import ca.weiway.fbgames.shared.model.Game;

public class GameParserTest {
	
	private GameParser gameParser = new GameParserJSoupImpl();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testParseBestBuyCa() {
		try {
			String url = "http://www.bestbuy.ca/en-CA/product/nfl-head-coach-09-xbox-360/10110092.aspx";
			Game game = gameParser.parse(url);
			assertNotNull("Game should not be null.", game);
			assertEquals("The Bourne Conspiracy (XBOX 360)", game.getName());
			
		} catch(Exception ex) {
			fail("Exception occurred: " + ex);
		}
		
	}

	@Test
	public void testParseAmazonCa() {
		try {
			String url = "http://www.amazon.ca/gp/product/B0024FAZZY";
			Game game = gameParser.parse(url);
			assertNotNull("Game should not be null.", game);
			assertEquals("Overlord 2", game.getName());
			assertEquals("Xbox 360", game.getPlatform());
			assertEquals("Teen", game.getRating());
			
		} catch(Exception ex) {
			fail("Exception occurred: " + ex);
		}
		
	}
	
	@Test
	public void testParseGameLinksBestBuyCa() {
		try {
			String url = "http://www.bestbuy.ca/catalog/category.aspx?lang=en-CA&category=23374&Page=1&PageSize=15";
			List<String> links = gameParser.parseGameLinks(url);
			List<Game> games = new ArrayList<Game>();
			for(String gameLink : links) {
				Game game = gameParser.parse(gameLink);
				games.add(game);
				System.out.println(game.getName());
			}
		} catch(Exception ex) {
			fail("Exception occurred: " + ex);
		}
		
	}
}
