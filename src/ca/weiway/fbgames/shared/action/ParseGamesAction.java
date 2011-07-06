package ca.weiway.fbgames.shared.action;

import net.customware.gwt.dispatch.shared.Action;

public class ParseGamesAction implements Action<ParseGamesResult> {
	
	private String batchImportLink;
	
	public ParseGamesAction() {
		
	}
	
	public ParseGamesAction(final String batchImportLink) {
		this.batchImportLink = batchImportLink;
	}

	public String getBatchImportLink() {
		return batchImportLink;
	}

	public void setBatchImportLink(String batchImportLink) {
		this.batchImportLink = batchImportLink;
	}
	
}