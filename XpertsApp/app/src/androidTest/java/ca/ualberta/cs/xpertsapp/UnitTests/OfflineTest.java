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
	protected void setUp2() {
		super.setUp2();
	}

	@Override
	protected void tearDown2(){
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + MyApplication.getLocalUser().toString());

		super.tearDown2();
	}

	public void test_09_01_01() {
		setUp2();

		

		tearDown2();
	}

	public void test_09_02_01() {
		setUp2();

		// TODO: Test make trade while offline and push while online
		assertTrue(false);

		tearDown2();
	}

	public void test_09_03_01() {
		setUp2();

		// TODO: Test cache friends and servies for offline use
		assertTrue(false);

		tearDown2();
	}
}