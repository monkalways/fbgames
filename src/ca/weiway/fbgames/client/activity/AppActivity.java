package ca.weiway.fbgames.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class AppActivity extends AbstractActivity implements Presenter {
	
	public abstract PlaceController getPlaceController();
	
	@Override
	public void goTo(Place place) {
		getPlaceController().goTo(place);
	}

}
