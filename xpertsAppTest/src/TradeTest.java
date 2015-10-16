import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by kmbaker on 10/8/15.
 */
public class TradeTest extends ActivityInstrumentationTestCase2 {
    public TradeTest() {
        super(com.ualberta.kmbaker.project.MainActivity.class);
    }

   public void testOfferTrade() {
        Trade trade = new Trade("Trade name");
        //trade plumbing (borrower service) for a hair cut (owner service)
        Service service1 = new Service("plumbing");
        Service service2 = new Service("haircut");
        trade.setTrade(service1, service2);
		assertNotNull(trade);
    }

	public void testNotifyOwner() {
	Trade trade = new Trade("Trade name");
        //trade plumbing (borrower service) for a hair cut (owner service)
        Service service1 = new Service("plumbing");
        Service service2 = new Service("haircut");
        trade.setTrade(service1, service2);
        User owner = new User();
        trade.sendNotification(owner);
		assertTrue(owner.hasNotification());
	}

    public void testAcceptTrade() {
        Trade trade = new Trade("Trade name");
        //trade plumbing (borrower service) for a hair cut (owner service)
        Service service1 = new Service("plumbing");
        Service service2 = new Service("haircut");
        trade.setTrade(service1, service2);
        User owner = new User();
        trade.sendNotification(owner);
        owner.acceptTrade(trade);
        assertTrue(owner.getAcceptedTrades(trade).contains(trade));
    }

    public void testDeclineTrade() {
        Trade trade = new Trade("Trade name");
        Service service1 = new Service("plumbing");
        Service service2 = new Service("haircut");
        trade.setTrade(service1, service2);
        User owner = new User();
        trade.sendNotification(owner);
        owner.declineTrade(trade);
        assertTrue(owner.getDeclinedTrades().contains(trade));
    }

    public void testOfferCounterTrade() {
        Trade trade = new Trade("Trade name");
        Service service1 = new Service("plumbing");
        Service service2 = new Service("haircut");
        trade.setTrade(service1, service2);
        User owner = new User();
        trade.sendNotification(owner);
        owner.declineTrade(trade);
        owner.offerCounterTrade(trade);
        //adds lawn mowing to plumbing (borrower's services)
        Service service3 = new Service("lawn mowing");
        trade.addBorrowerService(service3);
        assertTrue(owner.getCounteredTrades().contains(trade));
        assertEquals(trade.hasBorrowerService(service1), true);
        assertEquals(trade.hasBorrowerService(service2), true);
        assertEquals(trade.hasOwnerService(service3), true);
    }

    public void testEditTrade() {
        Trade trade = new Trade("Trade name");
        Service service1 = new Service("plumbing");
        Service service2 = new Service("haircut");
        trade.setTrade(service1, service2);
        Service service3 = new Service("calligraphy");
        Service service4 = new Service("math tutoring");
        trade.addBorrowerService(service3);
        trade.addOwnerService(service4);
        assertEquals(trade.hasBorrowerService(service3), true);
        assertEquals(trade.hasOwnerService(service4), true);
    }

    public void testDeleteTrade() {
        Trade trade = new Trade("Trade name");
        Service service1 = new Service("plumbing");
        Service service2 = new Service("haircut");
        trade.setTrade(service1, service2);
        trade.deleteTrade();
        assertNull(trade);
    }

    public void testNotifyOfAcceptedTrade() {
        Trade trade = new Trade("Trade name");
        //trade plumbing (borrower service) for a hair cut (owner service)
        Service service1 = new Service("plumbing");
        Service service2 = new Service("haircut");
        trade.setTrade(service1, service2);
        User owner = new User();
        User borrower = new User();
        borrower.createOffer(trade);
        owner.acceptTrade(trade);
        /* test that email sent to both owner and borrower and owner adds comments on how to continue */
        assertEquals(owner.gotEmail(), true);
        assertEquals(borrower.gotEmail(), true);
    }

    public void testBrowseMyTradesAsOwner() {
        User owner = new User();
        assertNotNull(owner.browseOffers());
    }

    public void testBrowseMyTradesAsBorrower() {
        User borrower = new User();
        assertNotNull(borrower.browseTrades());
    }
}
