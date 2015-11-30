package ca.ualberta.cs.xpertsapp.UITests;

import android.app.Instrumentation;
import android.view.View;
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
import ca.ualberta.cs.xpertsapp.views.BrowseServiceDetailsActivity;
import ca.ualberta.cs.xpertsapp.views.BrowseServicesActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;
import ca.ualberta.cs.xpertsapp.views.ViewProfileActivity;

public class CloneServiceTest extends TestCase {
    private Button browseButton;
    private Button cloneButton;
    private Button profileButton;
    private ListView serviceList;
    private User localUser;
    private User u1;
    private User u2;
    private User u3;
    Instrumentation instrumentation;
    Instrumentation.ActivityMonitor monitorBrowse;
    Instrumentation.ActivityMonitor monitorProfile;
    Instrumentation.ActivityMonitor monitorService;
    private static final int TIME_OUT = 5000;

    public CloneServiceTest() {
        super();
    }

    @Override
    protected void setUp2() {
        super.setUp2();
        UserManager.sharedManager().clearCache();
        ServiceManager.sharedManager().clearCache();

        IOManager.sharedManager().deleteData(Constants.serverUserExtension());
        IOManager.sharedManager().deleteData(Constants.serverServiceExtension());
        u1 = newTestUser("david@xperts.com", "David Skrundz", "Calgary");
        u1.addService(newTestService("U1 FirstService", "U1 FirstDescription", 0, true));
        localUser = MyApplication.getLocalUser();
        localUser.addFriend(u1);

    }

    @Override
    protected void tearDown2() {
        super.tearDown2();
    }

    /**
     * UC13.01.01
     */
    public void testCloneService() {
        setUp2();

        instrumentation = getInstrumentation();
        monitorBrowse = instrumentation.addMonitor(BrowseServicesActivity.class.getName(), null, false);

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
        BrowseServicesActivity browseActivity = (BrowseServicesActivity) instrumentation.waitForMonitorWithTimeout(monitorBrowse, TIME_OUT);
        assertNotNull(browseActivity);

        List<User> friends = localUser.getFriends();
        List<Service> friendsServices = new ArrayList<Service>();
        for (User f : friends) {
            friendsServices.addAll(f.getServices());
        }

        serviceList = browseActivity.getServiceListView();
        int count = serviceList.getAdapter().getCount();
        assertEquals(friendsServices.size(), count);

        Service toClone = (Service) browseActivity.getServiceListView().getAdapter().getItem(0);
        assertTrue(friendsServices.contains(toClone));

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                serviceList.performItemClick(
                        serviceList.getAdapter().getView(0, null, null),
                        0,
                        serviceList.getAdapter().getItemId(0));
            }
        });
        getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish
        instrumentation.removeMonitor(monitorBrowse);
        instrumentation = getInstrumentation();
        monitorService = instrumentation.addMonitor(BrowseServiceDetailsActivity.class.getName(), null, false);

        BrowseServiceDetailsActivity serviceActivity = (BrowseServiceDetailsActivity) instrumentation.waitForMonitorWithTimeout(monitorService, TIME_OUT);
        assertNotNull(serviceActivity);

        cloneButton = serviceActivity.getCloneBtn();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cloneButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        serviceActivity.finish();
        browseActivity.finish();

        activity = (MainActivity) getActivity();

        instrumentation.removeMonitor(monitorService);
        instrumentation = getInstrumentation();
        monitorProfile = instrumentation.addMonitor(ViewProfileActivity.class.getName(), null, false);

        profileButton = activity.getMyProfileBtn();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                profileButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish
        //Navigate from View profile

        ViewProfileActivity profileActivity = (ViewProfileActivity) instrumentation.waitForMonitorWithTimeout(monitorProfile, TIME_OUT);

        assertNotNull(profileActivity);

        int countCloned = profileActivity.getServiceList().getAdapter().getCount();
        assertEquals(1, countCloned);

        Service cloned = (Service) profileActivity.getServiceList().getAdapter().getItem(0);

        assertEquals(toClone.getName(), cloned.getName());
        profileActivity.finish();
        instrumentation.removeMonitor(monitorProfile);

        tearDown2();
    }

}