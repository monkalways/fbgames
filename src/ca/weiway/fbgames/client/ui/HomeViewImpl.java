package ca.weiway.fbgames.client.ui;

import java.util.List;

import ca.weiway.fbgames.client.place.EditGamePlace;
import ca.weiway.fbgames.shared.model.Game;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Composite;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.table.NumberCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.inject.Inject;
import com.jhickman.web.gwt.gxtuibinder.client.GxtEvent;
import com.jhickman.web.gwt.gxtuibinder.client.GxtUiHandler;

public class HomeViewImpl extends Composite implements HomeView {
	
	private ListStore<BeanModel> gameStore; 

	private static HomeViewImplUiBinder uiBinder = GWT
			.create(HomeViewImplUiBinder.class);

	interface HomeViewImplUiBinder extends UiBinder<Component, HomeViewImpl> {
	}

	@Inject
	public HomeViewImpl(ListStore<BeanModel> gameStore) {
		this.gameStore = gameStore;
		
		initComponent(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	Button btnAdd;
	
	@UiField
	Grid<BeanModel> grid;

	private static final NumberFormat CURRENCY_NUMBERFORMAT = NumberFormat
			.getCurrencyFormat();
	private static final NumberFormat NUMBER_FORMAT = NumberFormat
			.getFormat("0.00");
	private static final NumberCellRenderer<Grid<BeanModel>> NUMBER_RENDERER = new NumberCellRenderer<Grid<BeanModel>>(
			CURRENCY_NUMBERFORMAT);
	
	private Presenter presenter;

	@UiFactory
	public ListStore<BeanModel> provideStore() {
//		ListStore<ModelData> store = new ListStore<ModelData>();
//
//	    ModelData model = new BaseModel();
//	    model.set("name", "Justin");
//	    model.set("releaseDate", new Date());
//	    store.add(model);
//	    
//	    return store;
		return gameStore;
	}

	@UiFactory
	public DateTimeFormat provideDateFormat() {
		return DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
	}

	@GxtUiHandler(uiField = "btnAdd", eventType = GxtEvent.Select)
	public void onAddButtonClicked(ButtonEvent event) {
		presenter.goTo(new EditGamePlace());
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setGames(List<Game> games) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Integer> getSelectedRows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLoading(boolean isloading) {
		// TODO Auto-generated method stub
		
	}

//	public static final class ChangeCellRenderer implements
//			GridCellRenderer<BeanModel> {
//		public String render(Stock model, String property, ColumnData config,
//				int rowIndex, int colIndex, ListStore<Stock> store,
//				Grid<Stock> grid) {
//			double val = (Double) model.get(property);
//			String style = val < 0 ? "red" : GXT.isHighContrastMode ? "#00ff5a"
//					: "green";
//			String v = NUMBER_FORMAT.format(val);
//
//			return "<span qtitle='"
//					+ grid.getColumnModel().getColumnById(property).getHeader()
//					+ "' qtip='" + v + "' style='font-weight: bold;color:"
//					+ style + "'>" + v + "</span>";
//		}
//	}
//
//	public static final class GridNumberRenderer implements
//			GridCellRenderer<Stock> {
//		public String render(Stock model, String property, ColumnData config,
//				int rowIndex, int colIndex, ListStore<Stock> store,
//				Grid<Stock> grid) {
//			return NUMBER_RENDERER.render(null, property, model.get(property));
//		}
//	}

}
