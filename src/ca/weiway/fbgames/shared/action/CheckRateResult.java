package ca.weiway.fbgames.shared.action;

import ca.weiway.fbgames.shared.model.Rate;
import net.customware.gwt.dispatch.shared.Result;

public class CheckRateResult implements Result {

	private static final long serialVersionUID = -9099789297842581458L;

	private Rate rate;

	public CheckRateResult() {
	}

	public CheckRateResult(final Rate rate) {
		this.rate = rate;
	}

	public void setRate(final Rate rate) {
		this.rate = rate;
	}

	public Rate getRate() {
		return rate;
	}
}
