package ca.ualberta.cs.xpertsapp.UnitTests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Switch;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.EditProfileActivity;

public class ConfigTest extends ActivityInstrumentationTestCase2 {
	public ConfigTest() {
		super(EditProfileActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + MyApplication.getLocalUser().getEmail());

		super.tearDown();
	}

	public void test_10_01_01() {
		// Test toggle image download
		final EditProfileActivity mActivity = (EditProfileActivity) getActivity();
		final Switch switch1 = mActivity.getSwitch1();
		final Boolean toggleEnabled = switch1.isChecked();

		User user = MyApplication.getLocalUser();

		String newName = "Some user's name";
		String newLocation = "canada";
		String newContact = "2390832490838042";
		user.setName(newName);
		user.setLocation(newLocation);
		user.setToggleEnabled(false);

		// Toggle switch
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (toggleEnabled) {
					switch1.setChecked(false);
				} else {
					switch1.setChecked(true);
				}
			}
		});

		getInstrumentation().waitForIdleSync();

		//UserManager.sharedManager().clearCache();
		user = MyApplication.getLocalUser();
		if (toggleEnabled) {
			assertFalse(user.getToggleEnabled());
		} else {
			assertTrue(user.getToggleEnabled());
		}
	}

	public void test_10_02_01() {
		// Test edit profile
		User user = MyApplication.getLocalUser();

		String newName = "Some user's name";
		String newLocation = "canada";
		String newContact = "2390832490838042";
		user.setName(newName);
		user.setLocation(newLocation);

		UserManager.sharedManager().clearCache();
		user = MyApplication.getLocalUser();
		assertEquals(user.getName(), newName);
		assertEquals(user.getLocation(), newLocation);
	}
}