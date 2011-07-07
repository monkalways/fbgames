package ca.weiway.fbgames.client.activity;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;
import ca.weiway.fbgames.client.ui.HomeView;
import ca.weiway.fbgames.client.ui.popup.IBatchImportDialog;
import ca.weiway.fbgames.client.ui.popup.IImportGameDialog;
import ca.weiway.fbgames.shared.action.DeleteAllGamesAction;
import ca.weiway.fbgames.shared.action.DeleteAllGamesResult;
import ca.weiway.fbgames.shared.action.DeleteGamesAction;
import ca.weiway.fbgames.shared.action.DeleteGamesResult;
import ca.weiway.fbgames.shared.action.ImportGameAction;
import ca.weiway.fbgames.shared.action.ImportGameResult;
import ca.weiway.fbgames.shared.action.ParseGamesAction;
import ca.weiway.fbgames.shared.action.ParseGamesResult;

import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class HomeActivity extends AbstractActivity implements
		HomeView.Presenter, IImportGameDialog.Presenter, IBatchImportDialog.Presenter {

	private HomeView display;
	
	@Inject
	private PlaceController placeController;
	
	@Inject
	private IImportGameDialog importGameDialog;
	
	@Inject
	private IBatchImportDialog batchImportDialog;
	
	private DispatchAsync dispatchAsync;

	@Inject
	public HomeActivity(final HomeView homeView,
			final DispatchAsync dispatchAsync) {
		this.display = homeView;
		this.dispatchAsync = dispatchAsync;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		display.setPresenter(this);
		display.refreshGrid();
		panel.setWidget(display.asWidget());
//		display.refreshGrid();
	}

	@Override
	public void goTo(Place place) {
		placeController.goTo(place);
	}

	@Override
	public void deleteGame(final Long gameId) {
		MessageBox.confirm("Delete confirmation", 
				"Are you sure you want to delete this game?", new Listener<MessageBoxEvent>() {
					
					@Override
					public void handleEvent(MessageBoxEvent be) {
						if("Yes".equals(be.getButtonClicked().getText())) {
							final MessageBox box = MessageBox.wait("Progress",  
						            "Deleting game, please wait...", "Deleting...");
							doDelete(gameId, box);
						}
						
					}
				});
		
	}
	
	private void doDelete(Long gameId, final MessageBox box) {
		List<Long> gameIdsToDelete = new ArrayList<Long>();
		
		gameIdsToDelete.add(gameId);
		
		dispatchAsync.execute(new DeleteGamesAction(gameIdsToDelete), new AsyncCallback<DeleteGamesResult>() {
			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final DeleteGamesResult result) {
				box.close();
				display.refreshGrid();
			}
		});
	}

	@Override
	public void showImportGameDialog() {
		importGameDialog.setPresenter(this);
		importGameDialog.show();
	}

	@Override
	public void importGame(final String gameLink) {
		final MessageBox box = MessageBox.wait("Progress",  
	            "Importing game, please wait...", "Importing...");
		doImport(gameLink, box);
	}
	
	private void doImport(String gameLink, final MessageBox box) {
		dispatchAsync.execute(new ImportGameAction(gameLink), new AsyncCallback<ImportGameResult>() {
			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final ImportGameResult result) {
				box.close();
				importGameDialog.hide();
				display.refreshGrid();
			}
		});
	}

	@Override
	public void showBatchImportDialog() {
		batchImportDialog.setPresenter(this);
		batchImportDialog.init();
		batchImportDialog.show();
	}

	@Override
	public void parseGames(final String batchImportLink) {
		batchImportDialog.setBusy(true);
		dispatchAsync.execute(new ParseGamesAction(batchImportLink), new AsyncCallback<ParseGamesResult>() {
			@Override
			public void onFailure(final Throwable caught) {
				batchImportDialog.setBusy(false);
			}

			@Override
			public void onSuccess(final ParseGamesResult result) {
				batchImportDialog.setBusy(false);
				if(result.isSuccess()) {
					batchImportDialog.setParsingResult(result.getGames());
				} else {
					Info.display("Failed to parse batch link", batchImportLink);
				}
			}
		});
	}

	@Override
	public void importBatchGame(String gameLink) {
		dispatchAsync.execute(new ImportGameAction(gameLink), new AsyncCallback<ImportGameResult>() {
			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final ImportGameResult result) {
				batchImportDialog.importNext();
			}
		});
	}

	@Override
	public void importComplete() {
		batchImportDialog.hide();
		display.refreshGrid();
	}
	
	@Override
	public void importCancel() {
		batchImportDialog.hide();
	}

	@Override
	public void deleteAll() {
		MessageBox.confirm("Delete all confirmation", 
				"Are you sure you want to delete all games?", new Listener<MessageBoxEvent>() {
					
					@Override
					public void handleEvent(MessageBoxEvent be) {
						if("Yes".equals(be.getButtonClicked().getText())) {
							final MessageBox box = MessageBox.wait("Progress",  
						            "Deleting all games, please wait...", "Deleting...");
							doDeleteAll(box);
						}
						
					}
				});
	}
	
	private void doDeleteAll(final MessageBox box) {
		dispatchAsync.execute(new DeleteAllGamesAction(), new AsyncCallback<DeleteAllGamesResult>() {
			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final DeleteAllGamesResult result) {
				box.close();
				Info.display("Delete all completed", result.getDeleteRecords() + " games deleted.");
				display.refreshGrid();
			}
		});
	}

}
