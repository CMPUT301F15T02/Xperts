package ca.ualberta.cs.xpertsapp.FriendTests;


import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import junit.framework.Test;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.BrowseServicesActivity;
import ca.ualberta.cs.xpertsapp.views.FriendsActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

/**
 * Created by kmbaker on 11/3/15.
 * 02.05.01
 */


//TODO this doesn't test anything

public class ViewFriendProfileTest extends TestCase {

    private Button friendsButton;
    FriendsActivity friendsActivity;
    Instrumentation.ActivityMonitor monitor;


    public ViewFriendProfileTest() {
        super();
    }

    private User friend1;

    @Override
    protected void setUp2() {
        super.setUp2();
        friend1 = newTestUser("a@ualberta.ca","Matt Damon", "outer space");
        monitor = getInstrumentation().addMonitor(FriendsActivity.class.getName(), null, false);

    }

    @Override
    protected void tearDown2() {
        getInstrumentation().removeMonitor(monitor);
        super.tearDown2();
    }

    public void testStart() throws Exception {
        setUp2();
        Activity activity = getActivity();
        tearDown2();
    }

    public void testViewFriend() {
		setUp2();

        //starts FriendsActivity
        MainActivity activity = (MainActivity) getActivity();
        friendsButton = activity.getFriendsBtn();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendsButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish

        friendsActivity = (FriendsActivity) getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(friendsActivity);

        //reset app to a known state
        //need to add a friend to user
        //click on the friend
        //display the profile
        // Test add friends by searching for username
        User user = MyApplication.getLocalUser();
        user.addFriend(friend1);
        assertEquals(user.getFriends().size(), 1);
        friendsActivity.finish();

		tearDown2();
    }


}