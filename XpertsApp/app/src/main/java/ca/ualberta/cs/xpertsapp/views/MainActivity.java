package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.TradeController;


/**
 * Activity that displays the menu with My Profile, Browse Services, Trades, and Friends buttons.
 */
public class MainActivity extends Activity {

    private Button MyProfileBtn;
    public Button getMyProfileBtn() {return MyProfileBtn;};
    private Button BrowseBtn;
    public Button getBrowseBtn() {return BrowseBtn;};
    private Button TradesBtn;
    public Button getTradesBtn() {return TradesBtn;};
    private Button FriendsBtn;
    public Button getFriendsBtn() {return FriendsBtn;};
    private Button TopTradersBtn;
    public Button getTopTradersBtn() {return TopTradersBtn;};
    private Button LogoutBtn;
    public Button getLogoutBtn() {
        return LogoutBtn;
    }
    private TextView notifications;

    /**
     * Sets the onClickListeners for My Profile, Browse Services, Trades, and Friends buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication.loginCheck();

        MyProfileBtn = (Button) findViewById(R.id.MyProfileBtn);
        MyProfileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewProfileActivity.class));
            }
        });

        BrowseBtn = (Button) findViewById(R.id.BrowseBtn);
        BrowseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BrowseServicesActivity.class));
            }
        });

        TradesBtn = (Button) findViewById(R.id.TradesBtn);
        TradesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TradeListActivity.class));
            }
        });

        FriendsBtn = (Button) findViewById(R.id.FriendsBtn);
        FriendsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FriendsActivity.class));
            }
        });

        TopTradersBtn = (Button) findViewById(R.id.TopTradersBtn);
        TopTradersBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TopTradersActivity.class));
            }
        });

        LogoutBtn = (Button) findViewById(R.id.btn_logout);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logout();
            }
        });

        notifications = (TextView) findViewById(R.id.notifications);
    }

    /**
     * Sets the notification of how many pending trades the user has
     */
    @Override
    protected void onStart() {
        super.onStart();
        if(MyApplication.isLoggedIn()) {
            TradeController tradeController = new TradeController();
            Integer pending = tradeController.getPendingTrades();
            notifications.setText(pending.toString());
            if (pending == 0) {
                notifications.setVisibility(View.INVISIBLE);
            } else {
                notifications.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Sets the notification of how many pending trades the user has
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.isLoggedIn()) {

            TradeController tradeController = new TradeController();
            Integer pending = tradeController.getPendingTrades();
            notifications.setText(pending.toString());
            if (pending == 0) {
                notifications.setVisibility(View.INVISIBLE);
            } else {
                notifications.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}