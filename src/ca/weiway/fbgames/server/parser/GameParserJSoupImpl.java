package ca.weiway.fbgames.server.parser;

import java.io.IOException;
import java.util.Map;

import ca.weiway.fbgames.shared.model.Game;

public class GameParserJSoupImpl implements GameParser {
	
	private BestBuyCaGameParser bestBuyCaGameParser;
	private AmazonCaGameParser amazonCaGameParser;
	private GameStopCaGameParser gameStopCaGameParser;
	
	public GameParserJSoupImpl() {
		bestBuyCaGameParser = new BestBuyCaGameParser();
		amazonCaGameParser = new AmazonCaGameParser();
		gameStopCaGameParser = new GameStopCaGameParser();
	}

	@Override
	public Game parse(String url) throws IOException {
		if(url == null) {
			return null;
		}
		
		if(url.contains("bestbuy.ca")) {
			return bestBuyCaGameParser.parse(url);
		} else if(url.contains("amazon.ca")) {
			return amazonCaGameParser.parse(url);
		} else if(url.contains("gamestop.ca")) {
			return gameStopCaGameParser.parse(url);
		} else {
			return null;
		}
	}

	@Override
	public Map<String, String> parseGameLinks(String url) throws IOException {
		if(url == null) {
			return null;
		}
		
		if(url.contains("bestbuy.ca")) {
			return bestBuyCaGameParser.parseGameLinks(url);
		} else if(url.contains("gamestop.ca")) {
			return gameStopCaGameParser.parseGameLinks(url);
		} else {
			return null;
		}
	}

	
}
