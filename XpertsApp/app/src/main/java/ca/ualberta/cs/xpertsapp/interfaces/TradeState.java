package ca.ualberta.cs.xpertsapp.interfaces;

import ca.ualberta.cs.xpertsapp.model.Trade;

public interface TradeState {
	void accept(Trade context);

	void decline(Trade context);

	void cancel(Trade context);
}