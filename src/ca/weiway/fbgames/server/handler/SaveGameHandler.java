package ca.weiway.fbgames.server.handler;

import javax.jdo.PersistenceManager;

import com.google.inject.Inject;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.PersistenceManagerProvider;
import ca.weiway.fbgames.shared.action.SaveGameAction;
import ca.weiway.fbgames.shared.action.SaveGameResult;
import ca.weiway.fbgames.shared.model.Game;

public class SaveGameHandler implements ActionHandler<SaveGameAction, SaveGameResult> {
	
	private PersistenceManagerProvider pmp;
	
	@Inject
	public SaveGameHandler(final PersistenceManagerProvider pmp) {
		this.pmp = pmp;
	}

	@Override
	public SaveGameResult execute(SaveGameAction action, ExecutionContext context)
			throws DispatchException {
		
		final Game gameToSave = action.getGame();
		final SaveGameResult retval = new SaveGameResult();
		final PersistenceManager pm = pmp.get();
		
		try {
			pm.makePersistent(gameToSave);
			retval.setSuccess(true);
		} finally {
			pm.close();
		}

		return retval;
	}

	@Override
	public Class<SaveGameAction> getActionType() {
		return SaveGameAction.class;
	}

	@Override
	public void rollback(SaveGameAction arg0, SaveGameResult arg1,
			ExecutionContext arg2) throws DispatchException {
		
	}

}
