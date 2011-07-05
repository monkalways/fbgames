package ca.weiway.fbgames.client.ui;

import java.util.List;

import ca.weiway.fbgames.shared.model.Game;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface HomeView extends IsWidget {
	
	void setPresenter(Presenter presenter);
	
	interface Presenter {
		void goTo(Place place);

		void deleteGame(Long gameId);

		void showImportGameDialog();
	}

	public Long getSelectedId();

	void refreshGrid();
	
}
