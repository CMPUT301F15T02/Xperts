package ca.ualberta.cs.xpertsapp.ConfigurationTests;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class EditProfileTest extends ActivityInstrumentationTestCase2 {
    public EditProfileTest() {
        super(MainActivity.class);
    }

    public void testProfileChanged() {
        /*User user = new User();
        profileWasChanged = Boolean.FALSE;
        user.addObserver(this);
        user.setName("test");
        assertTrue(profileWasChanged);*/
    }
}