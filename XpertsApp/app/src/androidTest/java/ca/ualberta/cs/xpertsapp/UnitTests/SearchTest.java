package ca.ualberta.cs.xpertsapp.UnitTests;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class SearchTest extends TestCase {
	public SearchTest() {
		super();
	}

	private User friend1;
	private Service service1;

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
				"\"services\":[1]," +
				"\"trades\":[]" +
				"}";
		String service1String = "" +
				"{" +
				"\"category\":" +
				"{" +
				"\"name\":\"OTHER\"" +
				"}" +
				"," +
				"\"description\":\"a description that doesn't say much\"," +
				"\"id\":\"1\"," +
				"\"name\":\"\"," +
				"\"owner\":\"1\"," +
				"\"pictures\":[]," +
				"\"shareable\":true" +
				"}";
		friend1 = (new Gson()).fromJson(friend1String, User.class);
		service1 = (new Gson()).fromJson(service1String, Service.class);
		IOManager.sharedManager().storeData(friend1, Constants.serverUserExtension() + friend1.getID());
		IOManager.sharedManager().storeData(service1, Constants.serverServiceExtension() + service1.getID());
		friend1 = UserManager.sharedManager().getUser(friend1.getID());
	}

	@Override
	protected void tearDown() throws Exception {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend1.getID());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + service1.getID());
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getID());

		super.tearDown();
	}

	// Also 03_01_01
	public void test_03_01_02() {
		// Test search inventory of friends by category
		User user = UserManager.sharedManager().localUser();
		user.addFriend(friend1.getID());

		List<Service> allOfFriendsServices = new ArrayList<Service>();
		for (User friend : user.getFriends()) {
			allOfFriendsServices.addAll(friend.getServices());
		}

		assertEquals(allOfFriendsServices.size(), 1);
	}

	// Also 03_01_01
	public void test_03_01_03() {
		// Test search inventory of friends by text
		User user = UserManager.sharedManager().localUser();
		user.addFriend(friend1.getID());

		List<Service> friendsServices = ServiceManager.sharedManager().findServicesOfFriends("descrip*");
		assertEquals(friendsServices.size(), 1);
		assertEquals(friendsServices.get(0).getID(), service1.getID());
	}

	// 03.02.01 covered by 01.03.02
}