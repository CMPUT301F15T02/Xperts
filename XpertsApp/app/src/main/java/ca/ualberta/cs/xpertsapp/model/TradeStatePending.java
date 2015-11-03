package ca.ualberta.cs.xpertsapp.model;

import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

public class TradeStatePending implements TradeState {
	@Override
	public void accept(Trade context) {
		if (UserManager.sharedManager().localUser() != context.getBorrower()) throw new AssertionError();
		context.setState(new TradeStateAccepted());
		context.status = 1;
	}

	@Override
	public void decline(Trade context) {
		if (UserManager.sharedManager().localUser() != context.getBorrower()) throw new AssertionError();
		context.setState(new TradeStateDeclined());
		context.status = 3;
	}

	@Override
	public void cancel(Trade context) {
		if (UserManager.sharedManager().localUser() != context.getOwner()) throw new AssertionError();
		context.setState(new TradeStateCancelled());
		context.status = 2;
	}
}