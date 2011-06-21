package ca.weiway.fbgames.server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import ca.weiway.fbgames.shared.action.CheckRate;
import ca.weiway.fbgames.shared.action.CheckRateResult;
import ca.weiway.fbgames.shared.model.Rate;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

public class CheckRateHandler implements ActionHandler<CheckRate, CheckRateResult> {

	public static final String URL_BUY = "http://www.ingdirect.ca/en/datafiles/rates/usbuying.html";

	public static final String URL_SELL = "http://www.ingdirect.ca/en/datafiles/rates/usselling.html";

	public CheckRateHandler() {
	}

	@Override
	public CheckRateResult execute(final CheckRate action, final ExecutionContext ctx) throws ActionException {
		final CheckRateResult retval = new CheckRateResult();

		String strUrl = null;
		switch (action.getType()) {
		case Buying:
			strUrl = URL_BUY;
			break;
		case Selling:
			strUrl = URL_SELL;
			break;
		}

		try {
			final URL url = new URL(strUrl);

			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(url.openStream()));

				final double dRate = Double.parseDouble(br.readLine());

				final Rate rate = new Rate();
				rate.setRate(dRate);
				rate.setType(action.getType());
				rate.setTimeFetched(new Date());

				retval.setRate(rate);

			} finally {
				if (br != null)
					br.close();
			}
		} catch (final MalformedURLException e) {
			e.printStackTrace();
			throw new ActionException(e);
		} catch (final IOException e) {
			e.printStackTrace();
			throw new ActionException(e);
		} catch (final NumberFormatException e) {
			e.printStackTrace();
			throw new ActionException(e);
		}

		return retval;
	}

	@Override
	public Class<CheckRate> getActionType() {
		return CheckRate.class;
	}

	@Override
	public void rollback(CheckRate arg0, CheckRateResult arg1,
			ExecutionContext arg2) throws DispatchException {
	}
}
