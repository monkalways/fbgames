package ca.weiway.fbgames.client.ui;

import java.util.List;

import ca.weiway.fbgames.shared.model.Game;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface HomeView extends IsWidget {
	
	void setPresenter(Presenter presenter);
	
	interface Presenter {
		void goTo(Place place);

		void deleteGames();
	}

	void setGames(List<Game> games);

	List<Integer> getSelectedRows();

	void setLoading(boolean isloading);
	
}
