package ca.ualberta.cs.xpertsapp.model;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

/**
 * Represents the Accepted state of a trade
 */
public class TradeStateAccepted implements TradeState {
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

	@Override
	/** complete the trade */
	public void complete(Trade context) {
		if (MyApplication.getLocalUser() != context.getOwner()) throw new AssertionError();
		context.setState(new TradeStateAccepted());
		context.status = 4;
	}
}