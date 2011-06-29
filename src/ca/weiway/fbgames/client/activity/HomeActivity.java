package ca.weiway.fbgames.client.activity;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;
import ca.weiway.fbgames.client.ui.HomeView;
import ca.weiway.fbgames.client.ui.popup.IImportGameDialog;
import ca.weiway.fbgames.shared.action.DeleteGamesAction;
import ca.weiway.fbgames.shared.action.DeleteGamesResult;
import ca.weiway.fbgames.shared.action.GetAllGamesAction;
import ca.weiway.fbgames.shared.action.GetAllGamesResult;
import ca.weiway.fbgames.shared.action.ImportGameAction;
import ca.weiway.fbgames.shared.action.ImportGameResult;
import ca.weiway.fbgames.shared.model.Game;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class HomeActivity extends AbstractActivity implements
		HomeView.Presenter, IImportGameDialog.Presenter {

	private HomeView display;
	
	@Inject
	private PlaceController placeController;
	
	@Inject
	private IImportGameDialog importGameDialog;
	
	private DispatchAsync dispatchAsync;
	private ListStore<BeanModel> gameStore;
	private List<Game> games;

	@Inject
	public HomeActivity(final HomeView homeView,
			final DispatchAsync dispatchAsync,
			final ListStore<BeanModel> gameStore) {
		this.display = homeView;
		this.dispatchAsync = dispatchAsync;
		this.gameStore = gameStore;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		display.setPresenter(this);
		display.setLoading(true);
		loadGames();
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
				games = result.getGames();
				gameStore.removeAll();
				for (Game game : games) {
					BeanModelFactory beanModelFactory = BeanModelLookup.get()
							.getFactory(game.getClass());
					gameStore.add(beanModelFactory.createModel(game));
				}
			}
		});
	}

	@Override
	public void deleteGame(Long gameId) {
		List<Long> gameIdsToDelete = new ArrayList<Long>();
		
		gameIdsToDelete.add(gameId);
		
		dispatchAsync.execute(new DeleteGamesAction(gameIdsToDelete), new AsyncCallback<DeleteGamesResult>() {
			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final DeleteGamesResult result) {
				loadGames();
			}
		});
	}

	@Override
	public void showImportGameDialog() {
		importGameDialog.setPresenter(this);
		importGameDialog.show();
	}

	@Override
	public void importGame(String gameLink) {
		dispatchAsync.execute(new ImportGameAction(gameLink), new AsyncCallback<ImportGameResult>() {
			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final ImportGameResult result) {
				loadGames();
				importGameDialog.hide();
			}
		});
		
	}

}
