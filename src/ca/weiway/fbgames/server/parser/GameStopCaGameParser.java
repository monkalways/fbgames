package ca.weiway.fbgames.server.parser;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.GameDetail;
import ca.weiway.fbgames.shared.model.Price;
import ca.weiway.fbgames.shared.model.PriceSource;

public class GameStopCaGameParser extends AbstractGameParser {

	@Override
	public Game parse(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		
		String gameName = doc.select("div[class=innerpage noads] h1[class=grid_17]").text();
		if(gameName.isEmpty()) {
			return null;
		}
		if(gameName.indexOf("(") > 0) {
			gameName = gameName.substring(0, gameName.indexOf("(")).trim();
		} else if(gameName.indexOf("by") > 0){
			gameName = gameName.substring(0, gameName.indexOf("by")).trim();
		}
		
		String price = doc.select("div[class=buy1] h3 span").text();
		if(price.isEmpty()) {
			return null;
		}
		
		String esrbRating = doc.select("div[class=esrb_box greygrad grid_5] img").attr("alt");
		
		Elements gameInfo = doc.select("div[class=gameinfo nograd grid_15] li");
		
		String platform = gameInfo.get(0).select("span").text();
		String publisher = gameInfo.get(1).select("span").text();
		String developer = gameInfo.get(2).select("span").text();
		String category = null;
		if(gameInfo.size() == 4) {
			category = gameInfo.get(3).select("span").text();
			if(category.lastIndexOf(",") > 0) {
				category = category.substring(category.lastIndexOf(",") + 1).trim();
			}
		}
		
		String imageLink = "http://www.gamestop.ca" + doc.select("div[class=boxart] img").attr("src");
		String customerRating = doc.select("div[id=BVRRQuickTakeSummaryID] span[class=BVRRNumber BVRRRatingNumber rating]").text();
		
		Game returnValue = new Game();
		returnValue.setName(gameName);
		returnValue.setPlatform(getPlatform(platform));
		returnValue.setCreateDate(new Date());
		returnValue.setGameStopImageLink(imageLink);
		returnValue.setOnSale(false);
		returnValue.setRecentPriceDrop(false);
		returnValue.setUpdateDate(new Date());
		returnValue.setRating(getRating(esrbRating));
		returnValue.setPublisher(publisher);
		returnValue.setDeveloper(developer);
		returnValue.setGameCategory(getGameCategory(category));
		
		Price gamePrice = new Price();
		gamePrice.setPrice(Double.parseDouble(price));
		gamePrice.setCreateDate(new Date());
		gamePrice.setGame(returnValue);
		gamePrice.setLink(url);
		gamePrice.setSource(PriceSource.GAMESTOP_CA);
		
		GameDetail gameDetail = new GameDetail();
		gameDetail.setGame(returnValue);
		gameDetail.setGameLink(url);
		gameDetail.setSource(PriceSource.GAMESTOP_CA);
		if(customerRating != null && !customerRating.isEmpty()) {
			gameDetail.setRating(Double.parseDouble(customerRating));
		}
		
		returnValue.getPrices().add(gamePrice);
		returnValue.getGameDetails().add(gameDetail);
		
		return returnValue;
	}

	@Override
	public Map<String, String> parseGameLinks(String url) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
