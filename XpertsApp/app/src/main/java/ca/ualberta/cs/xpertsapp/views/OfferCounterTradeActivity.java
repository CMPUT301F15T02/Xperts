package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.controllers.TradeController;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;

public class OfferCounterTradeActivity extends Activity {
    private ServiceListAdapter serviceRequestAdapter;
    private ServiceListAdapter serviceListAdapter;
    private User user = MyApplication.getLocalUser();
    private ListView requestServices;
    private ListView yourServices;
    private Intent intent;
    private Service initialService;
    private TradeManager tradeManager = TradeManager.sharedManager();
    private ArrayList<Service> ownerServices = new ArrayList<Service>();
    private OfferCounterTradeActivity activity = this;
    private ArrayList<View> colouredItems = new ArrayList<View>();
    private ArrayList<View> colouredItems2 = new ArrayList<View>();
    private ArrayList<Service> borrowerServices = new ArrayList<Service>();
    private TradeController tradeController = new TradeController();
    private Trade trade;

    /**
     * used:
     * http://stackoverflow.com/questions/2217753/changing-background-color-of-listview-items-on-android
     * to help set background color on item selected. Answer from user Francisco Cabezas
     * Oct. 4, 2011. Accessed November 27, 2015.
     * http://stackoverflow.com/questions/14510346/nullpointerexception-when-clicking-listview-item
     * to help with NullPointerException from setBackgroundColor, accessed November 27, 2015.
     * Answer from user Leonidos, Jan. 24, 2013.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_counter_trade);
        requestServices = (ListView) findViewById(R.id.requestServiceListCounter);
        yourServices = (ListView) findViewById(R.id.yourServiceListCounter);

        //get Trade that user clicked on
        intent = getIntent();
        String tradeID = intent.getStringExtra("INTENT_TRADEID");

        trade = tradeManager.getTrade(tradeID);

        List<Service> services;
        services = trade.getOwner().getServices();
        //don't want the current services to be in lists
        for (Service s: trade.getOwnerServices()) {
            services.remove(s);
        }

        serviceRequestAdapter = new ServiceListAdapter(this, services);
        requestServices.setAdapter(serviceRequestAdapter);
        //set list adapter to display list
        requestServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //set colour - add to colourItems
                View v = parent.getChildAt(position);
                if (colouredItems2.contains(v)) {
                    colouredItems2.remove(v);
                    view.setBackgroundColor(Color.TRANSPARENT);
                    ownerServices.remove(serviceRequestAdapter.getItem(position));
                } else {
                    colouredItems2.add(v);
                    //set colour
                    float[] hsv = new float[3];
                    hsv[0] = (float) 203;
                    hsv[1] = (float) 0.1;
                    hsv[2] = (float) 0.75;
                    view.setBackgroundColor(Color.HSVToColor(hsv));
                    ownerServices.add(serviceRequestAdapter.getItem(position));
                }
            }
        });

        //set list adapter to display list
        //serviceRequestAdapter = new ServiceListAdapter(this,ownerServices);
        //requestServices.setAdapter(serviceRequestAdapter);

        yourServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //set colour - add to colourItems
                View v = parent.getChildAt(position);
                if (colouredItems.contains(v)) {
                    colouredItems.remove(v);
                    view.setBackgroundColor(Color.TRANSPARENT);
                    borrowerServices.remove(serviceListAdapter.getItem(position));
                }
                else {
                    colouredItems.add(v);
                    //set colour
                    float[] hsv = new float[3];
                    hsv[0] = (float) 203;
                    hsv[1] = (float) 0.1;
                    hsv[2] = (float) 0.75;
                    view.setBackgroundColor(Color.HSVToColor(hsv));
                    borrowerServices.add(serviceListAdapter.getItem(position));
                }
            }
        });

        //TODO set list items to dark if already part of trade - how??
        for (Service s: trade.getBorrowerServices()) {
            borrowerServices.add(s);
        }
        for (Service s: trade.getOwnerServices()) {
            ownerServices.add(s);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Service> services;
        services = trade.getBorrower().getServices();
        //don't want the current services to be in lists
        for (Service s: trade.getBorrowerServices()) {
            services.remove(s);
        }
        serviceListAdapter = new ServiceListAdapter(this,services);
        yourServices.setAdapter(serviceListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offer_counter_trade, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Called when Send Trade button is pushed. It creates a trade using a trade controller and
     * starts the next activity, which is BrowseServicesActivity.
     */
    public void makeTrade(View view) {
        //TODO change what's passed into createTrade
        //createTrade(User owner, ArrayList < Service > borrowerServices, ArrayList < Service > ownerServices)
        tradeController.createTrade(trade.getBorrower(), ownerServices, borrowerServices, true);
        //make a toast to say sent trade
        Context context = getApplicationContext();
        CharSequence text = "Trade request sent";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        //return to activity
        finish();
    }
}
