package ca.weiway.fbgames.shared.action;

import java.util.ArrayList;
import java.util.List;

import ca.weiway.fbgames.shared.model.Game;

import net.customware.gwt.dispatch.shared.Result;

public class GetAllGamesResult implements Result {
	
	private static final long serialVersionUID = -9099789297842581458L;
	
	private List<Game> games;
	
	public GetAllGamesResult() {
		
	}
	
	public GetAllGamesResult(List<Game> games) {
		this.games = games;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
	
}
