package ca.ualberta.cs.xpertsapp.UnitTests;

import junit.framework.TestCase;

import ca.ualberta.cs.xpertsapp.datamanagers.TradeManager;
import ca.ualberta.cs.xpertsapp.models.Trade;

/**
 * Created by hammadjutt on 2015-10-29.
 */
public class TradeManagerTest extends TestCase {
    public void testTradeManagerSingleton() {
        TradeManager tm = null;
        tm = TradeManager.sharedManager();
        TradeManager tm2 = null;
        tm2 = TradeManager.sharedManager();
        assertEquals(tm, tm2);
        Trade trade = new Trade("2", Boolean.FALSE);
        tm.addTrade(trade);
        assertEquals(tm.getTrade("2"), trade);
        assertEquals(tm2.getTrade("2"), trade);
        tm.clearCache();
        assertEquals(true, tm.getTrades().isEmpty());
        assertEquals(true, tm2.getTrades().isEmpty());
    }
}