package ca.ualberta.cs.xpertsapp.UnitTests;

import com.google.gson.Gson;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
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
		IOManager.sharedManager().storeData(friend1, Constants.serverUserExtension() + friend1.getID());
		IOManager.sharedManager().storeData(service1, Constants.serverServiceExtension() + service1.getID());
		friend1 = UserManager.sharedManager().getUser(friend1.getID());
	}

	@Override
	protected void tearDown() throws Exception {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend1.getID());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + service1.getID());
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getID());

		super.tearDown();
	}

	public void test_04_01_01() {
		// TODO: Test offer trade to friend
		User user = UserManager.sharedManager().localUser();
		Service newService = ServiceManager.sharedManager().newService();
		newService.setName("new service");
		user.addService(newService);

		user.addFriend(friend1.getID());
		User myFriend = user.getFriends().get(0);


		// Create a new trade
		Trade newTrade = TradeManager.sharedManager().newTrade(myFriend.getID(), false);
		newTrade.addOwnerService(newService.getID());
		newTrade.addBorrowerService(service1.getID());

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

//	public void test_04_02_01() {
//		// TODO: Test get notified by a trade
//		assertTrue(false);
//	}

//	public void test_04_03_01() {
//		// TODO: Test accept and decline trades
//		assertTrue(false);
//	}

//	public void test_04_04_01() {
//		// TODO: Test offer counter trade when declining
//		assertTrue(false);
//	}

//	public void test_04_05_01() {
//		// TODO: Test editing the trade when composing one
//		assertTrue(false);
//	}

//	public void test_04_06_01() {
//		// TODO: Test delete trade when composing
//		assertTrue(false);
//	}

//	public void test_04_07_01() {
//		// TODO: Test email both parties when accepting a trade
//		assertTrue(false);
//	}

	// Also 04_09_01
//	public void test_04_08_01() {
//		// TODO: Test browse past and current trades involving me
//		assertTrue(false);
//	}
}