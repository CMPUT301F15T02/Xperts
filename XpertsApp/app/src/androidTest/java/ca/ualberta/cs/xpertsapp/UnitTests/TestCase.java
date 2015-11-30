package ca.ualberta.cs.xpertsapp.UnitTests;

import android.app.Activity;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

public class TestCase extends ActivityInstrumentationTestCase2 {
	public TestCase() {
		super(MainActivity.class);
	}

	public TestCase(Class c) {
		super(c);
	}

	static final String testLocalEmail =  Constants.testEmail;
	protected static SharedPreferences pref;

	protected void setUp2() {
		Constants.isTest = true;
		Constants.isOnline = true;

		IOManager.sharedManager().clearCache();

		// Cleanup the last test
		UserManager.sharedManager().clearCache();
		ServiceManager.sharedManager().clearCache();
		TradeManager.sharedManager().clearCache();

		IOManager.sharedManager().deleteData(Constants.serverUserExtension());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension());
		IOManager.sharedManager().deleteData(Constants.serverTradeExtension());
		IOManager.sharedManager().deleteData(Constants.serverImageExtension());

		IOManager.sharedManager().clearCache();
		
	}

	protected void tearDown2() {
		// Cleanup
		IOManager.sharedManager().clearCache();

		UserManager.sharedManager().clearCache();
		ServiceManager.sharedManager().clearCache();
		TradeManager.sharedManager().clearCache();

		IOManager.sharedManager().deleteData(Constants.serverUserExtension());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension());
		IOManager.sharedManager().deleteData(Constants.serverTradeExtension());
		IOManager.sharedManager().deleteData(Constants.serverImageExtension());

		IOManager.sharedManager().clearCache();

		Constants.isTest = false;
	}

	protected User newTestUser(String email) {
		return UserManager.sharedManager().registerUser(email);
	}

	protected User newTestUser(String email, String name, String location) {
		User testUser = UserManager.sharedManager().registerUser(email);
		testUser.setName(name);
		testUser.setLocation(location);
		return testUser;
	}

	protected Service newTestService(String name, String desc, int categoryNumber, boolean shareable) {
		Service testService = ServiceManager.sharedManager().newService();
		testService.setName(name);
		testService.setDescription(desc);
		testService.setCategory(CategoryList.sharedCategoryList().getCategories().get(categoryNumber));
		testService.setShareable(shareable);
		return testService;
	}


}