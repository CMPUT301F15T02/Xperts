package ca.ualberta.cs.xpertsapp.OfflineBehaviorTests;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import ca.ualberta.cs.xpertsapp.models.Service;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class BrowseSearchHistoryTest extends ActivityInstrumentationTestCase2 {
    public BrowseSearchHistoryTest() {
        super(MainActivity.class);
    }

    // Test borrower's search history
    public void testSearchHistory() {
        User borrower = new User();
        borrower.setEmail("testEmail");
        Service service = new Service();
        service.setName("testService");

        /*borrower.getHistoryList().add(service);

        assertFalse(borrower.getHistoryList().size(), 0);*/
    }
}