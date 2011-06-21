package ca.weiway.fbgames.client.inject;

import ca.weiway.fbgames.client.mvp.AppActivityMapper;
import ca.weiway.fbgames.client.mvp.AppPlaceHistoryMapper;
import ca.weiway.fbgames.client.place.HomePlace;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class MvpModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(ActivityMapper.class).to(AppActivityMapper.class).in(Singleton.class);
		bind(PlaceHistoryMapper.class).to(AppPlaceHistoryMapper.class).in(Singleton.class);
	}
	
	@Singleton
	@Provides
	public PlaceHistoryHandler providePlaceHistoryHandler(
			PlaceHistoryMapper mapper,
			EventBus eventBus,
			PlaceController placeController) {
		PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(mapper);
		placeHistoryHandler.register(placeController, eventBus, new HomePlace());
		return placeHistoryHandler;
	}
	
	@Singleton
	@Provides
	public PlaceController providePlaceController(EventBus eventBus) {
		PlaceController placeController = new PlaceController(eventBus);
		return placeController;
	}

}
