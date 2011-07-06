package ca.weiway.fbgames.shared.action;

import java.util.Map;

import net.customware.gwt.dispatch.shared.Result;

public class ParseGamesResult implements Result {

	private static final long serialVersionUID = -9099789297842581458L;
	
	private Map<String, String> games;
	private boolean success;

	public ParseGamesResult() {
	}

	public ParseGamesResult(final Map<String, String> games) {
		this.games = games;
	}

	public Map<String, String> getGames() {
		return games;
	}

	public void setGames(Map<String, String> games) {
		this.games = games;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
