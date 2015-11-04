package ca.ualberta.cs.xpertsapp.UnitTests;

import com.google.gson.Gson;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.TradeStateAccepted;
import ca.ualberta.cs.xpertsapp.model.TradeStateDeclined;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class TradeTest extends TestCase {
	public TradeTest() {
		super();
	}

	private User friend1;
	private Service service1;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		String friend1String = "" +
				"{" +
				"\"contactInfo\":\"email1@u.ca\"," +
				"\"friends\":[]," +
				"\"id\":\"1\"," +
				"\"location\":\"British\"," +
				"\"name\":\"The Clown Guy\"," +
				"\"services\":[1]," +
				"\"trades\":[]" +
				"}";
		String service1String = "" +
				"{" +
				"\"category\":" +
				"{" +
				"\"name\":\"OTHER\"" +
				"}" +
				"," +
				"\"description\":\"\"," +
				"\"id\":\"1\"," +
				"\"name\":\"\"," +
				"\"owner\":\"1\"," +
				"\"pictures\":[]," +
				"\"shareable\":true" +
				"}";
		friend1 = (new Gson()).fromJson(friend1String, User.class);
		service1 = (new Gson()).fromJson(service1String, Service.class);
		IOManager.sharedManager().storeData(friend1, Constants.serverUserExtension() + friend1.getEmail());
		IOManager.sharedManager().storeData(service1, Constants.serverServiceExtension() + service1.getID());
		friend1 = UserManager.sharedManager().getUser(friend1.getEmail());
		service1 = ServiceManager.sharedManager().getService(service1.getID());
	}

	@Override
	protected void tearDown() throws Exception {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend1.getEmail());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + service1.getID());
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getEmail());

		super.tearDown();
	}

	// Also 04.05.01
	public void test_04_01_01() {
		// Test offer trade to friend
		User user = UserManager.sharedManager().localUser();
		Service newService = ServiceManager.sharedManager().newService();
		newService.setName("new service");
		user.addService(newService);

		user.addFriend(friend1);
		User myFriend = user.getFriends().get(0);


		// Create a new trade
		Trade newTrade = TradeManager.sharedManager().newTrade(myFriend, false);

		// Edit the trade
		newTrade.addOwnerService(newService);
		newTrade.addBorrowerService(service1);

		// Finalize it
		newTrade.commit();
		assertEquals(TradeManager.sharedManager().getTrades().size(), 1);
		assertEquals(user.getTrades().size(), 1);
		assertEquals(friend1.getTrades().size(), 1);

		TradeManager.sharedManager().clearCache();
		Trade oldTrade = TradeManager.sharedManager().getTrade(user.getTrades().get(0).getID());
		assertEquals(oldTrade.getID(), newTrade.getID());

		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + newService.getID());
		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + newTrade.getID());
	}

	public void test_04_02_01() {
		// Test get notified by a trade
		User user = UserManager.sharedManager().localUser();
		String tradeString = "" +
				"{" +
				"\"borrower\":\"" + user.getEmail() + "\"," +
				"\"borrowerServices\":[]," +
				"\"id\":\"0001\"," +
				"\"lastUpdatedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"owner\":\"1\"," +
				"\"ownerServices\":[]," +
				"\"proposedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"isCounterOffer\":false," +
				"\"status\":0" +
				"}";
		Trade trade = (new Gson()).fromJson(tradeString, Trade.class);
		IOManager.sharedManager().storeData(trade, Constants.serverTradeExtension() + trade.getID());
		trade = TradeManager.sharedManager().getTrade(trade.getID());
		trade.commit();

		assertEquals(user.newTrades(), 1);

		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + trade.getID());
	}

	public void test_04_03_01() {
		// Test accept and decline trades
		User user = UserManager.sharedManager().localUser();
		String trade1String = "" +
				"{" +
				"\"borrower\":\"" + user.getEmail() + "\"," +
				"\"borrowerServices\":[]," +
				"\"id\":\"0001\"," +
				"\"lastUpdatedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"owner\":\"1\"," +
				"\"ownerServices\":[]," +
				"\"proposedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"isCounterOffer\":false," +
				"\"status\":0" +
				"}";
		String trade2String = "" +
				"{" +
				"\"borrower\":\"" + user.getEmail() + "\"," +
				"\"borrowerServices\":[]," +
				"\"id\":\"0002\"," +
				"\"lastUpdatedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"owner\":\"1\"," +
				"\"ownerServices\":[]," +
				"\"proposedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"isCounterOffer\":false," +
				"\"status\":0" +
				"}";
		Trade trade1 = (new Gson()).fromJson(trade1String, Trade.class);
		Trade trade2 = (new Gson()).fromJson(trade2String, Trade.class);
		IOManager.sharedManager().storeData(trade1, Constants.serverTradeExtension() + trade1.getID());
		IOManager.sharedManager().storeData(trade2, Constants.serverTradeExtension() + trade2.getID());
		trade1 = TradeManager.sharedManager().getTrade(trade1.getID());
		trade2 = TradeManager.sharedManager().getTrade(trade2.getID());
		trade1.commit();
		trade2.commit();

		user.getTrades().get(0).accept();
		user.getTrades().get(1).decline();

		assertEquals(trade1.getState().getClass(), TradeStateAccepted.class);
		assertEquals(trade2.getState().getClass(), TradeStateDeclined.class);

		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + trade1.getID());
		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + trade2.getID());
	}

	// 04.04.01 is not model

	public void test_04_06_01() {
		// Test delete trade when composing
		User user = UserManager.sharedManager().localUser();
		Service newService = ServiceManager.sharedManager().newService();
		newService.setName("new service");
		user.addService(newService);

		user.addFriend(friend1);
		User myFriend = user.getFriends().get(0);


		// Create a new trade
		Trade newTrade = TradeManager.sharedManager().newTrade(myFriend, false);

		// Edit the trade
		newTrade.addOwnerService(newService);
		newTrade.addBorrowerService(service1);

		// Don't commit

		assertEquals(TradeManager.sharedManager().getTrades().size(), 0);
		assertEquals(user.getTrades().size(), 0);
		assertEquals(friend1.getTrades().size(), 0);

		TradeManager.sharedManager().clearCache();
		Trade oldTrade = TradeManager.sharedManager().getTrade(newTrade.getID());
		assertEquals(oldTrade, null);

		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + newService.getID());
	}

	// 04.07.01 is not model

	// Also 04_09_01
	public void test_04_08_01() {
		// Test browse past and current trades involving me
		User user = UserManager.sharedManager().localUser();
		String trade1String = "" +
				"{" +
				"\"borrower\":\"" + user.getEmail() + "\"," +
				"\"borrowerServices\":[]," +
				"\"id\":\"0001\"," +
				"\"lastUpdatedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"owner\":\"1\"," +
				"\"ownerServices\":[]," +
				"\"proposedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"isCounterOffer\":false," +
				"\"status\":1" +
				"}";
		String trade2String = "" +
				"{" +
				"\"borrower\":\"" + user.getEmail() + "\"," +
				"\"borrowerServices\":[]," +
				"\"id\":\"0002\"," +
				"\"lastUpdatedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"owner\":\"1\"," +
				"\"ownerServices\":[]," +
				"\"proposedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
				"\"isCounterOffer\":false," +
				"\"status\":0" +
				"}";
		Trade trade1 = (new Gson()).fromJson(trade1String, Trade.class);
		Trade trade2 = (new Gson()).fromJson(trade2String, Trade.class);
		IOManager.sharedManager().storeData(trade1, Constants.serverTradeExtension() + trade1.getID());
		IOManager.sharedManager().storeData(trade2, Constants.serverTradeExtension() + trade2.getID());
		trade1 = TradeManager.sharedManager().getTrade(trade1.getID());
		trade2 = TradeManager.sharedManager().getTrade(trade2.getID());
		trade1.commit();
		trade2.commit();

		assertEquals(user.getTrades().size(), 2);
		assertEquals(user.newTrades(), 1);

		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + trade1.getID());
		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + trade2.getID());
	}
}