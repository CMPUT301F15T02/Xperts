package ca.ualberta.cs.xpertsapp.UITests.BrowseTests;

import android.app.Instrumentation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.controllers.BrowseController;
import ca.ualberta.cs.xpertsapp.adapters.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.BrowseServicesActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

public class BrowseServicesTest extends TestCase {
    private Spinner Categories;
    private Button browseButton;
    private Spinner categorySpinner;
    private SearchView searchView;
    private ArrayAdapter<String> categoryAdapter;
    private BrowseController Controller;
    private ServiceListAdapter serviceAdapter;
    private List<Service> services;
    private ListView serviceList;
    int currentCategory;
    private String currentQuery;
    private User localUser;
    private User u1;
    private User u2;
    private User u3;
    Instrumentation instrumentation;
    Instrumentation.ActivityMonitor monitor;
    private static final int TIME_OUT = 5000;

    public BrowseServicesTest() {
        super();
    }

    @Override
    protected void setUp2() {
        super.setUp2();
        UserManager.sharedManager().clearCache();
        ServiceManager.sharedManager().clearCache();
        TradeManager.sharedManager().clearCache();

        IOManager.sharedManager().deleteData(Constants.serverUserExtension());
        IOManager.sharedManager().deleteData(Constants.serverServiceExtension());
        IOManager.sharedManager().deleteData(Constants.serverTradeExtension());
        u1 = newTestUser("david@xperts.com", "David Skrundz", "Calgary");
        u2 = newTestUser("seann@xperts.com", "Seann Murdock", "Vancouver");
        u3 = newTestUser("kathleen@xperts.com", "Kathleen Baker", "Toronto");
        u1.addService(newTestService("U1 FirstService", "U1 FirstDescription", 0, true));
        u1.addService(newTestService("U1 SecondService", "U1 SecondDescription", 1, true));
        u2.addService(newTestService("U2 FirstService", "U2 FirstDescription", 2, true));
        u2.addService(newTestService("U2 SecondService", "U2 SecondDescription", 3, true));
        u3.addService(newTestService("U3 FirstService", "U3 FirstDescription", 4, true));
        u3.addService(newTestService("U3 SecondService", "U3 SecondDescription", 5, true));
        localUser = MyApplication.getLocalUser();
        localUser.addFriend(u1);
        localUser.addFriend(u2);
        localUser.addFriend(u3);
        instrumentation = getInstrumentation();
        monitor = instrumentation.addMonitor(BrowseServicesActivity.class.getName(), null, false);
    }

    @Override
    protected void tearDown2() {
        instrumentation.removeMonitor(monitor);
        super.tearDown2();
    }

    /**
     * UC03.01.01
     */
    public void testServiceSearch() {
        setUp2();

        setActivityInitialTouchMode(true);
        //Navigate from Main menu
        MainActivity activity = (MainActivity) getActivity();
        browseButton = activity.getBrowseBtn();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                browseButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish
        //Navigate from View profile
        BrowseServicesActivity browseActivity = (BrowseServicesActivity) instrumentation.waitForMonitorWithTimeout(monitor, TIME_OUT);
        assertNotNull(browseActivity);

        List<User> friends = localUser.getFriends();
        List<Service> friendsServices = new ArrayList<Service>();
        for (User f : friends) {
            friendsServices.addAll(f.getServices());
        }

        serviceList = browseActivity.getServiceListView();
        int count = serviceList.getAdapter().getCount();
        assertEquals(friendsServices.size(), count);

        for (int i = 0; i < count; i++) {
            Service s = (Service) browseActivity.getServiceListView().getAdapter().getItem(i);
            assertTrue(friendsServices.contains(s));
        }
        browseActivity.finish();

        tearDown2();
    }

}