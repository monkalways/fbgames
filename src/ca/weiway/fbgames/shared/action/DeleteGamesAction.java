package ca.weiway.fbgames.shared.action;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteGamesAction implements Action<DeleteGamesResult> {
	
	private List<Long> gameIdsToDelete;
	
	public DeleteGamesAction() {
		
	}
	
	public DeleteGamesAction(List<Long> gameIdsToDelete) {
		this.gameIdsToDelete = gameIdsToDelete;
	}

	public List<Long> getGameIdsToDelete() {
		return gameIdsToDelete;
	}

	public void setGameIdsToDelete(List<Long> gameIdsToDelete) {
		this.gameIdsToDelete = gameIdsToDelete;
	}

}
