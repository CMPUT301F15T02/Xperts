package ca.ualberta.cs.xpertsapp.UnitTests;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.controllers.FriendsListAdapter;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.FriendsActivity;

public class FriendsTest extends TestCase {
	public FriendsTest() {
		super(FriendsActivity.class);
	}

	final String testEmail1 = "david@xperts.com";
	final String testEmail2 = "seann@xperts.com";
	final String testEmail3 = "kathleen@xperts.com";
	final String testEmail4 = "huy@xperts.com";
	final String testEmail5 = "justin@xperts.com";
	final String testEmail6 = "hammad@xperts.com";
	private User u1;
	private User u2;
	private User u3;

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

	public void test_02_01_01() {
		// Test track user by searching for username
		UserManager.sharedManager().clearCache();
		User user = MyApplication.getLocalUser();
		assertEquals(user.getEmail(), testLocalEmail);
		final String friendSearchString = "kathleen@xperts.com";

		FriendsActivity mActivity = (FriendsActivity) getActivity();
		final Button buttonAddFriend = mActivity.getButtonAddFriend();
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				buttonAddFriend.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();

		final AlertDialog alertDialog = mActivity.getAlertDialog();
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				alertDialog.show();
			}
		});
		getInstrumentation().waitForIdleSync();

		final EditText editTextEmail = mActivity.getEditTextEmail();
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				editTextEmail.setText(friendSearchString);
				Button buttonDialog = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				buttonDialog.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();

		FriendsListAdapter friendsListAdapter = mActivity.getFriendsListAdapter();
		assertTrue(friendsListAdapter.getCount() > 0);
	}

	public void test_02_02_01() {
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
		int numFriends = user.getFriends().size();
		user.addFriend(u1);
		user.addFriend(u2);

		assertEquals(user.getFriends().size(), numFriends + 2);

		assertTrue(user.getFriends().contains(u1));
		user.removeFriend(u1);
		assertFalse(user.getFriends().contains(u1));
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
