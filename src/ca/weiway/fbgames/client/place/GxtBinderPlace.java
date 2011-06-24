package ca.weiway.fbgames.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GxtBinderPlace extends Place {
	
	public static class Tokenizer implements PlaceTokenizer<GxtBinderPlace> {

		@Override
		public GxtBinderPlace getPlace(String token) {
			return null;
		}

		@Override
		public String getToken(GxtBinderPlace place) {
			return null;
		}
		
	}
	
}
