package ca.weiway.fbgames.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import ca.weiway.fbgames.client.service.GameService;
import ca.weiway.fbgames.server.handler.DeleteAllGamesHandler;
import ca.weiway.fbgames.server.handler.DeleteGamesHandler;
import ca.weiway.fbgames.server.handler.GetAllGamesHandler;
import ca.weiway.fbgames.server.handler.GetGamePricesHandler;
import ca.weiway.fbgames.server.handler.ImportGameHandler;
import ca.weiway.fbgames.server.handler.ParseGamesHandler;
import ca.weiway.fbgames.server.handler.SaveGameHandler;
import ca.weiway.fbgames.server.parser.GameParser;
import ca.weiway.fbgames.server.parser.GameParserJSoupImpl;
import ca.weiway.fbgames.server.service.GameServiceImpl;
import ca.weiway.fbgames.shared.action.DeleteAllGamesAction;
import ca.weiway.fbgames.shared.action.DeleteGamesAction;
import ca.weiway.fbgames.shared.action.GetAllGamesAction;
import ca.weiway.fbgames.shared.action.GetGamePricesAction;
import ca.weiway.fbgames.shared.action.ImportGameAction;
import ca.weiway.fbgames.shared.action.ParseGamesAction;
import ca.weiway.fbgames.shared.action.SaveGameAction;

public class GuiceServerModule extends ActionHandlerModule {

	public GuiceServerModule() {
	}

	@Override
	protected void configureHandlers() {
		bind(GameParser.class).to(GameParserJSoupImpl.class).in(com.google.inject.Singleton.class);
		
		bindHandler(GetAllGamesAction.class, GetAllGamesHandler.class);
		bindHandler(SaveGameAction.class, SaveGameHandler.class);
		bindHandler(DeleteGamesAction.class, DeleteGamesHandler.class);
		bindHandler(ImportGameAction.class, ImportGameHandler.class);
		bindHandler(GetGamePricesAction.class, GetGamePricesHandler.class);
		bindHandler(ParseGamesAction.class, ParseGamesHandler.class);
		bindHandler(DeleteAllGamesAction.class, DeleteAllGamesHandler.class);
		
		bind(GameService.class).to(GameServiceImpl.class);
	}

}
