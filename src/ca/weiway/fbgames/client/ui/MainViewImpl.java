package ca.weiway.fbgames.client.ui;

import com.extjs.gxt.ui.client.GXT;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class MainViewImpl extends Composite implements MainView {

	private static MainViewImplUiBinder uiBinder = GWT
			.create(MainViewImplUiBinder.class);

	interface MainViewImplUiBinder extends UiBinder<Widget, MainViewImpl> {
	}
	
	@Inject
	public MainViewImpl(ActivityMapper mapper, EventBus eventBus,
			HeaderView header) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.header = header;
		homeHeaderPanel.setWidget(header);
		
//		menuBar.addTab("On Sale!");
//        menuBar.addTab("Recent Price Drops");
//        menuBar.addTab("New Comers");
//        menuBar.addTab("All Games");
//        menuBar.addTab("Admin");
        
        ActivityManager manager = new ActivityManager(mapper, eventBus);
		manager.setDisplay(contentsPanel);
		
		GXT.hideLoadingPanel("loading");
	}
	
	private HeaderView header;
	
//    @UiField
//    DecoratedTabBar menuBar;

    @UiField
    SimplePanel contentsPanel;

    @UiField
    AcceptsOneWidget homeHeaderPanel;
    

}
