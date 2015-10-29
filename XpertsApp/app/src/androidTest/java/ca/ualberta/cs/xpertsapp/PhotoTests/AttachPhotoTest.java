package ca.ualberta.cs.xpertsapp.PhotoTests;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import ca.ualberta.cs.xpertsapp.views.EditProfileActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;
import ca.ualberta.cs.xpertsapp.views.ProfileActivity;

public class AttachPhotoTest extends ActivityInstrumentationTestCase2 {
	public AttachPhotoTest() {
		super(MainActivity.class);
	}

	public void testAttachPhoto() {
		MainActivity mainActivity = (MainActivity) this.getActivity();
		// On MainActivity
		final Button profileButton = mainActivity.getViewProfileButton();
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
		final Button newServiceButton = profileActivity.getNewServiceButton();
		// Go to EditProfileActivity
		Instrumentation.ActivityMonitor editProfileActivityMonitor = this.getInstrumentation().addMonitor(EditProfileActivity.class.getName(), null, false);
		profileActivity.runOnUiThread(new Runnable() {
			public void run() {
				newServiceButton.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();
		EditProfileActivity editProfileActivity = (EditProfileActivity) profileActivityMonitor.waitForActivityWithTimeout(1000);
		assertNotNull(editProfileActivity);
		assertEquals(1, editProfileActivityMonitor.getHits());
		assertEquals(EditProfileActivity.class, editProfileActivity.getClass());
		getInstrumentation().removeMonitor(editProfileActivityMonitor);

		// On EditProfileActivity
		final Button addPhotoButton = editProfileActivity.getAddPhotoButton();
		// Add a photo
		assertTrue(editProfileActivity.getPhotos().size() == 0);
		editProfileActivity.runOnUiThread(new Runnable() {
			public void run() {
				addPhotoButton.performClick();
			}
		});

		// TODO: add photo with the activity somehow
	}
}