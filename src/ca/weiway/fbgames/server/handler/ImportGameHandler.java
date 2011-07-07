package ca.weiway.fbgames.server.handler;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.server.parser.GameParser;
import ca.weiway.fbgames.shared.action.ImportGameAction;
import ca.weiway.fbgames.shared.action.ImportGameResult;
import ca.weiway.fbgames.shared.model.Game;
import ca.weiway.fbgames.shared.model.Price;
import ca.weiway.fbgames.shared.model.PriceSource;

import com.google.inject.Inject;

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
		
		try {
			Game game = parser.parse(gameLink);
			importGame(game);
			result.setSuccess(true);
		} catch (IOException e) {
			e.printStackTrace();
			result.setSuccess(false);
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
	
	private void importGame(Game game) {
		Game gameInDB = findGameByNameAndPlatform(game.getName(), game.getPlatform().toString());
		if(gameInDB != null) {
			Price currentPrice = game.getPrices().iterator().next();
			PriceSource source = currentPrice.getSource();
			Price latestPrice = getLatestGamePrice(gameInDB, source);
			
			if(latestPrice != null) {
				if(!equals(latestPrice.getPrice(), currentPrice.getPrice())) {
					saveNewPrice(currentPrice, gameInDB);
				} else {
					return;
				}
			} else {
				saveNewPrice(currentPrice, gameInDB);
			}
		} else {
			saveNewGame(game);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Game findGameByNameAndPlatform(String name, String platform) {
		EntityManager em = emp.get();
		
		try {
			Query query = 
				em.createQuery("select from Game g where g.name = :name and g.platform = :platform");
			query.setParameter("name", name);
			query.setParameter("platform", platform);
			List<Game> games = (List<Game>) query.getResultList();
			if(games != null && games.size() == 1) {
				return games.get(0);
			} else if(games != null && games.size() > 1){
				throw new RuntimeException("There are more than 1 games with " + name + "_" + platform);
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private Price getLatestGamePrice(Game game, PriceSource source) {
		EntityManager em = emp.get();
		
		try {
			
			if(game != null) {
				Query query = 
					em.createQuery(
							"select from Price p where p.game = :game and p.source = :source " +
							"order by p.createDate DESC");
				query.setParameter("game", game);
				query.setParameter("source", source);
				List<Price> prices = (List<Price>)query.getResultList();
				if(prices != null && !prices.isEmpty()) {
					return prices.get(0);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}
	
	private void saveNewPrice(Price price, Game game) {
		EntityManager em = emp.get();
		
		try {
			game.getPrices().add(price);
			price.setGame(game);
			game.setUpdateDate(new Date());
			em.persist(game);
		} finally {
			em.close();
		}
	}
	
	private void saveNewGame(Game game) {
		EntityManager em = emp.get();
		
		try {
			em.persist(game);
		} finally {
			em.close();
		}
	}
	
	public static boolean equals(double a, double b){
		double EPSILON = 0.00001;
	    return a == b ? true : Math.abs(a - b) < EPSILON;
	}
 
}
