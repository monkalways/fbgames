package ca.weiway.fbgames.client.ui;

import java.util.ArrayList;

import ca.weiway.fbgames.shared.model.Game;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

public interface HomeView extends IsWidget {
	
	void setPresenter(Presenter presenter);
	
	interface Presenter {
		void goTo(Place place);
	}

	void setGames(ArrayList<Game> games);
	
}
