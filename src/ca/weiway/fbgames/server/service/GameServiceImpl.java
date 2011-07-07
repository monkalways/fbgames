package ca.weiway.fbgames.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ca.weiway.fbgames.client.service.GameService;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.shared.model.Game;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.FilterConfig;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
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
	public PagingLoadResult<Game> loadGames(FilterPagingLoadConfig config) {
		EntityManager em = emp.get();

		try {
			Query query = 
				em.createQuery(buildQuery(config.getSortField(), config.getSortDir().name(), config.getFilterConfigs()));
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
	
	private String buildQuery(String sortField, String sortDir, List<FilterConfig> filters) {
		StringBuffer query = new StringBuffer("select from Game game");
		if(filters != null && !filters.isEmpty()) {
			query.append(" where ");
			for(FilterConfig filter : filters) {
				if("name".equals(filter.getField())) {
					query.append("game.name like '" + filter.getValue() + "%'"); 
				}
			}
		}
		
		query.append(" order by ");
		if(sortField == null || sortField.isEmpty()) {
			query.append("game.name asc");
		} else {
			query.append("game." + sortField + " " + sortDir);
		}
		return query.toString();
	}
}
