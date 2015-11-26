package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;

public class OutgoingOfferActivity extends Activity {
    private ServiceListAdapter serviceOwnerAdapter;
    private ServiceListAdapter serviceBorrowerAdapter;
    private ServiceManager serviceManager = ServiceManager.sharedManager();
    private TradeManager tradeManager = TradeManager.sharedManager();
    private TextView ownerName;
    private ListView borrowerServices;
    private ListView ownerServices;
    private Intent intent;
    private Trade trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_offer);
        intent = getIntent();
        String tradeID = intent.getStringExtra("INTENT_ID");
        trade = tradeManager.getTrade(tradeID);

        ownerName = (TextView) findViewById(R.id.outgoingOwnerName);
        borrowerServices = (ListView) findViewById(R.id.borrowerServicesOut);
        ownerServices = (ListView) findViewById(R.id.ownerServicesOut);

        ownerName.setText(trade.getOwner().getName());
    }

    /**
     * Updates the serviceListAdapter with new services.
     */
    @Override
    protected void onStart() {
        super.onStart();
        List<Service> servicesBorrower;
        servicesBorrower = trade.getBorrowerServices();
        serviceBorrowerAdapter = new ServiceListAdapter(this,servicesBorrower);
        borrowerServices.setAdapter(serviceBorrowerAdapter);
        List<Service> servicesOwner;
        servicesOwner = trade.getOwnerServices();
        serviceOwnerAdapter = new ServiceListAdapter(this,servicesOwner);
        ownerServices.setAdapter(serviceOwnerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_outgoing_offer, menu);
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
