package ca.weiway.fbgames.client.service;

import ca.weiway.fbgames.shared.model.Game;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("game")
public interface GameService extends RemoteService {
	PagingLoadResult<Game> loadGames(final PagingLoadConfig config);
}
