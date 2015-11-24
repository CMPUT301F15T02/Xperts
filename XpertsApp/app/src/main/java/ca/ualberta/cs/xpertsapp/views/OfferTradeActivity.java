package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
