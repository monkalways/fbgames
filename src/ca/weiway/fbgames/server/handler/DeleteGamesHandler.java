package ca.weiway.fbgames.server.handler;

import java.util.List;

import javax.jdo.PersistenceManager;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.PersistenceManagerProvider;
import ca.weiway.fbgames.shared.action.DeleteGamesAction;
import ca.weiway.fbgames.shared.action.DeleteGamesResult;
import ca.weiway.fbgames.shared.model.Game;

import com.google.inject.Inject;

public class DeleteGamesHandler implements ActionHandler<DeleteGamesAction, DeleteGamesResult> {
	
	private PersistenceManagerProvider pmp;
	
	@Inject
	public DeleteGamesHandler(PersistenceManagerProvider pmp) {
		this.pmp = pmp;
	}

	@Override
	public DeleteGamesResult execute(DeleteGamesAction action,
			ExecutionContext context) throws DispatchException {
		PersistenceManager pm = pmp.get();

		try {
			
			List<Long> gameIdsToDelete = action.getGameIdsToDelete();
			
			for(Long gameIdToDelete : gameIdsToDelete) {
				Game gameToDelete = (Game)pm.getObjectById(Game.class, gameIdToDelete);
				pm.deletePersistent(gameToDelete);
			}

			return new DeleteGamesResult();
		} finally {
			pm.close();
		}
	}

	@Override
	public Class<DeleteGamesAction> getActionType() {
		return DeleteGamesAction.class;
	}

	@Override
	public void rollback(DeleteGamesAction arg0, DeleteGamesResult arg1,
			ExecutionContext arg2) throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
