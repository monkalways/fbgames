package ca.weiway.fbgames.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

public interface EditGameView extends IsWidget {
	
	void setPresenter(Presenter presenter);
	String getName();
	Double getPrice();
	
	interface Presenter extends ca.weiway.fbgames.client.activity.Presenter {

		void doSave();
		
	}
}
