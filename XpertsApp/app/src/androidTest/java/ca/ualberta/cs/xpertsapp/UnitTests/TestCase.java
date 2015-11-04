package ca.ualberta.cs.xpertsapp.UnitTests;

import android.content.SharedPreferences;
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

	final String testEmail = "test@email.com";
	private static SharedPreferences pref;
	@Override
	protected void setUp() throws Exception {
		// Prepare
		Constants.isTest = true;

		pref = MyApplication.getContext().getSharedPreferences(Constants.PREF_FILE, 0);
		pref.edit().putString(Constants.EMAIL_KEY, testEmail);
		pref.edit().putBoolean(Constants.LOGGED_IN, true);
		pref.edit().apply();
		UserManager.sharedManager().registerUser(testEmail);

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

		pref.edit().clear();
		pref.edit().apply();

		Constants.isTest = false;

		super.tearDown();
	}
}