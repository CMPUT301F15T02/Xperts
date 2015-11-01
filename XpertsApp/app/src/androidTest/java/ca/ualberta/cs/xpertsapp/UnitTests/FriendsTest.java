package ca.ualberta.cs.xpertsapp.UnitTests;

import android.test.ActivityInstrumentationTestCase2;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

public class FriendsTest extends ActivityInstrumentationTestCase2 {
	public FriendsTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// Prepare some friends
		String friend1String = "" +
				"{" +
				"\"contactInfo\":\"email1@u.ca\"," +
				"\"friends\":[]," +
				"\"id\":\"3347b479c962c3fc\"," +
				"\"location\":\"British\"," +
				"\"name\":\"The Clown Guy\"," +
				"\"services\":[]," +
				"\"trades\":[]" +
				"}";
		String friend2String = "" +
				"{" +
				"\"contactInfo\":\"Don't contact me pl0x.\"," +
				"\"friends\":[]," +
				"\"id\":\"3347b479c962c3fc\"," +
				"\"location\":\"Canada\"," +
				"\"name\":\"\"," +
				"\"services\":[]," +
				"\"trades\":[]" +
				"}";
		User friend1 = (new Gson()).fromJson(friend1String, User.class);
		User friend2 = (new Gson()).fromJson(friend2String, User.class);
		IOManager.sharedManager().storeData(friend1, Constants.serverUserExtension() + friend1.getID());
		IOManager.sharedManager().storeData(friend2, Constants.serverUserExtension() + friend2.getID());
	}

	// Also 02.02.01
	public void test_02_01_01() {
		// TODO: Test add friends by searching for username
		assertTrue(false);

		User user = UserManager.sharedManager().localUser();
		String friendSearchString = "";
		List<User> friend = UserManager.sharedManager().findUsers(friendSearchString);

//		User f = new User();
	}

	public void test_02_03_01() {
		// TODO: Test remove friends
		assertTrue(false);
	}

	public void test_02_04_01() {
		// Test set contact info and location
		User user = UserManager.sharedManager().localUser();

		String newContactInfo = "somenewContactInfo";
		String newLocation = "CANADA";

		user.setContactInfo(newContactInfo);
		user.setLocation(newLocation);

		assertEquals(user.getContactInfo(), newContactInfo);
		assertEquals(user.getLocation(), newLocation);
	}

	// 02.05.01 is not model
}