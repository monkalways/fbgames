package ca.weiway.fbgames.server.guice;

import ca.weiway.fbgames.server.handler.DeleteGamesHandler;
import ca.weiway.fbgames.server.handler.GetAllGamesHandler;
import ca.weiway.fbgames.server.handler.SaveGameHandler;
import ca.weiway.fbgames.server.parser.GameParser;
import ca.weiway.fbgames.server.parser.GameParserJSoupImpl;
import ca.weiway.fbgames.shared.action.DeleteGamesAction;
import ca.weiway.fbgames.shared.action.GetAllGamesAction;
import ca.weiway.fbgames.shared.action.SaveGameAction;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

public class GuiceServerModule extends ActionHandlerModule {

	public GuiceServerModule() {
	}

	@Override
	protected void configureHandlers() {
		bind(GameParser.class).to(GameParserJSoupImpl.class).in(com.google.inject.Singleton.class);
		
		bindHandler(GetAllGamesAction.class, GetAllGamesHandler.class);
		bindHandler(SaveGameAction.class, SaveGameHandler.class);
		bindHandler(DeleteGamesAction.class, DeleteGamesHandler.class);
	}

}
