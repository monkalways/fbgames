package ca.weiway.fbgames.client.ui;

import java.util.Date;

import com.google.gwt.user.client.ui.IsWidget;

public interface EditGameView extends IsWidget {
	
	void setPresenter(Presenter presenter);
	String getName();
	Double getPrice();
	Date getReleaseDate();
	
	interface Presenter extends ca.weiway.fbgames.client.activity.Presenter {
		void doSave();
	}

	String getImageLink();
	
	void setHeading(String heading);
}
