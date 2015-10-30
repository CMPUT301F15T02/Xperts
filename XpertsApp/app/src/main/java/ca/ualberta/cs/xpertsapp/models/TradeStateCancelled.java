package ca.ualberta.cs.xpertsapp.models;

/**
 * Created by kmbaker on 10/30/15.
 */
public class TradeStateCancelled implements  TradeState{
    public void accept(Trade context) {}
    public void decline(Trade context) {}
    public void cancel(Trade context) {}
}
