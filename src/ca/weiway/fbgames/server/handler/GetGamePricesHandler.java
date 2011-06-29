package ca.weiway.fbgames.server.handler;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;

import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.shared.action.GetGamePricesAction;
import ca.weiway.fbgames.shared.action.GetGamePricesResult;
import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.Price;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class GetGamePricesHandler implements ActionHandler<GetGamePricesAction, GetGamePricesResult> {
	
	@Inject
	private EntityManagerProvider emp;

	@Override
	public GetGamePricesResult execute(GetGamePricesAction action,
			ExecutionContext context) throws DispatchException {
		EntityManager em = emp.get();
		List<Price> prices = new ArrayList<Price>();
		GetGamePricesResult result = new GetGamePricesResult(prices);
		try {
			Query q = em.createQuery("select from Game g where g.id = " + action.getGameId());
			final Game game = (Game) q.getSingleResult();
			if(game.getPrices() != null && !game.getPrices().isEmpty()) {
				for(Price price : game.getPrices()) {
					prices.add(price);
				}
			}
		} finally {
			em.close();
		}
		return result;
	}

	@Override
	public Class<GetGamePricesAction> getActionType() {
		return GetGamePricesAction.class;
	}

	@Override
	public void rollback(GetGamePricesAction arg0, GetGamePricesResult arg1,
			ExecutionContext arg2) throws DispatchException {
		// TODO Auto-generated method stub
		
	}
	

}
