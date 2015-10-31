package ca.ualberta.cs.xpertsapp.model;

import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

public class TradeStatePending implements TradeState {
	@Override
	public void accept(Trade context) {
		context.setState(new TradeStateAccepted());
	}

	@Override
	public void decline(Trade context) {
		context.setState(new TradeStateDeclined());
	}

	@Override
	public void cancel(Trade context) {
		context.setState(new TradeStateCancelled());
	}
}