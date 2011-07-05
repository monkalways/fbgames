package ca.weiway.fbgames.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ca.weiway.fbgames.client.service.GameService;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.shared.model.Game;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;

@SuppressWarnings("serial")
public class GameServiceImpl extends RemoteServiceServlet implements
		GameService {

	@Inject
	private EntityManagerProvider emp;

	@SuppressWarnings("unchecked")
	@Override
	public PagingLoadResult<Game> loadGames(PagingLoadConfig config) {
		EntityManager em = emp.get();

		try {
			Query query = 
				em.createQuery(buildQuery(config.getSortField(), config.getSortDir().name()));
			query.setFirstResult(config.getOffset());
			query.setMaxResults(config.getLimit());
			List<Game> games = (List<Game>)query.getResultList();
			
			PagingLoadResult<Game> result = new BasePagingLoadResult<Game>(new ArrayList<Game>(games));
			
			query = em.createNamedQuery("Game.getTotalOfGame");
			int totalOfGames = (Integer)query.getSingleResult();
			result.setTotalLength(totalOfGames);
			
			result.setOffset(config.getOffset());
			
			return result;
		} finally {
			em.close();
		}
	}
	
	private String buildQuery(String sortField, String sortDir) {
		String query = "select from Game game order by ";
		if(sortField == null || sortField.isEmpty()) {
			query += "name asc";
		} else {
			query += sortField + " " + sortDir;
		}
		return query;
	}
}
