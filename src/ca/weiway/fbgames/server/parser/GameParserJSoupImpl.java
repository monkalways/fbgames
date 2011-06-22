package ca.weiway.fbgames.server.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ca.weiway.fbgames.shared.model.Game;

public class GameParserJSoupImpl implements GameParser {

	@Override
	public Game parse(String url) throws IOException {
		if(url == null) {
			return null;
		}
		
		Document doc = Jsoup.connect(url).get();
		String webId = doc.select("div[id=pdpoverview] ul[class=infos] li[class=first] span").last().text();
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
		
		String imageLink = "http://www.bestbuy.ca" + doc.select("div[id=productdetail] img").attr("src");
		
		Game returnValue = new Game();
		returnValue.setWebId(webId);
		returnValue.setName(title);
		returnValue.setPrice(price);
		returnValue.setLink(url);
		returnValue.setImageLink(imageLink);
		
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
