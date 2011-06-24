package ca.weiway.fbgames.server.handler;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.guice.EntityManagerProvider;
import ca.weiway.fbgames.server.parser.GameParser;
import ca.weiway.fbgames.shared.action.GetAllGamesAction;
import ca.weiway.fbgames.shared.action.GetAllGamesResult;
import ca.weiway.fbgames.shared.model.Game;

import com.google.inject.Inject;

public class GetAllGamesHandler implements ActionHandler<GetAllGamesAction, GetAllGamesResult> {
	
	private EntityManagerProvider emp;
	private GameParser parser;
	
	@Inject
	public GetAllGamesHandler(EntityManagerProvider emp,
			GameParser parser) {
		this.emp = emp;
		this.parser = parser;
	}

	@Override
	@SuppressWarnings("unchecked")
	public GetAllGamesResult execute(GetAllGamesAction action,
			ExecutionContext context) throws DispatchException {
		EntityManager em = emp.get();

		try {
			Query q = em.createQuery("select from " + Game.class.getName());
//			query.setOrdering("timeFetched desc");

			// The following is needed because of an issue
			// with the result of type StreamingQueryResult not
			// being serializable for GWT
			// See the discussion here:
			// http://groups.google.co.uk/group/google-appengine-java/browse_frm/thread/bce6630a3f01f23a/62cb1c4d38cc06c7?lnk=gst&q=com.google.gwt.user.client.rpc.SerializationException:+Type+'org.datanucleus.store.appengine.query.StreamingQueryResult'+was+not+included+in+the+set+of+types+which+can+be+serialized+by+this+SerializationPolicy
			
			final List<Game> queryResult = (List<Game>) q.getResultList();
			final ArrayList<Game> games = new ArrayList<Game>(queryResult.size());

			for (final Object game : queryResult) {
				games.add((Game) game);
			}

			return new GetAllGamesResult(games);
		} finally {
			em.close();
		}
//		try {
//			final ArrayList<Game> games = new ArrayList<Game>();
//			final List<String> gameLinks = 
//				parser.parseGameLinks("http://www.bestbuy.ca/catalog/category.aspx?lang=en-CA&category=23374&Page=1&PageSize=15");
//			
//			for(String gameLink : gameLinks) {
//				games.add(parser.parse(gameLink));
//			}
//			
//			return new GetAllGamesResult(games);
//			
//		} catch(Exception ex) {
//			ex.printStackTrace();
//			return null;
//		}
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
