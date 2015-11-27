package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.TradeListAdapter;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * Activity to handle trades. Not finished yet. It is called from MainActivity.
 */
public class TradeListActivity extends Activity {
    private TradeListActivity activity = this;
    private TradeListAdapter tradeListAdapter;
    private ListView tradesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_list);

        tradesListView = (ListView) findViewById(R.id.tradeListView);
        tradesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, IncomingOfferActivity.class);
                //if the borrower of the trade is local user, then go to OutgoingOfferActivity
                //otherwise you go to IncomingOfferActivity
                if (tradeListAdapter.getItem(position).getBorrower().equals(MyApplication.getLocalUser())) {
                    intent = new Intent(activity, OutgoingOfferActivity.class);
                }
                intent.putExtra("INTENT_ID",tradeListAdapter.getItem(position).getID());
                startActivity(intent);
            }
        });
    }

    /**
     * This initializes the tradeListAdapter to display the trades a user has.
     */
    @Override
    protected void onStart() {
        super.onStart();
        UserManager.sharedManager().clearCache();
        User user = MyApplication.getLocalUser();
        List<Trade> trades = user.getTrades();
        List<Trade> incoming = new ArrayList<Trade>();
        if (!trades.isEmpty()) {
            for (Trade trade : trades) {
                if (trade.getOwner().equals(user)) {
                    incoming.add(trade);
                }
            }
        }
        tradeListAdapter = new TradeListAdapter(this,incoming);
        tradesListView.setAdapter(tradeListAdapter);
    }

    /**
     * This initializes the tradeListAdapter to display the trades a user has.
     */
    @Override
    protected void onResume() {
        super.onResume();
        UserManager.sharedManager().clearCache();
        User user = MyApplication.getLocalUser();
        List<Trade> trades = user.getTrades();
        List<Trade> incoming = new ArrayList<Trade>();
        for (Trade trade :trades) {
            if (trade.getOwner().equals(user)) {
                incoming.add(trade);
            }
        }
        tradeListAdapter = new TradeListAdapter(this,incoming);
        tradesListView.setAdapter(tradeListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trades, menu);
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

    public void viewOutgoing(View view) {
        UserManager.sharedManager().clearCache();
        User user = MyApplication.getLocalUser();
        List<Trade> trades = user.getTrades();
        List<Trade> outgoing = new ArrayList<Trade>();
        for (Trade trade :trades) {
            if (trade.getBorrower().equals(user)) {
                outgoing.add(trade);
            }
        }
        tradeListAdapter.updateTradeList(outgoing);
    }

    public void viewIncoming(View view) {
        User user = MyApplication.getLocalUser();
        List<Trade> trades = user.getTrades();
        List<Trade> incoming = new ArrayList<Trade>();
        for (Trade trade :trades) {
            if (trade.getOwner().equals(user)) {
                incoming.add(trade);
            }
        }
        tradeListAdapter.updateTradeList(incoming);
    }
}
