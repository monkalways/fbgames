package ca.weiway.fbgames.client.inject;

import ca.weiway.fbgames.client.ui.EditGameView;
import ca.weiway.fbgames.client.ui.EditGameViewImpl;
import ca.weiway.fbgames.client.ui.HomeView;
import ca.weiway.fbgames.client.ui.HomeViewImpl;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class ViewModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(HomeView.class).to(HomeViewImpl.class).in(Singleton.class);
		bind(EditGameView.class).to(EditGameViewImpl.class).in(Singleton.class);
	}

}
