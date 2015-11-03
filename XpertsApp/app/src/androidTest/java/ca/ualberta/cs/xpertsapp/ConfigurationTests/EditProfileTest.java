package ca.ualberta.cs.xpertsapp.ConfigurationTests;

import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;
import ca.ualberta.cs.xpertsapp.views.EditProfileActivity;

import android.support.test.InstrumentationRegistry;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class EditProfileTest extends ActivityInstrumentationTestCase2<EditProfileActivity> {
    private EditProfileActivity mActivity;

    public EditProfileTest() {
        super(EditProfileActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testEmailEdited() {
        // Load user from local
        Users users = new IOManager(mActivity).loadFromFile();
        User user = users.get(0);
        user.setEmail("testEmail");

        // UI execution with Espresso
        onView(withId(R.id.emailEditText))
                .perform(typeText("newEmail"), closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());

        // Check that user's email was changed
        users = new IOManager(mActivity).loadFromFile();
        user = users.get(0);

        assertEquals("newEmail", user.getEmail());
    }
}