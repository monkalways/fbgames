package ca.weiway.fbgames.client.ui;

import java.util.ArrayList;
import java.util.List;

import ca.weiway.fbgames.client.place.EditGamePlace;
import ca.weiway.fbgames.shared.model.Game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HomeViewImpl extends Composite implements HomeView {

	private static HomeViewImplUiBinder uiBinder = GWT
			.create(HomeViewImplUiBinder.class);

	interface HomeViewImplUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}
	
	private Presenter presenter;
	
	@UiField
	Button addButton;
	
	@UiField
	Button deleteButton;
	
	@UiField
	FlexTable gamesTable;
	
	@UiField
	Image loading;
	
	public HomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("addButton")
	void onAddButtonClicked(ClickEvent e) {
        presenter.goTo(new EditGamePlace());
	}
	
	@UiHandler("deleteButton")
	void onDeleteButtonClicked(ClickEvent e) {
		presenter.deleteGames();
	}

	@Override
	public void setGames(List<Game> games) {
		gamesTable.removeAllRows();
		int counter = 0;
		for(Game game : games) {
			gamesTable.setWidget(counter, 0, new CheckBox());
			gamesTable.setWidget(counter, 1, new Label(game.getName()));
			Image gameImage = new Image(game.getImageLink());
			gameImage.setWidth("200px");
			gamesTable.setWidget(counter, 3, gameImage);
			counter ++;
		}
	}

	@Override
	public List<Integer> getSelectedRows() {
		List<Integer> selectedRows = new ArrayList<Integer>();

		for (int i = 0; i < gamesTable.getRowCount(); ++i) {
			CheckBox checkBox = (CheckBox) gamesTable.getWidget(i, 0);
			if (checkBox.getValue()) {
				selectedRows.add(i);
			}
		}
		
		return selectedRows;
	}

	@Override
	public void setLoading(boolean isloading) {
		loading.setVisible(isloading);
		gamesTable.setVisible(!isloading);
	}

}
