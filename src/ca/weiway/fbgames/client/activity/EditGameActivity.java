package ca.weiway.fbgames.client.activity;

import net.customware.gwt.dispatch.client.DispatchAsync;
import ca.weiway.fbgames.client.place.EditGamePlace;
import ca.weiway.fbgames.client.place.HomePlace;
import ca.weiway.fbgames.client.ui.EditGameView;
import ca.weiway.fbgames.shared.action.GetAllGamesResult;
import ca.weiway.fbgames.shared.action.SaveGameAction;
import ca.weiway.fbgames.shared.action.SaveGameResult;
import ca.weiway.fbgames.shared.model.Game;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class EditGameActivity extends AppActivity implements EditGameView.Presenter {
	
	private EditGameView display;
	private PlaceController placeController;
	private DispatchAsync dispatchAsync;
	private String gameName;
	
	@Inject
	public EditGameActivity(EditGameView display, PlaceController placeController,
			DispatchAsync dispatchAsync) {
		this.display = display;
		this.placeController = placeController;
		this.dispatchAsync = dispatchAsync;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		display.setPresenter(this);
		if(gameName != null) {
			
		}
		panel.setWidget(display.asWidget());
	}
	
	public EditGameActivity withPlace(EditGamePlace place) {
		this.gameName = place.getName();
		return this;
	}

	@Override
	public PlaceController getPlaceController() {
		return this.placeController;
	}

	@Override
	public void doSave() {
		Game game = new Game();
		game.setName(display.getName());
		game.setPrice(display.getPrice());
		game.setImageLink(display.getImageLink());
		
		dispatchAsync.execute(new SaveGameAction(game), new AsyncCallback<SaveGameResult>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
			}

			@Override
			public void onSuccess(SaveGameResult result) {
				if(result.isSuccess()) {
					goTo(new HomePlace());
				}
				
			}
			
		});
	}

}
