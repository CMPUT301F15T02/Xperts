package ca.ualberta.cs.xpertsapp.UnitTests;

import junit.framework.TestCase;

import ca.ualberta.cs.xpertsapp.models.Category;
import ca.ualberta.cs.xpertsapp.models.Service;
import ca.ualberta.cs.xpertsapp.models.Trade;
import ca.ualberta.cs.xpertsapp.models.TradeStateAccepted;
import ca.ualberta.cs.xpertsapp.models.TradeStateDeclined;
import ca.ualberta.cs.xpertsapp.models.TradeStatePending;
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
        User owner = new User("Harry Potter", "hp@ualberta.ca");
        Service ownerService = new Service("12","magic","do some magic",(Category)null,Boolean.TRUE,owner);
        trade.addOwnerService(ownerService);
        assertTrue("service not right", trade.getOwnerServices().contains(ownerService));
        assertEquals(1,trade.getOwnerServices().size());
    }

    public void testRemoveOwnerService() {
        String id = "1";
        Boolean isCounter = Boolean.FALSE;
        Trade trade = new Trade(id, isCounter);
        User owner = new User("Harry Potter", "hp@ualberta.ca");
        Service ownerService = new Service("12","magic","do some magic",(Category)null,Boolean.TRUE,owner);
        trade.addOwnerService(ownerService);
        assertTrue("service not right", trade.getOwnerServices().contains(ownerService));
        assertEquals(1, trade.getOwnerServices().size());
        trade.removeOwnerService(ownerService);
        assertFalse("service not right", trade.getOwnerServices().contains(ownerService));
        assertEquals(0, trade.getOwnerServices().size());
        trade.removeOwnerService(ownerService);
        assertEquals(0, trade.getOwnerServices().size());
    }

    public void testAddBorrowerService() {
        String id = "1";
        Boolean isCounter = Boolean.FALSE;
        Trade trade = new Trade(id, isCounter);
        User borrower = new User("Harry Potter", "hp@ualberta.ca");
        Service borrowerService = new Service("12","magic","do some magic",(Category)null,Boolean.TRUE,borrower);
        trade.addBorrowerService(borrowerService);
        assertTrue("service not right", trade.getBorrowerServices().contains(borrowerService));
        assertEquals(1,trade.getBorrowerServices().size());
    }

    public void testRemoveBorrowerService() {
        String id = "1";
        Boolean isCounter = Boolean.FALSE;
        Trade trade = new Trade(id, isCounter);
        User borrower = new User("Harry Potter", "hp@ualberta.ca");
        Service borrowerService = new Service("12","magic","do some magic",(Category)null,Boolean.TRUE,borrower);
        trade.addBorrowerService(borrowerService);
        assertTrue("service not right", trade.getBorrowerServices().contains(borrowerService));
        assertEquals(1,trade.getBorrowerServices().size());
        trade.removeBorrowerService(borrowerService);
        assertFalse("service not right", trade.getOwnerServices().contains(borrowerService));
        assertEquals(0, trade.getBorrowerServices().size());
        trade.removeBorrowerService(borrowerService);
        assertEquals(0, trade.getBorrowerServices().size());
    }

    public void testTradeStatus() {
        String id = "1";
        Boolean isCounter = Boolean.FALSE;
        Trade trade = new Trade(id, isCounter);
        assertTrue("Trade state not pending", (trade.getTradeState() instanceof TradeStatePending));
        trade.accept();
        assertTrue("Trade state not accepted", (trade.getTradeState() instanceof TradeStateAccepted));
    }

    //test for isEditable()

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }
}