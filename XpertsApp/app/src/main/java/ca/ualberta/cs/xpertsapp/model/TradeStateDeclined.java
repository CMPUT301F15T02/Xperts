package ca.ualberta.cs.xpertsapp.model;

import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

/**
 * Represents the Declined state of a trade
 */
public class TradeStateDeclined implements TradeState {
	@Override
	/** accept the trade - crash */
	public void accept(Trade context) {
		throw new AssertionError();
	}

	@Override
	/** decline the trade - crash */
	public void decline(Trade context) {
		throw new AssertionError();
	}

	@Override
	/** cancel the trade - crash */
	public void cancel(Trade context) {
		throw new AssertionError();
	}
}