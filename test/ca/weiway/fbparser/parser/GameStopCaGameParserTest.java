package ca.weiway.fbparser.parser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.weiway.fbgames.server.parser.GameStopCaGameParser;
import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.GameCategory;
import ca.weiway.fbgames.shared.model.GameDetail;
import ca.weiway.fbgames.shared.model.Platform;
import ca.weiway.fbgames.shared.model.Price;
import ca.weiway.fbgames.shared.model.PriceSource;

public class GameStopCaGameParserTest {

	private GameStopCaGameParser gameParser = new GameStopCaGameParser();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParseMassEffect2() {
		try {
			String url = "http://www.gamestop.ca/xbox-360/games/mass-effect-2/253449";
			Game game = gameParser.parse(url);

			assertNotNull(game);
			assertEquals("Mass Effect 2", game.getName());
			assertEquals("Electronic Arts", game.getPublisher());
			assertEquals("Electronic Arts", game.getDeveloper());
			assertEquals(Platform.XBOX360, game.getPlatform());
			assertEquals(GameCategory.ROLE_PLAYING, game.getGameCategory());

			Price price = game.getPrices().iterator().next();
			assertNotNull(price);
			assertEquals(19.99, price.getPrice(), 0.0001);
			assertEquals(PriceSource.GAMESTOP_CA, price.getSource());

			assertNotNull(game.getGameDetails());
			assertEquals(1, game.getGameDetails().size());

			GameDetail gameDetail = game.getGameDetails().iterator().next();
			assertNotNull(gameDetail);
			assertEquals(
					"http://www.gamestop.ca/xbox-360/games/mass-effect-2/253449",
					gameDetail.getGameLink());
			assertEquals(PriceSource.GAMESTOP_CA, gameDetail.getSource());
			assertEquals(8.5, gameDetail.getRating().doubleValue(), 0.0001);

		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception occurred: " + ex);
		}

	}

	@Test
	public void testParseModernWarfare2() {
		try {
			String url = "http://www.gamestop.ca/ps3/call-of-duty-modern-warfare-2-web-exclusive-price-for-pre-owned-version/204351";
			Game game = gameParser.parse(url);

			assertNotNull(game);
			assertEquals("Call of Duty: Modern Warfare 2", game.getName());
			assertEquals("Activision", game.getPublisher());
			assertEquals("Infinity Ward", game.getDeveloper());
			assertEquals(Platform.PS3, game.getPlatform());
			assertNull(game.getGameCategory());

			Price price = game.getPrices().iterator().next();
			assertNotNull(price);
			assertEquals(24.99, price.getPrice(), 0.0001);
			assertEquals(PriceSource.GAMESTOP_CA, price.getSource());

			assertNotNull(game.getGameDetails());
			assertEquals(1, game.getGameDetails().size());

			GameDetail gameDetail = game.getGameDetails().iterator().next();
			assertNotNull(gameDetail);
			assertEquals(
					"http://www.gamestop.ca/ps3/call-of-duty-modern-warfare-2-web-exclusive-price-for-pre-owned-version/204351",
					gameDetail.getGameLink());
			assertEquals(PriceSource.GAMESTOP_CA, gameDetail.getSource());
			assertEquals(8.8, gameDetail.getRating().doubleValue(), 0.0001);

		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception occurred: " + ex);
		}

	}

}
