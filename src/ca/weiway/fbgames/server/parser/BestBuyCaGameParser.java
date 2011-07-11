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
import ca.weiway.fbgames.shared.model.GameDetail;
import ca.weiway.fbgames.shared.model.Price;
import ca.weiway.fbgames.shared.model.PriceSource;

public class BestBuyCaGameParser extends AbstractGameParser {
	
	public Game parse(String url) throws IOException {
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
		
		String customerRatingStr = doc.select("div[class=customer-rating] div[class=rating-stars] span[class=nbr]").text();
				
		String numCustomerRatingStr = doc.select("div[id=divTab_details] div[class=left] p[class=rating-numbers]").text();
		
		Game returnValue = new Game();
		returnValue.setName(name.substring(0, name.indexOf("(")).trim());
		returnValue.setPlatform(getPlatform(name));
		returnValue.setCreateDate(new Date());
		returnValue.setBestBuyImageLink(imageLink);
		returnValue.setOnSale(false);
		returnValue.setRecentPriceDrop(false);
		returnValue.setUpdateDate(new Date());
		returnValue.setRating(getRating(rating));
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
		gamePrice.setSource(PriceSource.BESTBUY_CA);
		
		GameDetail gameDetail = new GameDetail();
		gameDetail.setGame(returnValue);
		gameDetail.setGameLink(url);
		gameDetail.setSource(PriceSource.BESTBUY_CA);
		if(!customerRatingStr.isEmpty()) {
			double customerRating = Double.parseDouble(customerRatingStr);
			gameDetail.setRating(customerRating);
		}
		if(!numCustomerRatingStr.isEmpty()) {
			int numCustomerRating = Integer.parseInt(numCustomerRatingStr.substring(
				numCustomerRatingStr.indexOf("(") + 1, numCustomerRatingStr.indexOf("rating")).trim());
			gameDetail.setNumRating(numCustomerRating);
		}
		
		returnValue.getPrices().add(gamePrice);
		returnValue.getGameDetails().add(gameDetail);
		
		return returnValue;
	}
	
	public Map<String, String> parseGameLinks(String url) throws IOException {
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
