package ca.weiway.fbgames.server.handler;

import java.io.IOException;

import javax.persistence.EntityManager;

import com.google.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.server.parser.GameParser;
import ca.weiway.fbgames.shared.action.ImportGameAction;
import ca.weiway.fbgames.shared.action.ImportGameResult;
import ca.weiway.fbgames.shared.model.Game;

public class ImportGameHandler implements ActionHandler<ImportGameAction, ImportGameResult> {
	
	@Inject
	private GameParser parser;
	
	@Inject
	private EntityManagerProvider emp;

	@Override
	public ImportGameResult execute(ImportGameAction action, ExecutionContext context)
			throws DispatchException {
		
		ImportGameResult result = new ImportGameResult();
		String gameLink = action.getGameLink();
		EntityManager em = emp.get();
		try {
			Game game = parser.parse(gameLink);
			em.persist(game);
			result.setSuccess(true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		return result;
		
	}

	@Override
	public Class<ImportGameAction> getActionType() {
		return ImportGameAction.class;
	}

	@Override
	public void rollback(ImportGameAction arg0, ImportGameResult arg1,
			ExecutionContext arg2) throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
