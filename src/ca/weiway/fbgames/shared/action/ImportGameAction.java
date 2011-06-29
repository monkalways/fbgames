package ca.weiway.fbgames.shared.action;

import net.customware.gwt.dispatch.shared.Action;

public class ImportGameAction implements Action<ImportGameResult> {
	
	private String gameLink;
	
	public ImportGameAction() {
		
	}
	
	public ImportGameAction(final String gameLink) {
		this.gameLink = gameLink;
	}

	public String getGameLink() {
		return gameLink;
	}

	public void setGameLink(String gameLink) {
		this.gameLink = gameLink;
	}
	
}
