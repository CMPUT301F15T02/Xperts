package ca.ualberta.cs.xpertsapp.UnitTests;

import android.content.Context;
import android.net.wifi.WifiManager;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

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

	public void test_09_01_01() {
		// Test add service while offline and push while online
		// Disable internet
		Constants.allowOnline = false;
		// Create a new service
		Service newService = ServiceManager.sharedManager().newService();
		newService.setName("Some new offline service");
		User user = MyApplication.getLocalUser();
		user.addService(newService);

		// Enable online
		Constants.allowOnline = true;
		ServiceManager.sharedManager().clearCache();
		Service nullService = ServiceManager.sharedManager().getService(newService.getID());
		assertEquals(null, nullService);

		// Push the changes
//		IOManager.sharedManager().pushChanges();
		Service theService = ServiceManager.sharedManager().getService(newService.getID());

		assertEquals(newService, theService);

		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + newService.getID());
	}

	public void test_09_02_01() {
		// TODO: Test make trade while offline and push while online
		assertTrue(false);
	}

	public void test_09_03_01() {
		// TODO: Test cache friends and servies for offline use
		assertTrue(false);
	}
}