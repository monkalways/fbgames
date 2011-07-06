package ca.weiway.fbgames.client.ui.widget;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;
import ca.weiway.fbgames.client.resource.Resources;
import ca.weiway.fbgames.client.util.StringUtils;
import ca.weiway.fbgames.shared.action.GetGamePricesAction;
import ca.weiway.fbgames.shared.action.GetGamePricesResult;
import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.Price;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

public class GameDetailWidget extends LayoutContainer {
	
	private Grid<BeanModel> grid;
	
	private ListStore<BeanModel> store = new ListStore<BeanModel>();
	
	private Margins margins = new Margins(5);
	
	private Game game;
	
	public GameDetailWidget(Game game) {
		this.game = game;
	}
	
	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		
		this.setSize(760, 200);
		HBoxLayout layout = new HBoxLayout();  
        layout.setPadding(new Padding(5));  
        layout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
        layout.setPack(BoxLayoutPack.CENTER); 
		this.setLayout(layout);  
		
		HBoxLayoutData layoutData = new HBoxLayoutData(new Margins(-80, 120, 0, 0));
		LayoutContainer viewPort = new LayoutContainer();
		viewPort.setWidth(600);
		viewPort.setHeight(200);
		viewPort.setLayout(new CenterLayout());
		
		viewPort.add(new Html("<div class=\"loading\"><div class=\"loading-indicator\"><img src=\"resources/images/default/shared/large-loading.gif\" width=\"32\" height=\"32\" />" + 
				StringUtils.getSubtractedGameName(game.getName(), 20) + "<br /> <span class=\"loading-msg\">Loading...</span></div></div>"));
		this.add(viewPort, layoutData);
		
		DispatchAsync dispatchAsync = new StandardDispatchAsync(new DefaultExceptionHandler());
		dispatchAsync.execute(new GetGamePricesAction(game.getId()), new AsyncCallback<GetGamePricesResult>() {
			@Override
			public void onFailure(final Throwable caught) {
			}

			@Override
			public void onSuccess(final GetGamePricesResult result) {
				List<Price> prices = result.getPrices();
				if(prices != null && !prices.isEmpty()) {
					provideStore(prices);
					GameDetailWidget.this.removeAll();
					GameDetailWidget.this.setLayout(new RowLayout(Orientation.HORIZONTAL));  
					GameDetailWidget.this.setSize(760, 200);
					addGameImage(game.getImageLink());
					addGrid();
					GameDetailWidget.this.unmask();
					GameDetailWidget.this.layout();
				}
			}
		});
	}
	
	private void addGameImage(String imageHref) {
		LayoutContainer container = new LayoutContainer(new FlowLayout());
		Image image = new Image(imageHref);
		image.setHeight("185px");
		image.setWidth("185px");
		container.add(image);
		GameDetailWidget.this.add(container, new RowData(0.25, 0.85, margins));
	}
	
	private void addGrid() {
		ContentPanel cp = new ContentPanel();
		cp.setBodyBorder(true);
		cp.setIcon(Resources.ICONS.table());  
		cp.setHeading("Prices");  
		cp.setButtonAlign(HorizontalAlignment.CENTER);  
		cp.setLayout(new FillLayout(Orientation.HORIZONTAL));  
		cp.getHeader().setIconAltText("Grid Icon");  
		
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		ColumnConfig column = new ColumnConfig();  
	    column.setId("price");  
	    column.setHeader("Price");  
	    column.setWidth(150);  
	    configs.add(column); 
	    
	    column = new ColumnConfig();  
	    column.setId("source");  
	    column.setHeader("Source");  
	    column.setWidth(150);  
	    configs.add(column); 

	    column = new ColumnConfig();  
	    column.setId("createDate");  
	    column.setHeader("Date recorded");  
	    column.setWidth(150); 
	    column.setDateTimeFormat(DateTimeFormat.getFormat("MM/dd/yyyy")); 
	    configs.add(column); 
	    
	    ColumnModel cm = new ColumnModel(configs);  
	    
		grid = new Grid<BeanModel>(store, cm);  
		grid.setStyleAttribute("borderTop", "none");  
	    grid.setAutoExpandColumn("price");
	    grid.setBorders(false);  
	    grid.setStripeRows(true);  
	    grid.setColumnLines(true);  
	    grid.setColumnReordering(true); 
	    grid.getView().setAutoFill(true);
	    
	    cp.add(grid);
	    GameDetailWidget.this.add(cp, new RowData(0.75, 0.85, margins));
	}
	
	public void provideStore(List<Price> prices) {
		store.removeAll();
		BeanModelFactory beanModelFactory =
			BeanModelLookup.get().getFactory(Price.class);
		if(prices != null && !prices.isEmpty()) {
			for(Price price : prices) {
				store.add(beanModelFactory.createModel(price));
			}
		}
	}
	
}
