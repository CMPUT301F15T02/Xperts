package ca.ualberta.cs.xpertsapp.UnitTests;

import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

public class TestCase extends ActivityInstrumentationTestCase2 {
	public TestCase() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// Prepare
		Constants.isTest = true;

		MyApplication.login("test@email.com");

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// Cleanup
		UserManager.sharedManager().clearCache();
		ServiceManager.sharedManager().clearCache();
		TradeManager.sharedManager().clearCache();

		IOManager.sharedManager().deleteData(Constants.serverUserExtension());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension());
		IOManager.sharedManager().deleteData(Constants.serverTradeExtension());

		Constants.isTest = false;

		super.tearDown();
	}
}