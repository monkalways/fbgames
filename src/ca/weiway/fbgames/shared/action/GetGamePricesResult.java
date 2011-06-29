package ca.weiway.fbgames.shared.action;

import java.util.List;

import ca.weiway.fbgames.shared.model.Price;

import net.customware.gwt.dispatch.shared.Result;

public class GetGamePricesResult implements Result {

	private List<Price> prices;
	
	public GetGamePricesResult() {
		
	}
	
	
	public GetGamePricesResult(final List<Price> prices) {
		this.prices = prices;
	}


	public List<Price> getPrices() {
		return prices;
	}


	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	
}
