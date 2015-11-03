package ca.ualberta.cs.xpertsapp.UnitTests;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class FriendsTest extends TestCase {
	public FriendsTest() {
		super();
	}

	private User friend1;
	private User friend2;

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
				"\"services\":[]," +
				"\"trades\":[]" +
				"}";
		String friend2String = "" +
				"{" +
				"\"contactInfo\":\"Don't contact me pl0x.\"," +
				"\"friends\":[]," +
				"\"id\":\"2\"," +
				"\"location\":\"Canada\"," +
				"\"name\":\"Ronald\"," +
				"\"services\":[]," +
				"\"trades\":[]" +
				"}";
		friend1 = (new Gson()).fromJson(friend1String, User.class);
		friend2 = (new Gson()).fromJson(friend2String, User.class);
		IOManager.sharedManager().storeData(friend1, Constants.serverUserExtension() + friend1.getID());
		IOManager.sharedManager().storeData(friend2, Constants.serverUserExtension() + friend2.getID());
		friend1 = UserManager.sharedManager().getUser(friend1.getID());
		friend2 = UserManager.sharedManager().getUser(friend2.getID());
	}

	@Override
	protected void tearDown() throws Exception {
		// Cleanup
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend1.getID());
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend2.getID());
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getID());

		super.tearDown();
	}

	// Also 02.02.01
	public void test_02_01_01() {
		// Test add friends by searching for username
		User user = UserManager.sharedManager().localUser();
		String friendSearchString = "name:Ron*";
		List<User> results = UserManager.sharedManager().findUsers(friendSearchString);
		assertEquals(results.size(), 1);

		User soonFriend = results.get(0);
		assertEquals(soonFriend, friend2);

		user.addFriend(soonFriend.getID());
		assertEquals(user.getFriends().size(), 1);
		assertEquals(UserManager.sharedManager().getUser(user.getFriends().get(0).getID()), friend2);
	}

	public void test_02_03_01() {
		// Test remove friends
		User user = UserManager.sharedManager().localUser();
		user.addFriend(friend1.getID());
		user.addFriend(friend2.getID());
		assertEquals(user.getFriends().size(), 2);

		user.removeFriend(friend1.getID());
		assertEquals(user.getFriends().size(), 1);
		assertEquals(user.getFriends().get(0), friend2);
	}

	public void test_02_04_01() {
		// Test set contact info and location
		User user = UserManager.sharedManager().localUser();

		String newName = "Skrundz";
		String newContactInfo = "somenewContactInfo";
		String newLocation = "CANADA";

		user.setContactInfo(newContactInfo);
		user.setLocation(newLocation);
		user.setName(newName);

		UserManager.sharedManager().clearCache();
		user = UserManager.sharedManager().localUser();

		assertEquals(user.getContactInfo(), newContactInfo);
		assertEquals(user.getLocation(), newLocation);
		assertEquals(user.getName(), newName);

		assertEquals(UserManager.sharedManager().getUsers().size(), 1);
	}

	// 02.05.01 is not model
}