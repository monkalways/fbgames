package ca.weiway.fbgames.client.ui;

import java.util.ArrayList;

import ca.weiway.fbgames.client.place.EditGamePlace;
import ca.weiway.fbgames.shared.model.Game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
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
	
	public HomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("addButton")
	void onClick(ClickEvent e) {
        presenter.goTo(new EditGamePlace());
	}

	@Override
	public void setGames(ArrayList<Game> games) {
		gamesTable.removeAllRows();
		int counter = 0;
		for(Game game : games) {
			gamesTable.setWidget(counter, 0, new CheckBox());
			gamesTable.setWidget(counter, 1, new Label(game.getName()));
			gamesTable.setWidget(counter, 2, new Label(game.getPrice() + ""));
			counter ++;
		}
	}
	

}
