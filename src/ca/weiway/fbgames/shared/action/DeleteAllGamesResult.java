package ca.weiway.fbgames.shared.action;

import net.customware.gwt.dispatch.shared.Result;

public class DeleteAllGamesResult implements Result {
	
	private Integer deleteRecords; 
	
	public DeleteAllGamesResult() {
		
	}

	public DeleteAllGamesResult(int deleteRecords) {
		this.deleteRecords = deleteRecords;
	}

	public Integer getDeleteRecords() {
		return deleteRecords;
	}

	public void setDeleteRecords(Integer deleteRecords) {
		this.deleteRecords = deleteRecords;
	}

}
