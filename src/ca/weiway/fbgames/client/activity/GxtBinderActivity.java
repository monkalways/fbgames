package ca.weiway.fbgames.client.activity;

import ca.weiway.fbgames.client.ui.GxtBinderViewImpl;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class GxtBinderActivity extends AbstractActivity {
	
	private GxtBinderViewImpl view;
	
	@Inject
	public GxtBinderActivity(GxtBinderViewImpl view) {
		this.view = view;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
		
	}

}
