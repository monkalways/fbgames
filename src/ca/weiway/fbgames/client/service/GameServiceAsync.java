package ca.weiway.fbgames.client.service;

import ca.weiway.fbgames.shared.model.Game;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GameServiceAsync {

	void loadGames(PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<Game>> callback);

}
