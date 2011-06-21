package ca.weiway.fbgames.client.ui;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;

public class MainView extends SimplePanel {
	
	@Inject
	public MainView(ActivityMapper mapper, EventBus eventBus) {
		ActivityManager manager = new ActivityManager(mapper, eventBus);
		manager.setDisplay(this);
	}
	
}
