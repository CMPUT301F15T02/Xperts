package ca.ualberta.cs.xpertsapp.UnitTests;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class FriendsTest extends TestCase {
	public FriendsTest() {
		super();
	}

	final String testEmail1 = "david@xperts.com";
	final String testEmail2 = "seann@xperts.com";
	final String testEmail3 = "kathleen@xperts.com";
	final String testEmail4 = "huy@xperts.com";
	final String testEmail5 = "justin@xperts.com";
	final String testEmail6 = "hammad@xperts.com";
	User u1;
	User u2;
	User u3;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		u1 = newTestUser(testEmail1,"David Skrundz","Calgary");
		u2 = newTestUser(testEmail2,"Seann Murdock","Vancouver");
		u3 = newTestUser(testEmail3,"Kathleen Baker","Toronto");

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// Also 02.02.01
	public void test_02_01_01() {
		// Test add friends by searching for username
		User user = MyApplication.getLocalUser();
		assertEquals(user.getEmail(), testLocalEmail);
		String friendSearchString = "kathleen";
		List<User> results = UserManager.sharedManager().findUsers(friendSearchString);
		assertEquals(results.size(), 1);

		User soonFriend = results.get(0);
		assertEquals(soonFriend.getName(), "Kathleen Baker");
		assertEquals(soonFriend.getEmail(), "kathleen@xperts.com");
		assertEquals(soonFriend.getLocation(), "Toronto");
		assertEquals(soonFriend.getFriends().size(), 0);
		assertEquals(soonFriend.getServices().size(), 0);
		assertEquals(soonFriend.getTrades().size(), 0);
		assertEquals(soonFriend, u3);

		user.addFriend(soonFriend);
		assertEquals(user.getFriends().size(), 1);
		assertEquals(UserManager.sharedManager().getUser(user.getFriends().get(0).getEmail()), u3);

	}

	public void test_02_03_01() {
		// Test remove friends
		User user = MyApplication.getLocalUser();
		user.addFriend(u1);
		user.addFriend(u2);

		assertEquals(user.getFriends().size(), 2);

		user.removeFriend(u1);
		assertEquals(user.getFriends().size(), 1);
		assertEquals(user.getFriends().get(0), u2);
	}

	public void test_02_04_01() {
		// Test set contact info and location
		User user = MyApplication.getLocalUser();

		String name = "Polar bear";
		String location = "Nunavut";

		user.setLocation(location);
		user.setName(name);

		//UserManager.sharedManager().clearCache();
		//user = MyApplication.getLocalUser();

		assertEquals(user.getLocation(), location);
		assertEquals(user.getName(), name);

		//assertEquals(UserManager.sharedManager().getUsers().size(), 4);
	}

	// 02.05.01 is not model
}
