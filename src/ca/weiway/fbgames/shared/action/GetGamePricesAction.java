package ca.weiway.fbgames.shared.action;

import net.customware.gwt.dispatch.shared.Action;

public class GetGamePricesAction implements Action<GetGamePricesResult> {
	
	private Long gameId;
	
	public GetGamePricesAction() {
		
	}
	
	public GetGamePricesAction(Long gameId) {
		this.gameId = gameId;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
}
