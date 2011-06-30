package ca.weiway.fbgames.client.ui;

import java.util.ArrayList;
import java.util.List;

import ca.weiway.fbgames.client.resource.icons.ExampleIcons;
import ca.weiway.fbgames.client.ui.widget.GameDetailWidget;
import ca.weiway.fbgames.shared.model.Game;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Composite;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.WidgetExpander;
import com.extjs.gxt.ui.client.widget.grid.WidgetRowRenderer;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.jhickman.web.gwt.gxtuibinder.client.GxtEvent;
import com.jhickman.web.gwt.gxtuibinder.client.GxtUiHandler;

public class HomeViewImpl extends Composite implements HomeView {

	private ListStore<BeanModel> gameStore;

	private static HomeViewImplUiBinder uiBinder = GWT
			.create(HomeViewImplUiBinder.class);

	interface HomeViewImplUiBinder extends UiBinder<Component, HomeViewImpl> {
	}

	public static final ExampleIcons INSTANCE = GWT.create(ExampleIcons.class);

	@Inject
	public HomeViewImpl(ListStore<BeanModel> gameStore) {
		this.gameStore = gameStore;

		initComponent(uiBinder.createAndBindUi(this));

		initGrid();
	}

	@UiField
	Button btnImport;

	@UiField
	ContentPanel contentPanel;

	private Grid<BeanModel> grid;

	private Presenter presenter;

	private GridCellRenderer<BeanModel> platformRender = new GridCellRenderer<BeanModel>() {
		public String render(BeanModel model, String property,
				ColumnData config, int rowIndex, int colIndex,
				ListStore<BeanModel> store, Grid<BeanModel> grid) {
			String platform = (String) model.get(property);
			if ("XBOX 360".equals(platform.toUpperCase())) {
				return "<img src='http://spiffy360.com/images/icons/xbox360.png' height='20px' width='20px' alt='XBOX 360'></span>";
			} else {
				return "<img src='http://t2.gstatic.com/images?q=tbn:ANd9GcTa8LNtHhdoghuWRvefTVEKQskgt2OsYRSBYcIwWWvlkt_g2VUw&t=1' height='20px' width='20px' alt='PS3'></span>";
			}
		}
	};

	private GridCellRenderer<BeanModel> buttonRenderer = new GridCellRenderer<BeanModel>() {

		public Object render(final BeanModel model, String property,
				ColumnData config, final int rowIndex, final int colIndex,
				ListStore<BeanModel> store, Grid<BeanModel> grid) {

			LayoutContainer c = new LayoutContainer();  
	        HBoxLayout layout = new HBoxLayout();  
	        layout.setPadding(new Padding(5));  
	        layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);  
	        layout.setPack(BoxLayoutPack.CENTER);  
	        c.setLayout(layout);  
	  
	        HBoxLayoutData layoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

			Button btnDelete = new Button();
			btnDelete.setIcon(INSTANCE.delete2());
			btnDelete.addSelectionListener(new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							Game game = (Game) model.getBean();
							presenter.deleteGame(game.getId());
							// Info.display(game.getName(),
							// "<ul><li>" + game.getPlatform()
							// + "</li></ul>");
						}
					});

			btnDelete.setToolTip("Delete game");

			Button btnEdit = new Button();
			btnEdit.setIcon(INSTANCE.edit());
			btnEdit.addSelectionListener(new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							Game game = (Game) model.getBean();
//							presenter.deleteGame(game.getId());
							 Info.display("Edit " + game.getName(),
							 "<ul><li>" + game.getPlatform()
							 + "</li></ul>");
						}
					});

			btnEdit.setToolTip("Edit game");

			c.add(btnEdit, layoutData);
			c.add(btnDelete, layoutData);

			return c;
		}
	};

	@UiFactory
	public ListStore<BeanModel> provideStore() {
		return gameStore;
	}

	@UiFactory
	public DateTimeFormat provideDateFormat() {
		return DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
	}

	@GxtUiHandler(uiField = "btnImport", eventType = GxtEvent.Select)
	public void onImportButtonClicked(ButtonEvent event) {
		presenter.showImportGameDialog();
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
	public Long getSelectedId() {

		BeanModel model = grid.getSelectionModel().getSelectedItem();

		return ((Game) model.getBean()).getId();
	}

	@Override
	public void setLoading(boolean isloading) {
		// TODO Auto-generated method stub

	}

	private void initGrid() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		// final CheckBoxSelectionModel<BeanModel> selectionModel = new
		// CheckBoxSelectionModel<BeanModel>();
		// selection model supports the SIMPLE selection mode
		// selectionModel.setSelectionMode(SelectionMode.SINGLE);

		// configs.add(selectionModel.getColumn());

		final WidgetExpander<BeanModel> expander = new WidgetExpander<BeanModel>(
				new WidgetRowRenderer<BeanModel>() {
					@Override
					public Widget render(BeanModel model, int rowIdx) {
						// final FlowPanel retval = new FlowPanel();
						Game game = (Game) model.getBean();
						// Image image = new Image(game.getImageLink());
						// retval.add(image);
						return new GameDetailWidget(game);
						// return retval;
					}
				});
		configs.add(expander);

		// XTemplate tpl =
		// XTemplate.create("<p><b>Name:</b> {name} <br/> <b>Price:</b> {prices}</p>");
		//
		// RowExpander expander = new RowExpander();
		// expander.setTemplate(tpl);
		// configs.add(expander);

		ColumnConfig column = new ColumnConfig();
		column.setId("platform");
		column.setHeader("");
		column.setWidth(15);
		column.setRenderer(platformRender);
		column.setMenuDisabled(true);
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("name");
		column.setHeader("Name");
		column.setWidth(200);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("releaseDate");
		column.setHeader("Release Date");
		column.setDateTimeFormat(provideDateFormat());
		column.setWidth(100);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("");
		column.setHeader("");
		column.setWidth(50);
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setRenderer(buttonRenderer);
		column.setSortable(false);
		column.setMenuDisabled(true);
		configs.add(column);

		ColumnModel cm = new ColumnModel(configs);

		grid = new Grid<BeanModel>(gameStore, cm);
		grid.setAutoExpandColumn("name");
		grid.addPlugin(expander);
		grid.setColumnReordering(true);
		grid.setStripeRows(true);
		grid.getView().setAutoFill(true);
		grid.setWidth("99%");
		contentPanel.add(grid);
	}

}
