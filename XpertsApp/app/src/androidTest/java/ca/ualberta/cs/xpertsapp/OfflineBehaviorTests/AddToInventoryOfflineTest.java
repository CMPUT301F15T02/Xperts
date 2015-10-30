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
public class AddToInventoryOfflineTest extends ActivityInstrumentationTestCase2 {

    public AddToInventoryOfflineTest() {
        super(MainActivity.class);
    }

    // Test that a service offer can be added offline
    public void testAddServicesOffline() {

    }
}