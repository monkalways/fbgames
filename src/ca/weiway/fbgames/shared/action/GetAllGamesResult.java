package ca.weiway.fbgames.shared.action;

import java.util.ArrayList;

import ca.weiway.fbgames.shared.model.Game;

import net.customware.gwt.dispatch.shared.Result;

public class GetAllGamesResult implements Result {
	
	private static final long serialVersionUID = -9099789297842581458L;
	
	private ArrayList<Game> games;
	
	public GetAllGamesResult() {
		
	}
	
	public GetAllGamesResult(ArrayList<Game> games) {
		this.games = games;
	}

	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	
}
