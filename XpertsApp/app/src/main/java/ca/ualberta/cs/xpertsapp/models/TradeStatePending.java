package ca.ualberta.cs.xpertsapp.models;

/**
 * Created by kmbaker on 10/30/15.
 */
public class TradeStatePending implements TradeState {
    public void accept(Trade context) {
        context.setTradeState(new TradeStateAccepted());
    }
    public void decline(Trade context) {
        context.setTradeState(new TradeStateDeclined());
    }
    public void cancel(Trade context) {
        context.setTradeState(new TradeStateCancelled());
    }
}
