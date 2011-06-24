package ca.weiway.fbgames.client.mvp;

import ca.weiway.fbgames.client.place.EditGamePlace;
import ca.weiway.fbgames.client.place.GxtBinderPlace;
import ca.weiway.fbgames.client.place.HomePlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers(
		{
			HomePlace.Tokenizer.class, 
			EditGamePlace.Tokenizer.class, 
			GxtBinderPlace.Tokenizer.class
		}
)
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
