package ca.ualberta.cs.xpertsapp.BrowseTests;

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
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.BrowseServicesActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

public class PrivateServicesTest extends TestCase {
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
    BrowseServicesActivity browseActivity;
    Service privateService;
    Instrumentation instrumentation;
    Instrumentation.ActivityMonitor monitor;
    private static final int TIME_OUT = 5000;

    public PrivateServicesTest() {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
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
        privateService = newTestService("Private Service", "Description", 3, false);
        u1.addService(privateService);
        localUser = MyApplication.getLocalUser();
        localUser.addFriend(u1);
        localUser.addFriend(u2);
        localUser.addFriend(u3);
        instrumentation = getInstrumentation();
        monitor = instrumentation.addMonitor(BrowseServicesActivity.class.getName(), null, false);
    }

    @Override
    protected void tearDown() throws Exception {
        browseActivity.finish();
        instrumentation.removeMonitor(monitor);
        super.tearDown();
    }

    /**
     * UC03.04.01
     */
    public void testPrivateServices() {
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
        browseActivity = (BrowseServicesActivity) instrumentation.waitForMonitorWithTimeout(monitor, TIME_OUT);
        assertNotNull(browseActivity);

        serviceList = browseActivity.getServiceList();
        int count = serviceList.getAdapter().getCount();

        for (int i = 0; i < count; i++) {
            Service s = (Service) serviceList.getAdapter().getItem(i);
            assertNotSame(privateService, s);
        }

        browseActivity.finish();
    }

}