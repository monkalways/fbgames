package ca.weiway.fbgames.server.parser;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.Platform;

public class AmazonCaGameParser extends AbstractGameParser {

	@Override
	public Game parse(String url) throws IOException {
		Game game = new Game();
		Document doc = Jsoup.connect(url).get();
		
		String name = doc.select("h1[class=parseasinTitle] span[id=btAsinTitle]").text();
		game.setName(name);
		
		String platformStr = doc.select("div[class=bucket] div[class=content] ul li").first().text();
		Platform platform = platformStr.indexOf("Xbox") > 0 ? Platform.XBOX360 : Platform.PS3;
		game.setPlatform(platform);
		
		String rating = doc.select("div[class=bucket] div[class=content] ul li a").get(1).text();
		game.setRating(getRating(rating));
		
		String releaseDate = doc.select("td[class=bucket] div[class=content] ul li").get(1).text();
		
		game.setCreateDate(new Date());
		game.setOnSale(false);
		game.setRecentPriceDrop(false);
		game.setUpdateDate(new Date());
		
		return game;
	}

	@Override
	public Map<String, String> parseGameLinks(String url) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	 
}
