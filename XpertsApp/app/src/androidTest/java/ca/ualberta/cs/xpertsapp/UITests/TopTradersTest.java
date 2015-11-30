package ca.ualberta.cs.xpertsapp.UITests;

import android.app.Instrumentation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.controllers.BrowseController;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.BrowseServicesActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

public class TopTradersTest extends TestCase {
    private Button browseButton;
    private SearchView searchView;
    private ListView serviceList;
    private User localUser;
    private User u1;
    private User u2;
    private User u3;
    Instrumentation instrumentation;
    Instrumentation.ActivityMonitor monitor;
    private static final int TIME_OUT = 5000;

    public TopTradersTest() {
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


        instrumentation = getInstrumentation();
        monitor = instrumentation.addMonitor(BrowseServicesActivity.class.getName(), null, false);

        /*
        status = 0 -> pending
        status = 1 -> accepted
        status = 2 -> cancelled
        status = 3 -> declined
        status = 4 -> complete
        */
    }

    @Override
    protected void tearDown2() {

        instrumentation.removeMonitor(monitor);
        super.tearDown2();
    }

    /**
     * UC12.01.01
     */
    public void testTopTraders() {
        setUp2();

        setActivityInitialTouchMode(true);

        User friend = localUser.getFriends().get(0);
        Service testService = newTestService("test search footext", "descriptionText", 0, true);
        Service testService2 = newTestService("test", "835346274 descriptionText", 0, true);
        friend.addService(testService);
        friend.addService(testService2);

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
        BrowseServicesActivity browseActivity = (BrowseServicesActivity) instrumentation.waitForMonitorWithTimeout(monitor, TIME_OUT);
        assertNotNull(browseActivity);

        searchView = browseActivity.getSearchView();
        browseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery("footext", true);
            }
        });
        getInstrumentation().waitForIdleSync();

        serviceList = browseActivity.getServiceListView();
        assertFalse(serviceList.getAdapter().isEmpty());

        int count = serviceList.getAdapter().getCount();
        assertEquals(1, count);

        assertEquals(testService, serviceList.getAdapter().getItem(0));

        browseActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery("835346274", true);
            }
        });
        getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish

        serviceList = browseActivity.getServiceListView();
        assertFalse(serviceList.getAdapter().isEmpty());

        count = serviceList.getAdapter().getCount();
        assertEquals(1, count);

        assertEquals(testService2, serviceList.getAdapter().getItem(0));
        browseActivity.finish();

        tearDown2();
    }

    private void addTrade(User user, Integer status) {
        /*
        status = 0 -> pending
        status = 1 -> accepted
        status = 2 -> cancelled
        status = 3 -> declined
        status = 4 -> complete
        */
        String tradeString = "" +
                "{" +
                "\"borrower\":\"" + user.getEmail() + "\"," +
                "\"borrowerServices\":[]," +
                "\"id\":\"0002\"," +
                "\"lastUpdatedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
                "\"owner\":\"1\"," +
                "\"ownerServices\":[]," +
                "\"proposedDate\":\"Nov 1, 2015 6:11:40 PM\"," +
                "\"isCounterOffer\":false," +
                "\"status\":"+ status.toString() +
                "}";

        Trade trade = (new Gson()).fromJson(tradeString, Trade.class);
        IOManager.sharedManager().storeData(trade, Constants.serverTradeExtension() + trade.getID());
        trade = TradeManager.sharedManager().getTrade(trade.getID());
        trade.commit();
    }
}