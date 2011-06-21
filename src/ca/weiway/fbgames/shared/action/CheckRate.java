package ca.weiway.fbgames.shared.action;

import ca.weiway.fbgames.shared.model.Rate.RateType;
import net.customware.gwt.dispatch.shared.Action;

public class CheckRate implements Action<CheckRateResult> {

	private static final long serialVersionUID = -1716760883016361503L;

	private RateType type;

	public CheckRate() {
	}

	public CheckRate(final RateType type) {
		this.type = type;
	}

	public void setType(final RateType type) {
		this.type = type;
	}

	public RateType getType() {
		return type;
	}

}
