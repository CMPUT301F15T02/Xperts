package ca.ualberta.cs.xpertsapp.FriendTests;


import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import junit.framework.TestCase;

import java.util.List;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.FriendsActivity;

/**
 * Created by kmbaker on 11/3/15.
 * 02.05.01
 */


//TODO this doesn't test anything

public class ViewFriendProfileTest extends ActivityInstrumentationTestCase2 {

    public ViewFriendProfileTest() {
        super(FriendsActivity.class);
    }

    private User friend1;
    private User friend2;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String friend1String = "" +
                "{" +
                "\"contactInfo\":\"a@ualberta.ca\"," +
                "\"friends\":[]," +
                "\"id\":\"50\"," +
                "\"location\":\"outer space\"," +
                "\"name\":\"Matt Damon\"," +
                "\"services\":[]," +
                "\"trades\":[]" +
                "}";
        String friend2String = "" +
                "{" +
                "\"contactInfo\":\"b@ualberta.ca\"," +
                "\"friends\":[]," +
                "\"id\":\"51\"," +
                "\"location\":\"Canada\"," +
                "\"name\":\"Joe\"," +
                "\"services\":[]," +
                "\"trades\":[]" +
                "}";
        friend1 = (new Gson()).fromJson(friend1String, User.class);
        friend2 = (new Gson()).fromJson(friend2String, User.class);
        IOManager.sharedManager().storeData(friend1, Constants.serverUserExtension() + friend1.getEmail());
        IOManager.sharedManager().storeData(friend2, Constants.serverUserExtension() + friend2.getEmail());
        friend1 = UserManager.sharedManager().getUser(friend1.getEmail());
        friend2 = UserManager.sharedManager().getUser(friend2.getEmail());
    }

    @Override
    protected void tearDown() throws Exception {
        // Cleanup
        IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend1.getEmail());
        IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend2.getEmail());
        IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getEmail());

        super.tearDown();
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testViewFriend() {
        //starts FriendsActivity
        FriendsActivity activity = (FriendsActivity) getActivity();
        //reset app to a known state
        //need to add a friend to user
        //click on the friend
        //display the profile
        // Test add friends by searching for username
        User user = UserManager.sharedManager().localUser();
        user.addFriend(friend1);
        assertEquals(user.getFriends().size(), 1);




    }


}