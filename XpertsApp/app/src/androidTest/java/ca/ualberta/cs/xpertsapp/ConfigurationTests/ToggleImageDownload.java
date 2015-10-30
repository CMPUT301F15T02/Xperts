package ca.ualberta.cs.xpertsapp.ConfigurationTests;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ToggleImageDownload extends ActivityInstrumentationTestCase2 {
    public ToggleImageDownload() {
        super(MainActivity.class);
    }

    public void testToggleDownload() {
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());


        User user = new User();
        onView(withId(R.id.toggleDownload))
                .perform(swipeLeft());
        assertTrue(user.getSettings().getToggleDownload());
    }
}