package ca.weiway.fbgames.client;

import ca.weiway.fbgames.client.inject.AppInjector;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fbgames implements EntryPoint {

	@Override
	public void onModuleLoad() {
//		ThemeManager.register(Theme.GRAY);
		Theme.GRAY.set("file","resources/css/gxt-gray.css");
		GXT.setDefaultTheme(Theme.GRAY, true);
		
		AppInjector injector = GWT.create(AppInjector.class);
		RootPanel.get().add(injector.getMainView());
		injector.getPlaceHistoryHandler().handleCurrentHistory();
	}
	
}
