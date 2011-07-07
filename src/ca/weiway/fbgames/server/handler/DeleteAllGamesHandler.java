package ca.weiway.fbgames.server.handler;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.shared.action.DeleteAllGamesAction;
import ca.weiway.fbgames.shared.action.DeleteAllGamesResult;

import com.google.inject.Inject;

public class DeleteAllGamesHandler implements ActionHandler<DeleteAllGamesAction, DeleteAllGamesResult> {
	
	@Inject
	private EntityManagerProvider emp;

	@Override
	public DeleteAllGamesResult execute(DeleteAllGamesAction arg0,
			ExecutionContext arg1) throws DispatchException {
		EntityManager em = emp.get();

		try {
			Query query=em.createQuery("DELETE FROM Game game");
			int deleteRecords=query.executeUpdate();
			return new DeleteAllGamesResult(deleteRecords);
		} finally {
			em.close();
		}
	}

	@Override
	public Class<DeleteAllGamesAction> getActionType() {
		return DeleteAllGamesAction.class;
	}

	@Override
	public void rollback(DeleteAllGamesAction arg0, DeleteAllGamesResult arg1,
			ExecutionContext arg2) throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
