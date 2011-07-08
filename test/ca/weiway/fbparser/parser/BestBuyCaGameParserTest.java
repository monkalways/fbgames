package ca.weiway.fbparser.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.weiway.fbgames.server.parser.BestBuyCaGameParser;
import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.GameDetail;
import ca.weiway.fbgames.shared.model.Price;
import ca.weiway.fbgames.shared.model.PriceSource;

public class BestBuyCaGameParserTest {
	private BestBuyCaGameParser gameParser = new BestBuyCaGameParser();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testParse() {
		try {
			String url = "http://www.bestbuy.ca/en-CA/product/nfl-head-coach-09-xbox-360/10110092.aspx";
			Game game = gameParser.parse(url);
			
			assertNotNull(game);
			assertEquals("NFL Head Coach 09", game.getName());
			
			Price price = game.getPrices().iterator().next();
			assertNotNull(price);
			assertEquals(1.99, price.getPrice(), 0.0001);
			
			assertNotNull(game.getGameDetails());
			assertEquals(1, game.getGameDetails().size());
			
			GameDetail gameDetail = game.getGameDetails().iterator().next();
			assertNotNull(gameDetail);
			assertEquals("http://www.bestbuy.ca/en-CA/product/nfl-head-coach-09-xbox-360/10110092.aspx", gameDetail.getGameLink());
			assertEquals(PriceSource.BESTBUY_CA, gameDetail.getSource());
			assertEquals(2, gameDetail.getNumRating().intValue());
			assertEquals(5.0, gameDetail.getRating().doubleValue(), 0.0001);
			
		} catch(Exception ex) {
			fail("Exception occurred: " + ex);
		}
		
	}
	
	@Test
	public void testParseGameLinks() {
		try {
			String url = "http://www.bestbuy.ca/catalog/category.aspx?lang=en-CA&category=23374&Page=1&PageSize=15";
			Map<String, String> links = gameParser.parseGameLinks(url);
			List<Game> games = new ArrayList<Game>();
			for(String gameLink : links.values()) {
				Game game = gameParser.parse(gameLink);
				games.add(game);
				System.out.println(game.getName());
			}
		} catch(Exception ex) {
			fail("Exception occurred: " + ex);
		}
		
	}
}
