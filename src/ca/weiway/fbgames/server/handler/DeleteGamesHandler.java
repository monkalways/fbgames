package ca.weiway.fbgames.server.handler;

import java.util.List;

import javax.persistence.EntityManager;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.shared.action.DeleteGamesAction;
import ca.weiway.fbgames.shared.action.DeleteGamesResult;
import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.Price;

import com.google.inject.Inject;

public class DeleteGamesHandler implements ActionHandler<DeleteGamesAction, DeleteGamesResult> {
	
	private EntityManagerProvider emp;
	
	@Inject
	public DeleteGamesHandler(EntityManagerProvider pmp) {
		this.emp = pmp;
	}

	@Override
	public DeleteGamesResult execute(DeleteGamesAction action,
			ExecutionContext context) throws DispatchException {
		EntityManager em = emp.get();

		try {
			
			List<Long> gameIdsToDelete = action.getGameIdsToDelete();
			
			for(Long gameIdToDelete : gameIdsToDelete) {
				Game gameToDelete = (Game)em.find(Game.class, gameIdToDelete);
				for(Price price : gameToDelete.getPrices()) {
					em.remove(price);
				}
//				gameToDelete.getPrices().clear();
				em.remove(gameToDelete);
			}

			return new DeleteGamesResult();
		} finally {
			em.close();
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
