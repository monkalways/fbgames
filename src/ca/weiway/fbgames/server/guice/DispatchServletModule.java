package ca.weiway.fbgames.server.guice;

import net.customware.gwt.dispatch.server.guice.GuiceStandardDispatchServlet;

import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		super.configureServlets();
		serve("/fbgames/dispatch").with(GuiceStandardDispatchServlet.class);
		serve("/fbgames/game").with(GuiceRemoteServiceServlet.class);
		serve("/fbgames/gameparsing").with(GamesParsingServlet.class);
		
	}
}
