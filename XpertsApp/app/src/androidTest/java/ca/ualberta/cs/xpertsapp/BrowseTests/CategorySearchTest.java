package ca.ualberta.cs.xpertsapp.BrowseTests;

import android.app.Instrumentation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.controllers.BrowseController;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.BrowseServicesActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

public class CategorySearchTest extends TestCase {
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
    Instrumentation instrumentation;
    Instrumentation.ActivityMonitor monitor;
    private static final int TIME_OUT = 5000;

    public CategorySearchTest() {
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
        u1 = newTestUser("david@xperts.com","David Skrundz","Calgary");
        u2 = newTestUser("seann@xperts.com", "Seann Murdock", "Vancouver");
        u3 = newTestUser("kathleen@xperts.com", "Kathleen Baker", "Toronto");
        u1.addService(newTestService("someCategory1Service","Description",0,true));
        u1.addService(newTestService("someCategory2Service","Description",1,true));
        u2.addService(newTestService("someCategory3Service","Description",2,true));
        u2.addService(newTestService("someCategory4Service","Description",3,true));
        u3.addService(newTestService("someCategory5Service","Description",4,true));
        u3.addService(newTestService("someCategory6Service","Description",5,true));
        u1.addService(newTestService("someCategory7Service","Description",6,true));
        u2.addService(newTestService("someCategory8Service","Description",7,true));
        u3.addService(newTestService("someCategory9Service","Description",8,true));
        u1.addService(newTestService("someCategory10Service","Description",9,true));
        localUser = MyApplication.getLocalUser();
        localUser.addFriend(u1);
        localUser.addFriend(u2);
        localUser.addFriend(u3);
        instrumentation = getInstrumentation();
        monitor = instrumentation.addMonitor(BrowseServicesActivity.class.getName(), null, false);
    }

    @Override
    protected void tearDown2() {
        browseActivity.finish();
        instrumentation.removeMonitor(monitor);
        super.tearDown2();
    }

    /**
     * UC03.03.01
     */
    public void testServiceCategorySearch(){
        setUp2();

        setActivityInitialTouchMode(true);

        User friend = localUser.getFriends().get(0);

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
        browseActivity = (BrowseServicesActivity) instrumentation.waitForMonitorWithTimeout(monitor, TIME_OUT);
        assertNotNull(browseActivity);

        categorySpinner = browseActivity.getCategorySpinner();
        int count = categorySpinner.getAdapter().getCount();

        //Iterate over every category in spinner
        for(int i = 1; i < count; i++){
            final int finalI = i;
            browseActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    categorySpinner.setSelection(finalI);
                }
            });
            getInstrumentation().waitForIdleSync();

            //i-1 because i starts at 1, not testing all categories
            Category currentCat = CategoryList.sharedCategoryList().getCategories().get(i-1);
            serviceList = browseActivity.getServiceListView();
            int numResults = serviceList.getAdapter().getCount();

            //Iterate over all services returned and check they are of the right category
            for (int j = 0; j < numResults; j++) {
                Service s = (Service) serviceList.getAdapter().getItem(j);
                assertTrue(s.getCategory().equals(currentCat));
            }

        }

        tearDown2();
    }
}