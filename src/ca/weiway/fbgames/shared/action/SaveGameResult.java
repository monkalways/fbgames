package ca.weiway.fbgames.shared.action;

import net.customware.gwt.dispatch.shared.Result;

public class SaveGameResult implements Result {

	private static final long serialVersionUID = -9099789297842581458L;
	
	private Boolean success;

	public SaveGameResult() {
	}

	public SaveGameResult(final Boolean success) {
		this.success = success;
	}

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}

