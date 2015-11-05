package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Constants;

public class MainActivity extends Activity {
    private Button MyProfileBtn;
    public Button getMyProfileBtn() {return MyProfileBtn;};
    private Button BrowseBtn;
    public Button getBrowseBtn() {return BrowseBtn;};
    private Button TradesBtn;
    public Button getTradesBtn() {return TradesBtn;};
    private Button FriendsBtn;
    public Button getFriendsBtn() {return FriendsBtn;};
    private Button LogoutBtn;
    public Button getLogoutBtn() {
        return LogoutBtn;
    }

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

        LogoutBtn = (Button) findViewById(R.id.btn_logout);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.logout();
            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
