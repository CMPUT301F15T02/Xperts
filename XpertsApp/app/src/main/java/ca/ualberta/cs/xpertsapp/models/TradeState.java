package ca.ualberta.cs.xpertsapp.models;

/**
 * Created by murdock on 10/16/15.
 */
public interface TradeState {
   public void accept(Trade context);
   public void decline(Trade context);
   public void cancel(Trade context);
}
