package ca.weiway.fbgames.server.parser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.Price;
import ca.weiway.fbgames.shared.model.PriceSource;

public class GameParserJSoupImpl implements GameParser {

	@Override
	public Game parse(String url) throws IOException {
		if(url == null) {
			return null;
		}
		
		if(url.contains("bestbuy.ca")) {
			return parseBestBuyCaGame(url);
		} else if(url.contains("amazon.ca")) {
			return parseAmazonCaGame(url);
		}
		else {
			return null;
		}
	}

	Game parseAmazonCaGame(String url) throws IOException {
		Game game = new Game();
		Document doc = Jsoup.connect(url).get();
		
		String name = doc.select("h1[class=parseasinTitle] span[id=btAsinTitle]").text();
		game.setName(name);
		
		String platform = doc.select("div[class=bucket] div[class=content] ul li").first().text();
		platform = platform.indexOf("Xbox") > 0 ? "Xbox 360" : "PlayStation 3";
		game.setPlatform(platform);
		
		String rating = doc.select("div[class=bucket] div[class=content] ul li a").get(1).text();
		game.setRating(rating);
		
		String releaseDate = doc.select("td[class=bucket] div[class=content] ul li").get(1).text();
		
		game.setCreateDate(new Date());
		game.setImageLink(null);
		game.setOnSale(false);
		game.setRecentPriceDrop(false);
		game.setUpdateDate(new Date());
		
		return game;
	}

	Game parseBestBuyCaGame(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		String name = doc.select("div[id=pdpoverview] h1 span").text();
		
		Elements priceElements = doc.select("div[id=pdpoverview] div[class=content] span[class=price]");
		Double price = 10000.;
		if(priceElements.size() == 1) {
			price = Double.parseDouble(priceElements.text().trim().substring(1));
		} else if(priceElements.size() > 1) {
			for(Element priceElement : priceElements) {
				Double currentPrice = Double.parseDouble(priceElement.text().trim().substring(1));
				if(currentPrice < price) {
					price = currentPrice;
				}
			}
		}
		
		String releaseDateStr = doc.select("div[id=pdpoverview] ul[class=infos] li span").get(3).text();
		
		String imageLink = "http://www.bestbuy.ca" + doc.select("div[id=productdetail] img").attr("src");
		
		String rating = doc.select("div[class=ProductDetailsControlMiddleRow] div[class=std-ratingsystem] p span").first().text();
		
		Game returnValue = new Game();
		returnValue.setName(name.substring(0, name.indexOf("(")).trim());
		returnValue.setPlatform(name.substring(name.indexOf("(") + 1, name.indexOf(")")).trim());
		returnValue.setCreateDate(new Date());
		returnValue.setImageLink(imageLink);
		returnValue.setOnSale(false);
		returnValue.setRecentPriceDrop(false);
		returnValue.setUpdateDate(new Date());
		returnValue.setRating(rating.substring(rating.indexOf(":") + 1).trim());
		if(releaseDateStr != null && releaseDateStr.length() != 0) {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			try {
				returnValue.setReleaseDate(format.parse(releaseDateStr));
			} catch(Exception ex) {
				
			}
		}
		
		Price gamePrice = new Price();
		gamePrice.setPrice(price);
		gamePrice.setCreateDate(new Date());
		gamePrice.setGame(returnValue);
		gamePrice.setLink(url);
		gamePrice.setSource(PriceSource.BESTBUY);
		
		returnValue.getPrices().add(gamePrice);
		
		return returnValue;
	}

	@Override
	public Map<String, String> parseGameLinks(String url) throws IOException {
		if(url == null) {
			return null;
		}
		
		if(url.contains("bestbuy.ca")) {
			return parseBestBuyGameCaLinks(url);
		} else {
			return null;
		}
	}

	Map<String, String> parseBestBuyGameCaLinks(String url) throws IOException {
		Map<String, String> gameLinks = new HashMap<String, String>();
		
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("table[class=skublocklist] td[class=infos] h4 a");
		
		for (Element link : links) {
//			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
			String href = link.attr("abs:href");
			String name = link.text();
			if(!name.contains("-")) {
				gameLinks.put(name, href);
			}
		}
		
		return gameLinks;
	}
	
}
