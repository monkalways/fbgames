package ca.weiway.fbgames.client.activity;

import net.customware.gwt.dispatch.client.DispatchAsync;
import ca.weiway.fbgames.client.ui.HomeView;
import ca.weiway.fbgames.shared.action.GetAllGamesAction;
import ca.weiway.fbgames.shared.action.GetAllGamesResult;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class HomeActivity extends AbstractActivity implements
		HomeView.Presenter {

	private HomeView display;
	private PlaceController placeController;
	private DispatchAsync dispatchAsync;

	@Inject
	public HomeActivity(final HomeView homeView,
			final PlaceController placeController,
			final DispatchAsync dispatchAsync) {
		this.display = homeView;
		this.placeController = placeController;
		this.dispatchAsync = dispatchAsync;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		loadGames();
		display.setPresenter(this);
		panel.setWidget(display.asWidget());
	}

	@Override
	public void goTo(Place place) {
		placeController.goTo(place);
	}
	
	private void loadGames() {
		dispatchAsync.execute(new GetAllGamesAction(), new AsyncCallback<GetAllGamesResult>() {

			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final GetAllGamesResult result) {
				display.setGames(result.getGames());
//				eventBus.fireEvent(new RateFetchedEvent(result.getRate()));
			}

		});
	}

}
