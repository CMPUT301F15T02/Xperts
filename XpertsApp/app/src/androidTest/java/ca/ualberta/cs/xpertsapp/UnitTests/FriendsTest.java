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

		friend1 = UserManager.sharedManager().registerUser("email1@u.ca");
		friend2 = UserManager.sharedManager().registerUser("email2@u.ca");
	}

	@Override
	protected void tearDown() throws Exception {
		// Cleanup
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend1.getEmail());
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend2.getEmail());
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getEmail());

		super.tearDown();
	}

	// Also 02.02.01
	public void test_02_01_01() {
		// Test add friends by searching for username
		User user = UserManager.sharedManager().localUser();
		String friendSearchString = "email1*";
		List<User> results = UserManager.sharedManager().findUsers(friendSearchString);
		assertEquals(results.size(), 1);

		User soonFriend = results.get(0);
		assertEquals(soonFriend, friend2);

		user.addFriend(soonFriend);
		assertEquals(user.getFriends().size(), 1);
		assertEquals(UserManager.sharedManager().getUser(user.getFriends().get(0).getEmail()), friend2);
	}

	public void test_02_03_01() {
		// Test remove friends
		User user = UserManager.sharedManager().localUser();
		user.addFriend(friend1);
		user.addFriend(friend2);
		assertEquals(user.getFriends().size(), 2);

		user.removeFriend(friend1);
		assertEquals(user.getFriends().size(), 1);
		assertEquals(user.getFriends().get(0), friend2);
	}

	public void test_02_04_01() {
		// Test set contact info and location
		User user = UserManager.sharedManager().localUser();

		String newName = "Skrundz";
		String newLocation = "CANADA";

		user.setLocation(newLocation);
		user.setName(newName);

		UserManager.sharedManager().clearCache();
		user = UserManager.sharedManager().localUser();

		assertEquals(user.getLocation(), newLocation);
		assertEquals(user.getName(), newName);

		assertEquals(UserManager.sharedManager().getUsers().size(), 1);
	}

	// 02.05.01 is not model
}