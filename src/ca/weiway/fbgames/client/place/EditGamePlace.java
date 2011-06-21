package ca.weiway.fbgames.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EditGamePlace extends Place {
	
	private String name;
	
	public EditGamePlace() {
	}
	
	public EditGamePlace(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static class Tokenizer implements PlaceTokenizer<EditGamePlace> {

		@Override
		public EditGamePlace getPlace(String token) {
			return new EditGamePlace(token);
		}

		@Override
		public String getToken(EditGamePlace place) {
			return place.getName();
		}
		
	}

}
