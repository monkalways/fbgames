package ca.weiway.fbgames.server.parser;

import ca.weiway.fbgames.shared.model.ESRBRating;
import ca.weiway.fbgames.shared.model.GameCategory;
import ca.weiway.fbgames.shared.model.Platform;

public abstract class AbstractGameParser implements GameParser {

	public Platform getPlatform(String name) {
		if(name != null) {
			if(name.toLowerCase().contains("xbox 360")) {
				return Platform.XBOX360;
			} else if(name.toLowerCase().contains("playstation 3")
					|| (name.toLowerCase().contains("play") && name.contains("3"))) {
				return Platform.PS3;
			}
		}
		
		return null;
	}

	public ESRBRating getRating(String rating) {
		if(rating != null) {
			if(rating.toLowerCase().contains("10+")) {
				return ESRBRating.EVERYONE_10_PLUS;
			} else if(rating.toLowerCase().contains("everyone")
					|| rating.toLowerCase().equals("e")) {
				return ESRBRating.EVERYONE;
			} else if(rating.toLowerCase().contains("teen")
					|| rating.toLowerCase().equals("t")) {
				return ESRBRating.TEEN;
			} else if(rating.toLowerCase().contains("mature")
					|| rating.toLowerCase().equals("m")) {
				return ESRBRating.MATURE;
			} else if(rating.toLowerCase().contains("not rated") 
					|| rating.toLowerCase().contains("pending")
					|| rating.toLowerCase().equals("rp")) {
				return ESRBRating.RATING_PENDING;
			} else if(rating.toLowerCase().contains("early")) {
				return ESRBRating.EARLY_CHILDHOOD;
			} 
		}
		return null;
	}
	
	public GameCategory getGameCategory(String category) {
		if(category != null && !category.isEmpty()) {
			for(GameCategory gameCategory : GameCategory.values()) {
				if(gameCategory.getName().equals(category)) {
					return gameCategory;
				}
			}
		} 
		
		return null;
	}

}
