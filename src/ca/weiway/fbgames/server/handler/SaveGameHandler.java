package ca.weiway.fbgames.server.handler;

import javax.persistence.EntityManager;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.shared.action.SaveGameAction;
import ca.weiway.fbgames.shared.action.SaveGameResult;
import ca.weiway.fbgames.shared.model.Game;

import com.google.inject.Inject;

public class SaveGameHandler implements ActionHandler<SaveGameAction, SaveGameResult> {
	
	private EntityManagerProvider emp;
	
	@Inject
	public SaveGameHandler(final EntityManagerProvider pmp) {
		this.emp = pmp;
	}

	@Override
	public SaveGameResult execute(SaveGameAction action, ExecutionContext context)
			throws DispatchException {
		
		final Game gameToSave = action.getGame();
		final SaveGameResult retval = new SaveGameResult();
		final EntityManager em = emp.get();
		
		try {
			em.persist(gameToSave);
			retval.setSuccess(true);
		} finally {
			em.close();
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
