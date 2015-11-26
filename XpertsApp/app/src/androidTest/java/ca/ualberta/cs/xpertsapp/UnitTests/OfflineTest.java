package ca.ualberta.cs.xpertsapp.UnitTests;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.SystemClock;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;
import ca.ualberta.cs.xpertsapp.model.es.SearchResponse;

public class OfflineTest extends TestCase {
	public OfflineTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + MyApplication.getLocalUser().toString());

		super.tearDown();
	}

	// Test add service while offline and push while online
	public void test_09_01_01() {
		// Disable internet
		Constants.isOnline = false;
		// Create a new service
		Service offlineService = ServiceManager.sharedManager().newService();
		offlineService.setName("Some new offline service");
		// Add service to server here not possible because isOnline = false
		User user = MyApplication.getLocalUser();
		user.addService(offlineService);

		// Enable online
		Constants.isOnline = true;
		// Automatically push
		IOManager.sharedManager().pushMe();

		SearchHit<Service> loadedService = IOManager.sharedManager().fetchData(Constants.serverServiceExtension() + offlineService.getID(), new TypeToken<SearchHit<Service>>() {});
		Service onlineService = loadedService.getSource();

		assertEquals(offlineService.getID(), onlineService.getID());

		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + offlineService.getID());
	}

	// Test add trade while offline and push while online
	public void test_09_02_01() {
		// Disable internet
		Constants.isOnline = false;
		// Create a new trade
		Trade offlineTrade = TradeManager.sharedManager().newTrade(newTestUser("testborrower@xperts.com"), false);
		//offlineTrade.setDescription("Some new offline trade");
		// Add trade offline
		User user = MyApplication.getLocalUser();
		//user.addTrade(offlineTrade);

		// Enable online
		Constants.isOnline = true;
		// Automatically push
		IOManager.sharedManager().pushMe();

		SearchHit<Trade> loadedTrade = IOManager.sharedManager().fetchData(Constants.serverTradeExtension() + offlineTrade.getID(), new TypeToken<SearchHit<Trade>>() {});
		Trade onlineTrade = loadedTrade.getSource();

		assertEquals(offlineTrade.getID(), onlineTrade.getID());

		IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + offlineTrade.getID());
	}

	public void test_09_03_01() {
		// TODO: Test cache friends and servies for offline use
		assertTrue(false);
	}
}