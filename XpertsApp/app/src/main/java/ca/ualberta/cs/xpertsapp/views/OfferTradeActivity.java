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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.controllers.TradeController;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.User;

public class OfferTradeActivity extends Activity {
    private ServiceListAdapter serviceRequestAdapter;
    private ServiceListAdapter serviceListAdapter;
    private User user = MyApplication.getLocalUser();
    private ListView requestServices;
    private ListView yourServices;
    private Intent intent;
    private Service initialService;
    private ServiceManager serviceManager = ServiceManager.sharedManager();
    private ArrayList<Service> initialServiceList = new ArrayList<Service>();
    private OfferTradeActivity activity = this;
    private ArrayList<View> colouredItems = new ArrayList<View>();
    private ArrayList<Service> borrowerServices = new ArrayList<Service>();
    private TradeController tradeController = new TradeController();


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
        setContentView(R.layout.activity_offer_trade);
        requestServices = (ListView) findViewById(R.id.requestServiceList);
        yourServices = (ListView) findViewById(R.id.yourServiceList);

        //get Service that user clicked on
        intent = getIntent();
        String serviceID = intent.getStringExtra("INTENT_SERVICEID");
        initialService = serviceManager.getService(serviceID);
        initialServiceList.add(initialService);
        //set list adapter to display list
        serviceRequestAdapter = new ServiceListAdapter(this,initialServiceList);
        requestServices.setAdapter(serviceRequestAdapter);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Service> Services;
        Services = user.getServices();
        serviceListAdapter = new ServiceListAdapter(this,Services);
        yourServices.setAdapter(serviceListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offer_trade, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when Send Trade button is pushed. It creates a trade using a trade controller and
     * starts the next activity, which is BrowseServicesActivity.
     */
    public void makeTrade(View view) {
        //can make a trade without any borrower services
        tradeController.createTrade(initialService.getOwner(), borrowerServices, initialService);
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
