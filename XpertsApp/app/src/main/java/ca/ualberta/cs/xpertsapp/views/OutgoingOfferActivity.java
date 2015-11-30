package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.controllers.TradeController;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;

/**
 * This displays the trade details of an outgoing offer. The user is able to delete the trade
 * here by clicking the Cancel button. This removes the trade from the system and from the borrower
 * and owner.
 */
public class OutgoingOfferActivity extends Activity {
    private ServiceListAdapter serviceOwnerAdapter;
    private ServiceListAdapter serviceBorrowerAdapter;
    private ServiceManager serviceManager = ServiceManager.sharedManager();
    private TradeManager tradeManager = TradeManager.sharedManager();
    private ListView borrowerServices;
    private ListView ownerServices;
    private Intent intent;
    private Trade trade;
    private TradeController tradeController = new TradeController();
    private Button cancel;

    /**
     * This sets the owner name and gets the trade from the intent.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_offer);
        intent = getIntent();
        String tradeID = intent.getStringExtra("INTENT_ID");
        trade = tradeManager.getTrade(tradeID);

        borrowerServices = (ListView) findViewById(R.id.borrowerServicesOut);
        ownerServices = (ListView) findViewById(R.id.ownerServicesOut);


        cancel = (Button) findViewById(R.id.cancelButton);
        if (trade.getStatus() != 0) {
            cancel.setVisibility(View.INVISIBLE);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelTrade(v);
            }
        });
    }

    /**
     * Updates the serviceListAdapters with new services.
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



        return super.onOptionsItemSelected(item);
    }

    /**
     * This should cancel the trade. Only the borrower in the trade can cancel a trade and only when
     * it's status is pending. It deletes the trade from the system and from both the owner and borrower.
     * * @param view cancel button that was pressed
     */
    public void cancelTrade(View view) {
        //need to cancel the trade here
        //state must be pending
        if (trade.getStatus()==0) {
            tradeController.deleteTrade(trade.getID());
            finish();
        }
    }
}
