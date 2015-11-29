package ca.ualberta.cs.xpertsapp.model;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

/**
 * Represents the Pending state of a trade
 */
public class TradeStatePending implements TradeState {
	@Override
	/** accept the trade */
	public void accept(Trade context) {
		if (MyApplication.getLocalUser() != context.getOwner()) throw new AssertionError();
		context.setState(new TradeStateAccepted());
		context.status = 1;
	}

	@Override
	/** decline the trade */
	public void decline(Trade context) {
		if (MyApplication.getLocalUser() != context.getOwner()) throw new AssertionError();
		context.setState(new TradeStateDeclined());
		context.status = 3;
	}

	@Override
	/** cancel the trade */
	//TODO I think context.getOwner() should be getBorrower() but this isn't being used
	public void cancel(Trade context) {
		if (MyApplication.getLocalUser() != context.getOwner()) throw new AssertionError();
		context.setState(new TradeStateCancelled());
		context.status = 2;
	}

	@Override
	/** complete the trade - crash */
	public void complete(Trade context) {
		throw new AssertionError();
	}
}