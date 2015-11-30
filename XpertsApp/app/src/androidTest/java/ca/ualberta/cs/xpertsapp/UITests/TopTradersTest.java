package ca.ualberta.cs.xpertsapp.UITests;

import android.app.Instrumentation;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.MainActivity;
import ca.ualberta.cs.xpertsapp.views.TopTradersActivity;

public class TopTradersTest extends TestCase {
    private Button topTradersBtn;
    private SearchView searchView;
    private ListView traderList;
    private User localUser;
    private User u1;
    private User u2;
    private User u3;
    Instrumentation instrumentation;
    Instrumentation.ActivityMonitor monitor;
    private static final int TIME_OUT = 10000;

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
        localUser = MyApplication.getLocalUser();
        u1 = newTestUser("david@xperts.com", "David Skrundz", "Calgary");
        u2 = newTestUser("seann@xperts.com", "Seann Murdock", "Vancouver");
        u3 = newTestUser("kathleen@xperts.com", "Kathleen Baker", "Toronto");
        IOManager.sharedManager().storeData(u1, Constants.serverUserExtension() + u1.getEmail());
        IOManager.sharedManager().storeData(u2, Constants.serverUserExtension() + u2.getEmail());
        IOManager.sharedManager().storeData(u3, Constants.serverUserExtension() + u3.getEmail());

        /*
        status = 0 -> pending
        status = 1 -> accepted
        status = 2 -> cancelled
        status = 3 -> declined
        status = 4 -> complete
        */
        //Add four accepted/complete trades to u1
        addTrade(u1, 1);
        addTrade(u1, 1);
        addTrade(u1, 4);
        addTrade(u1, 4);

        //Add three accepted/complete trades to u2
        addTrade(u2, 1);
        addTrade(u2, 1);
        addTrade(u2, 4);
        //Add two accepted/complete trades to u3 and three declined, cancelled and pending trades
        addTrade(u3, 1);
        addTrade(u3, 4);
        addTrade(u3, 0);
        addTrade(u3, 2);
        addTrade(u3, 3);

        instrumentation = getInstrumentation();
        monitor = instrumentation.addMonitor(TopTradersActivity.class.getName(), null, false);


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

        //Navigate from Main menu
        MainActivity activity = (MainActivity) getActivity();
        topTradersBtn = activity.getTopTradersBtn();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                topTradersBtn.performClick();
            }
        });
        getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish
        TopTradersActivity topTradersActivity = (TopTradersActivity) instrumentation.waitForMonitorWithTimeout(monitor, TIME_OUT);
        assertNotNull(topTradersActivity);


        traderList = topTradersActivity.getTraderList();

        int count = traderList.getAdapter().getCount();

        Integer score1 = 0;
        Integer score2 = 0;

        for(int i = 1; i < count; i++){
            View trader1 = getViewByPosition(i-1, traderList);
            View trader2 = getViewByPosition(i, traderList);
            TextView u_score1 = (TextView) trader1.findViewById(R.id.num_trades);
            TextView u_score2 = (TextView) trader2.findViewById(R.id.num_trades);
            score1 = Integer.parseInt(u_score1.getText().toString());
            score2 = Integer.parseInt(u_score2.getText().toString());
            assertEquals(true, score1 >= score2);
        }

        topTradersActivity.finish();

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
                "\"owner\":\""+localUser.getEmail()+"\"," +
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

    /**
     * Taken from: http://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position
     * @param pos
     * @param listView
     * @return List view item at the given position in the listview.
     */
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}