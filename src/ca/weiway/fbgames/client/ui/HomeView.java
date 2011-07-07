package ca.weiway.fbgames.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface HomeView extends IsWidget {
	
	void setPresenter(Presenter presenter);
	
	interface Presenter {
		void goTo(Place place);

		void deleteGame(Long gameId);

		void showImportGameDialog();

		void showBatchImportDialog();

		void deleteAll();
	}

	public Long getSelectedId();

	void refreshGrid();

}
