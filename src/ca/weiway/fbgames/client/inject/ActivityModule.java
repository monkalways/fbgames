package ca.weiway.fbgames.client.inject;

import ca.weiway.fbgames.client.activity.HomeActivity;

import com.google.gwt.inject.client.AbstractGinModule;

public class ActivityModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(HomeActivity.class);
	}

}
