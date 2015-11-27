package ca.ualberta.cs.xpertsapp.UnitTests;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.EditProfileActivity;

public class ConfigTest extends TestCase {
	public ConfigTest() {
		super(EditProfileActivity.class);
	}

	@Override
	protected void setUp2() {
		super.setUp2();
	}

	@Override
	protected void tearDown2() {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + MyApplication.getLocalUser().getEmail());

		super.tearDown2();
	}

	public void test_10_01_01() {
		setUp2();
		// Test toggle image download
		EditProfileActivity mActivity = (EditProfileActivity) getActivity();
		final Switch switch1 = mActivity.getSwitch1();
		final Boolean toggleEnabled = switch1.isChecked();

		User user = MyApplication.getLocalUser();

		String newName = "Some user's name";
		String newLocation = "canada";
		String newContact = "2390832490838042";
		user.setName(newName);
		user.setLocation(newLocation);
		user.setDownloadsEnabled(false);

		Log.i("", "PART 1");

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

		Log.i("", "PART 2");

		getInstrumentation().waitForIdleSync();

		//UserManager.sharedManager().clearCache();
		user = MyApplication.getLocalUser();
		if (toggleEnabled) {
			assertFalse(user.getDownloadsEnabled());
		} else {
			assertTrue(user.getDownloadsEnabled());
		}

		Log.i("", "PART 3");

		tearDown2();
	}

	public void test_10_02_01() {
		setUp2();
		// Test edit profile
		EditProfileActivity mActivity = (EditProfileActivity) getActivity();
		final EditText email = mActivity.getEmail();
		final EditText name = mActivity.getName();
		final EditText location = mActivity.getLocation();
		final Button saveButton = mActivity.getSaveButton();

		final String newName = "Some user's name";
		final String newLocation = "canada";
		String newContact = "2390832490838042";

		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				name.setText(newName);
				location.setText(newLocation);
				saveButton.performClick();
			}
		});

		getInstrumentation().waitForIdleSync();

		UserManager.sharedManager().clearCache();
		User user = MyApplication.getLocalUser();
		assertEquals(user.getName(), newName);
		assertEquals(user.getLocation(), newLocation);

		tearDown2();
	}
}