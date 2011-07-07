package ca.weiway.fbgames.server.handler;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.shared.action.GetAllGamesAction;
import ca.weiway.fbgames.shared.action.GetAllGamesResult;
import ca.weiway.fbgames.shared.model.Game;

import com.google.inject.Inject;

public class GetAllGamesHandler implements ActionHandler<GetAllGamesAction, GetAllGamesResult> {
	
	private EntityManagerProvider emp;
	
	@Inject
	public GetAllGamesHandler(EntityManagerProvider emp) {
		this.emp = emp;
	}

	@Override
	@SuppressWarnings("unchecked")
	public GetAllGamesResult execute(GetAllGamesAction action,
			ExecutionContext context) throws DispatchException {
		EntityManager em = emp.get();

		try {
			Query q = em.createQuery("select from " + Game.class.getName());
			final List<Game> queryResult = (List<Game>) q.getResultList();
			final ArrayList<Game> games = new ArrayList<Game>(queryResult.size());

			for (final Object game : queryResult) {
				games.add((Game) game);
			}

			return new GetAllGamesResult(games);
		} finally {
			em.close();
		}
	}

	@Override
	public Class<GetAllGamesAction> getActionType() {
		return GetAllGamesAction.class;
	}

	@Override
	public void rollback(GetAllGamesAction arg0, GetAllGamesResult arg1,
			ExecutionContext arg2) throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
