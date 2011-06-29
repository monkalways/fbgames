package ca.weiway.fbgames.shared.action;

import net.customware.gwt.dispatch.shared.Result;

public class ImportGameResult implements Result {
	private Boolean success;

	public ImportGameResult() {
	}

	public ImportGameResult(final Boolean success) {
		this.success = success;
	}

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
