package ca.ualberta.cs.xpertsapp.UnitTests;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;

public class OfflineTest extends TestCase {
	public OfflineTest() {
		super();
	}

	@Override
	protected void setUp2() {
		super.setUp2();
	}

	@Override
	protected void tearDown2(){
		super.tearDown2();
	}

	public void test_09_01_01() {
		setUp2();

		// Disable internet
		Constants.allowOnline = false;
		// Create a new service
		Service offlineService = ServiceManager.sharedManager().newService();
		offlineService.setName("Some new offline service");
		// Add service
		User user = MyApplication.getLocalUser();
		user.addService(offlineService);

		// Enable online, automatically push
		Constants.allowOnline = true;

		SearchHit<Service> loadedService = IOManager.sharedManager().fetchData(Constants.serverServiceExtension() + offlineService.getID(), new TypeToken<SearchHit<Service>>() {});
		Service onlineService = loadedService.getSource();

		assertEquals(offlineService.getID(), onlineService.getID());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + offlineService.getID());

		tearDown2();
	}

	public void test_09_02_01() {
		setUp2();

		// Disable internet
		Constants.allowOnline = false;
		// Create a new trade
		Trade offlineTrade = TradeManager.sharedManager().newTrade(newTestUser("testborrower@xperts.com"), false);
		// Add trade offline
		User user = MyApplication.getLocalUser();
		user.addTrade(offlineTrade);

		// Enable online & automatically push
		Constants.allowOnline = true;

		SearchHit<Trade> loadedTrade = IOManager.sharedManager().fetchData(Constants.serverTradeExtension() + offlineTrade.getID(), new TypeToken<SearchHit<Trade>>() {});
		Trade onlineTrade = loadedTrade.getSource();

		assertEquals(offlineTrade.getID(), onlineTrade.getID());

		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + offlineTrade.getID());

		tearDown2();
	}

	// Test cache friends and services for offline use
	public void test_09_03_01() {
		setUp2();

		User user = MyApplication.getLocalUser();

		Service offlineService = ServiceManager.sharedManager().newService();
		user.addService(offlineService);
		user.addFriend(newTestUser("testborrower@xperts.com"));

		// Read service from cache
		ArrayList<Service> offlineServices = IOManager.sharedManager().loadFromFile(MyApplication.getContext(), new TypeToken<ArrayList<Service>>() {
		}, Constants.diskService);
		for (Service service : offlineServices) {
			if (service.getID().equals(offlineService.getID())) {
				assertEquals(service.getID(), offlineService.getID());
				break;
			}
		}

		// Read friend from cache
		User friend = IOManager.sharedManager().loadUserFromFile("testborrower@xperts.com");
		assertEquals(friend.getEmail(), "testborrower@xperts.com");

		tearDown2();
	}
}