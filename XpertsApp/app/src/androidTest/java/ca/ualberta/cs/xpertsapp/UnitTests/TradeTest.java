package ca.ualberta.cs.xpertsapp.UnitTests;

import junit.framework.TestCase;

import ca.ualberta.cs.xpertsapp.models.Service;
import ca.ualberta.cs.xpertsapp.models.Trade;
import ca.ualberta.cs.xpertsapp.models.User;

/**
 * Created by hammadjutt on 2015-10-29.
 */
public class TradeTest extends TestCase {

    public void testCreateTrade() {
        String id = "1";
        Boolean isCounter = Boolean.FALSE;
        Trade trade = new Trade(id, isCounter);
        assertTrue("id is not equal", id.equals(trade.getId()));
        assertTrue("isCounter not equal", isCounter.equals(trade.isCounterOffer()));
    }

    public void testAddOwnerService() {
        String id = "1";
        Boolean isCounter = Boolean.FALSE;
        Trade trade = new Trade(id, isCounter);
        Service ownerService = new Service();

    }

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }
}