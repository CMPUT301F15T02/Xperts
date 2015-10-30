package ca.ualberta.cs.xpertsapp.ConfigurationTests;

import android.app.Activity;
import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.widget.Button;

import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.views.MainActivity;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class EditProfileTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mActivity;

    public EditProfileTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testProfileChanged() {
        User user = new User();
        //profileWasChanged = Boolean.FALSE;
        //user.addObserver(this);
        user.setName("test");
        //assertTrue(profileWasChanged);*/

        onView(withId(R.id.editTextName))
                .perform(typeText("newName"), closeSoftKeyboard());
        onView(withId(R.id.buttonChangeName)).perform(click());

        // Check that user's name was changed
        assertEquals("newName", user.getName());
    }
}