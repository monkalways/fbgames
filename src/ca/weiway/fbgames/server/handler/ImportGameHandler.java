package ca.weiway.fbgames.server.handler;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ca.weiway.fbgames.shared.model.GameDetail;
import ca.weiway.fbgames.shared.model.Platform;
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
		Game gameInDB = findGameByNameAndPlatform(game.getName(), game.getPlatform());
		if(gameInDB != null) {
			updateGame(gameInDB, game);
			updatePrice(game, gameInDB);
			updateGameDetail(game, gameInDB);

			saveGame(gameInDB);
		} else {
			Price price = game.getPrices().iterator().next();
			game.setLatestLowestPrice(price.getPrice());
			game.setLatestLowestPriceSource(price.getSource());
			saveGame(game);
		}
	}

	private void updatePrice(Game game, Game gameInDB) {
		Price currentPrice = game.getPrices().iterator().next();
		PriceSource source = currentPrice.getSource();
		Price latestPrice = getLatestGamePrice(gameInDB, source);
		
		if(latestPrice != null) {
			if(!equals(latestPrice.getPrice(), currentPrice.getPrice())) {
				addNewPrice(currentPrice, gameInDB);
			} else {
				return;
			}
		} else {
			addNewPrice(currentPrice, gameInDB);
		}
	}
	
	private void updateGameDetail(Game game, Game gameInDB) {
		GameDetail newDetail = game.getGameDetails().iterator().next();
		GameDetail currentDetail = findGameDetail(gameInDB, newDetail.getSource());
		
		if(currentDetail == null) {
			addNewGameDetail(gameInDB, newDetail);
		}
	}
	
	private void addNewGameDetail(Game game, GameDetail newDetail) {
		game.getGameDetails().add(newDetail);
		newDetail.setGame(game);
		game.setUpdateDate(new Date());
	}

	@SuppressWarnings("unchecked")
	private GameDetail findGameDetail(Game game, PriceSource source) {
		EntityManager em = emp.get();
		
		try {
			Query query = 
				em.createQuery("select from GameDetail detail where detail.game = :game and detail.source = :source");
			query.setParameter("game", game);
			query.setParameter("source", source);
			List<GameDetail> details = (List<GameDetail>) query.getResultList();
			if(details != null && details.size() == 1) {
				return details.get(0);
			} else if(details != null && details.size() > 1){
				throw new RuntimeException("There are more than 1 game detail with " + game + "_" + source);
			} else {
				return null;
			}
		} finally {
			em.close();
		}
	}

	private void updateGame(Game gameInDB, Game game) {
		if(game.getBestBuyImageLink() != null && !game.getBestBuyImageLink().isEmpty()
				&& (gameInDB.getBestBuyImageLink() == null || gameInDB.getBestBuyImageLink().isEmpty())) {
			gameInDB.setBestBuyImageLink(game.getBestBuyImageLink());
		}
		if(game.getGameStopImageLink() != null && !game.getGameStopImageLink().isEmpty()
				&& (gameInDB.getGameStopImageLink() == null || gameInDB.getGameStopImageLink().isEmpty())) {
			gameInDB.setGameStopImageLink(game.getGameStopImageLink());
		}
		if(game.getReleaseDate() != null && gameInDB.getReleaseDate() == null) {
			gameInDB.setReleaseDate(game.getReleaseDate());
		}
		if(game.getDeveloper() != null && !game.getDeveloper().isEmpty()
				&& (gameInDB.getDeveloper() == null || gameInDB.getDeveloper().isEmpty())) {
			gameInDB.setDeveloper(game.getDeveloper());
		}
		if(game.getPublisher() != null && !game.getPublisher().isEmpty()
				&& (gameInDB.getPublisher() == null || gameInDB.getPublisher().isEmpty())) {
			gameInDB.setPublisher(game.getPublisher());
		}
		if(game.getGameCategory() != null && gameInDB.getGameCategory() == null) {
			gameInDB.setGameCategory(game.getGameCategory());
		}
		
	}

	@SuppressWarnings("unchecked")
	private Game findGameByNameAndPlatform(String name, Platform platform) {
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
	
	private void addNewPrice(Price price, Game game) {
		game.getPrices().add(price);
		price.setGame(game);
		game.setUpdateDate(new Date());
		
		Collection<Price> latestOtherPrices = 
			getLatestOtherPrices(game, price.getSource());
		
		Double lowestPrice = price.getPrice();
		PriceSource lowestSource = price.getSource();
		
		for(Price otherPrice : latestOtherPrices) {
			if(otherPrice.getPrice() < lowestPrice) {
				lowestPrice = otherPrice.getPrice();
				lowestSource = otherPrice.getSource();
			}
		}
		
		game.setLatestLowestPrice(lowestPrice);
		game.setLatestLowestPriceSource(lowestSource);
	}
	
	private void saveGame(Game game) {
		EntityManager em = emp.get();
		
		try {
			em.persist(game);
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Price> getLatestOtherPrices(Game game, PriceSource source) {
		EntityManager em = emp.get();
		Map<PriceSource, Price> latestOtherPrices = new HashMap<PriceSource, Price>();
		try {
			if(game != null) {
				Query query = 
					em.createQuery(
							"select from Price p where p.game = :game and p.source <> :source " +
							"order by p.createDate DESC");
				query.setParameter("game", game);
				query.setParameter("source", source);
				List<Price> prices = (List<Price>)query.getResultList();
				for(Price price : prices) {
					PriceSource currentPriceSource = price.getSource();
					if(!latestOtherPrices.containsKey(currentPriceSource)) {
						latestOtherPrices.put(currentPriceSource, price);
					}
				}
				
			} 
			return latestOtherPrices.values();
		} finally {
			em.close();
		}
	}
	
	public static boolean equals(double a, double b){
		double EPSILON = 0.00001;
	    return a == b ? true : Math.abs(a - b) < EPSILON;
	}
 
}
