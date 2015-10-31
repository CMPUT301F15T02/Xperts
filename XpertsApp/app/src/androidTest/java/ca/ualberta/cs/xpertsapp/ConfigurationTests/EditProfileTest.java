package ca.ualberta.cs.xpertsapp.ConfigurationTests;

import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;
import ca.ualberta.cs.xpertsapp.views.ChangeProfileActivity;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class EditProfileTest extends ActivityInstrumentationTestCase2<ChangeProfileActivity> {
    private ChangeProfileActivity mActivity;

    public EditProfileTest() {
        super(ChangeProfileActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testProfileChanged() {
        // Load user from local
        Users users = new IOManager(mActivity).loadFromFile();
        User user = users.get(0);
        user.setName("testName");

        onView(withId(R.id.editTextName))
                .perform(typeText("newName"), closeSoftKeyboard());
        onView(withId(R.id.buttonChangeName)).perform(click());

        // Check that user's name was changed
        users = new IOManager(mActivity).loadFromFile();
        user = users.get(0);

        assertEquals("newName", user.getName());
    }
}