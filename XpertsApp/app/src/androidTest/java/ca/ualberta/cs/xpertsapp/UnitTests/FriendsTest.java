package ca.ualberta.cs.xpertsapp.UnitTests;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.controllers.FriendsListAdapter;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.FriendsActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;
import ca.ualberta.cs.xpertsapp.views.ViewProfileActivity;

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

		MainActivity mActivity = (MainActivity) getActivity();
		final Button buttonViewFriends = mActivity.getFriendsBtn();

		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				buttonViewFriends.performClick();
			}
		});

		// Load the next activity, it may fail here if 5000 is not long enough
		Instrumentation.ActivityMonitor receiverActivityMonitor =
				getInstrumentation().addMonitor(FriendsActivity.class.getName(), null, false);
		FriendsActivity receiverActivity = (FriendsActivity)
				receiverActivityMonitor.waitForActivityWithTimeout(5000);
		assertNotNull(receiverActivity);

		final Button buttonAddFriend = receiverActivity.getButtonAddFriend();
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				buttonAddFriend.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();

		final AlertDialog alertDialog = receiverActivity.getAlertDialog();
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				alertDialog.show();
			}
		});
		getInstrumentation().waitForIdleSync();

		final EditText editTextEmail = receiverActivity.getEditTextEmail();
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				editTextEmail.setText(friendSearchString);
				Button buttonDialog = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				buttonDialog.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();

		FriendsListAdapter friendsListAdapter = receiverActivity.getFriendsListAdapter();
		assertTrue(friendsListAdapter.getCount() > 0);

		// Remove the ActivityMonitor
		getInstrumentation().removeMonitor(receiverActivityMonitor);
		// end of test, make sure edit activity is close
		receiverActivity.finish();
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
		MainActivity mActivity = (MainActivity) getActivity();
		final Button buttonMyProfile = mActivity.getMyProfileBtn();

		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				buttonMyProfile.performClick();
			}
		});

		// Load the next activity, it may fail here if 5000 is not long enough
		Instrumentation.ActivityMonitor receiverActivityMonitor =
				getInstrumentation().addMonitor(ViewProfileActivity.class.getName(), null, false);
		ViewProfileActivity receiverActivity = (ViewProfileActivity)
				receiverActivityMonitor.waitForActivityWithTimeout(5000);

		TextView name = receiverActivity.getName();
		TextView email = receiverActivity.getEmail();
		TextView location = receiverActivity.getLocation();

		User user = MyApplication.getLocalUser();

		assertEquals(user.getName(), name.getText().toString());
		assertEquals(user.getEmail(), email.getText().toString());
		assertEquals(user.getLocation(), location.getText().toString());

		// Remove the ActivityMonitor
		getInstrumentation().removeMonitor(receiverActivityMonitor);
		// end of test, make sure edit activity is close
		receiverActivity.finish();
	}

	// 02.05.01 is not model
}
