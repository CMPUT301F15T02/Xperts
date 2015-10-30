package ca.ualberta.cs.xpertsapp.inventory;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import ca.ualberta.cs.xpertsapp.view.MainActivity;

public class ViewInventoryTest extends ActivityInstrumentationTestCase2 {
	public ViewInventoryTest() {
		super(MainActivity.class);
	}

	/**
	 * US01.02.01
	 */
	public void test_ViewInventory() {
		MainActivity mainActivity = (MainActivity) this.getActivity();
		// On MainActivity
		final Button profileButton = mainActivity.getProfileButton();
		// Go to Profile Activity
		Instrumentation.ActivityMonitor profileActivityMonitor = this.getInstrumentation().addMonitor(ProfileActivity.class.getName(), null, false);
		mainActivity.runOnUiThread(new Runnable() {
			public void run() {
				profileButton.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();
		ProfileActivity profileActivity = (ProfileActivity) profileActivityMonitor.waitForActivityWithTimeout(1000);
		assertNotNull(profileActivity);
		assertEquals(1, profileActivityMonitor.getHits());
		assertEquals(ProfileActivity.class, profileActivity.getClass());
		getInstrumentation().removeMonitor(profileActivityMonitor);

		// On ProfileActivity
		// PASS
	}
}