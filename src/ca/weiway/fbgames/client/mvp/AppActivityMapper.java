package ca.weiway.fbgames.client.mvp;

import ca.weiway.fbgames.client.activity.EditGameActivity;
//import ca.weiway.fbgames.client.activity.HomeActivity;
import ca.weiway.fbgames.client.activity.HomeActivity;
import ca.weiway.fbgames.client.place.EditGamePlace;
import ca.weiway.fbgames.client.place.HomePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AppActivityMapper implements ActivityMapper {
	
	private Provider<HomeActivity> homeActivityProvider;
	private Provider<EditGameActivity> editGameActivityProvider;
	
	@Inject
	public AppActivityMapper(
			Provider<HomeActivity> homeActivityProvider,
			Provider<EditGameActivity> editGameActivityProvider
			) {
		this.homeActivityProvider = homeActivityProvider;
		this.editGameActivityProvider = editGameActivityProvider;
	}

	@Override
	public Activity getActivity(Place place) {
		Activity activity = null;
		if(place instanceof HomePlace) {
			activity = homeActivityProvider.get();
		} 
		else if(place instanceof EditGamePlace) {
			activity = editGameActivityProvider.get().withPlace((EditGamePlace) place);
		}
		return activity;
	}

}
