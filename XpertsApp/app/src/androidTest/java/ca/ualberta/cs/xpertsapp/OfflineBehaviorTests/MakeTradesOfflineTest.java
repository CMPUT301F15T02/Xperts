package ca.ualberta.cs.xpertsapp.OfflineBehaviorTests;

import android.app.Activity;
import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.models.Service;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class MakeTradesOfflineTest extends ActivityInstrumentationTestCase2 {
    private Activity activity;

    public MakeTradesOfflineTest() {
        super(MainActivity.class);
    }

    // Test that a trade can be done offline
    public void testTradeOffline() {
        User borrower = new User();
        Users users = new Users();
        ArrayList<Service> gives = new ArrayList<Service>();
        ArrayList<Service> receives = new ArrayList<Service>();

        // Borrower makes trade offline
        Service give = new Service();
        give.setName("test");
        gives.add(give);
        Service receive = new Service();
        receive.setName("newTest");
        receives.add(receive);
        /*borrower.setGives(gives);
        borrower.setReceives(receives);*/
        users.add(borrower);

        // Writes trade to local storage
        new IOManager(activity).writeToFile(users);

        Users borrowerLocals = new IOManager(activity).loadFromFile();
        User borrowerLocal = borrowerLocals.get(0);
        /*assertEquals(gives, borrowerLocal.getGives());
        assertEquals(receives, borrowerLocal.getReceives());*/
    }

    // Test that an offline trade can be written to server if internet is detected
    public void testPushTradesToServer() {
        // Loads borrower from local storage
        Users borrowerLocals = new IOManager(activity).loadFromFile();
        User borrowerLocal = borrowerLocals.get(0);

        // Writes to server (if internet is detected)
        new IOManager(activity).addUserToServer(borrowerLocal);

        // Retrieves from server
        User borrowerServer = new IOManager(activity).getUser("testEmail");

        /*assertEquals(borrowerLocal.getGives(), borrowerServer.getGives());
        assertEquals(borrowerLocal.getReceives(), borrowerServer.getReceives());*/
    }
}