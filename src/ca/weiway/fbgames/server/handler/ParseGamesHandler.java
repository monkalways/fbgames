package ca.weiway.fbgames.server.handler;

import java.io.IOException;

import com.google.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.parser.GameParser;
import ca.weiway.fbgames.shared.action.ParseGamesAction;
import ca.weiway.fbgames.shared.action.ParseGamesResult;

public class ParseGamesHandler implements ActionHandler<ParseGamesAction, ParseGamesResult> {
	
	@Inject
	private GameParser parser;

	@Override
	public ParseGamesResult execute(ParseGamesAction action, ExecutionContext context)
			throws DispatchException {
		String url = action.getBatchImportLink();
		ParseGamesResult result;
		try {
			result = new ParseGamesResult(parser.parseGameLinks(url));
			result.setSuccess(true);
		} catch(IOException ex) {
			result = new ParseGamesResult();
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public Class<ParseGamesAction> getActionType() {
		return ParseGamesAction.class;
	}

	@Override
	public void rollback(ParseGamesAction arg0, ParseGamesResult arg1,
			ExecutionContext arg2) throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
