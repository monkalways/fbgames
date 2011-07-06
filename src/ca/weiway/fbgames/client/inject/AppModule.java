package ca.weiway.fbgames.client.inject;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;
import ca.weiway.fbgames.client.ui.MainView;
import ca.weiway.fbgames.client.ui.MainViewImpl;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class AppModule extends AbstractGinModule {

	@Override
	protected void configure() {
		
		install(new MvpModule());
		install(new ActivityModule());
		install(new ViewModule());
		
		bind(MainView.class).to(MainViewImpl.class).in(Singleton.class);
	}
	
	@Singleton
	@Provides
	public DispatchAsync provideDispatchAsync() {
		return new StandardDispatchAsync(new DefaultExceptionHandler());
	}

}
