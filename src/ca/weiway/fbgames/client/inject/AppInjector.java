package ca.weiway.fbgames.client.inject;

import ca.weiway.fbgames.client.ui.MainView;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;

@GinModules({AppModule.class})
public interface AppInjector extends Ginjector {
	MainView getMainView();
	PlaceHistoryHandler getPlaceHistoryHandler();
}
