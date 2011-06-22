package ca.weiway.fbgames.server.parser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		
		Document doc = Jsoup.connect(url).get();
		String title = doc.select("div[id=pdpoverview] h1 span").text();
		
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
		
		Game returnValue = new Game();
		returnValue.setName(title.substring(0, title.indexOf("(")).trim());
		returnValue.setPlatform(title.substring(title.indexOf("(") + 1, title.indexOf(")")).trim());
		returnValue.setCreateDate(new Date());
		returnValue.setImageLink(imageLink);
		returnValue.setOnSale(false);
		returnValue.setRecentPriceDrop(false);
		returnValue.setUpdateDate(new Date());
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
	public List<String> parseGameLinks(String url) throws IOException {
		if(url == null) {
			return null;
		}
		
		List<String> gameLinks = new ArrayList<String>();
		
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("table[class=skublocklist] td[class=infos] h4 a");
		
		for (Element link : links) {
//			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
			String href = link.attr("abs:href");
			gameLinks.add(href);
		}

		
		return gameLinks;
	}

}
