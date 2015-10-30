package ca.ualberta.cs.xpertsapp.datamanagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.models.Trade;

/**
 * Created by justin on 23/10/15.
 */
public class TradeManager {
    private Map<String, Trade> trades = new HashMap<String, Trade>();
    private static TradeManager instance = null;

    private TradeManager() { }

    //?
    public TradeManager sharedManager() {
        if (instance == null) {
            instance = new TradeManager();
        }
        return instance;
    }

    public Map<String,Trade> getTrades() {
        return trades;
    }

    public Trade getTrade(String id) {
        return trades.get(id);
    }

    //? uml not the same
    public void addTrade(Trade trade) {
        trades.put(trade.getId(),trade);
    }

    public void clearCache() {
        trades.clear();
    }
}
