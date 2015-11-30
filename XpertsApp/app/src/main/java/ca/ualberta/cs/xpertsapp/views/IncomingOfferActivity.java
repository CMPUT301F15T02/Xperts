package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.controllers.TradeController;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;

public class IncomingOfferActivity extends Activity {
    private ServiceListAdapter serviceOwnerAdapter;
    private ServiceListAdapter serviceBorrowerAdapter;
    private ServiceManager serviceManager = ServiceManager.sharedManager();
    private TradeManager tradeManager = TradeManager.sharedManager();
    private TradeController tradeController = new TradeController();
    private TextView borrowerName;
    private ListView borrowerServices;
    private ListView ownerServices;
    private Intent intent;
    private Trade trade;
    private Button accept;
    private Button decline;
    private Button complete;
    private Button offerCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_offer);
        intent = getIntent();
        String tradeID = intent.getStringExtra("INTENT_ID");
        trade = tradeManager.getTrade(tradeID);

        accept = (Button) findViewById(R.id.acceptButton);
        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                acceptTrade(v);
            }
        });
        decline = (Button) findViewById(R.id.declineButton);
        decline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                declineTrade(v);
            }
        });
        complete = (Button) findViewById(R.id.completeButton);
        complete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                completeTrade(v);
            }
        });
        offerCounter = (Button) findViewById(R.id.counterButton);
        offerCounter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                counterTrade(v);
            }
        });
        complete.setVisibility(View.INVISIBLE);
        offerCounter.setVisibility(View.INVISIBLE);

        //status = 0 means pending
        if (trade.getStatus() != 0) {
            accept.setVisibility(View.INVISIBLE);
            decline.setVisibility(View.INVISIBLE);
        }
        //status = 1 means accepted
        if (trade.getStatus() == 1) {
            complete.setVisibility(View.VISIBLE);
        }
        //status = 3 means declined
        if (trade.getStatus() == 3) {
            offerCounter.setVisibility(View.VISIBLE);
        }

        borrowerName = (TextView) findViewById(R.id.incomingBorrowerName);
        borrowerServices = (ListView) findViewById(R.id.borrowerServicesIn);
        ownerServices = (ListView) findViewById(R.id.ownerServicesIn);

        borrowerName.setText(trade.getBorrower().getName());
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
        getMenuInflater().inflate(R.menu.menu_incoming_offer, menu);
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
     * Accepts a trade. Only possible for the owner in the trade.
     * @param view The accept button
     */
    public void acceptTrade(View view) {
        trade.accept();

        //send email
        /* http://developer.android.com/guide/components/intents-filters.html */
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("message/rfc822");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {trade.getOwner().getEmail(), trade.getBorrower().getEmail()});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Your trade has been accepted!");
        sendIntent.putExtra(Intent.EXTRA_TEXT, getEmailText());
        startActivity(Intent.createChooser(sendIntent, "send email"));


        finish();
    }

    /**
     * This is for getting the body of the email for an acceptance email.
     * @return A string for the body of the email text.
     */
    public String getEmailText() {
        String email = "Congrats on the trade!\n"+
            "Your trade is for:\n";
        for (Service service : trade.getOwnerServices()) {
            email+=service.getName();
            email+="\n";
        }
        email+="from "+trade.getOwner().getName();
        email+=", in exchange for:\n";
        for (Service service : trade.getBorrowerServices()) {
            email+=service.getName();
            email+="\n";
        }
        if (trade.getBorrowerServices().isEmpty()) {
            email+="no services\n";
        }
        email+="from "+trade.getBorrower().getName()+".";
        email+=" Once you have both exchanged services, you can set the trade to complete.";
        return email;
    }

    /**
     * Declines a trade. Only possible for the owner in the trade.
     * @param view The decline button
     */
    public void declineTrade(View view) {
        trade.decline();
        finish();
    }

    /**
     * Sets the trade state to complete.
     * @param view The complete button.
     */
    public void completeTrade(View view) {
        trade.complete();
        finish();
    }

    /**
     * Offers a counter trade.
     * @param view The counter trade button.
     */
    public void counterTrade(View view) {
        //go to OfferTradeActivity to create new trade initialized with items from this trade
        //pass in trade id.
        //maybe make new OfferCounterTradeActivity
        Intent intent = new Intent(this, OfferCounterTradeActivity.class);
        intent.putExtra("INTENT_TRADEID", trade.getID());
        startActivity(intent);
    }
}
