package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;

/**
 * Activity that displays the menu with My Profile, Browse Services, Trades, and Friends buttons.
 */
public class MainActivity extends Activity {

    // The BroadcastReceiver that tracks network connectivity changes.
    //private NetworkReceiver receiver = new NetworkReceiver();

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

    /**
     * Sets the onClickListeners for My Profile, Browse Services, Trades, and Friends buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register BroadcastReceiver to track connection changes.
        /*IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);*/

        // First run requires internet, otherwise write local
        /*if (MyApplication.isOnline()) {
            IOManager.sharedManager().cacheAll();
        }*/

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
    public void onDestroy() {
        super.onDestroy();
        /*if (receiver != null) {
            this.unregisterReceiver(receiver);
        }*/
    }

    /**
     *
     * This BroadcastReceiver intercepts the android.net.ConnectivityManager.CONNECTIVITY_ACTION,
     * which indicates a connection change.
     *
     * Code from http://developer.android.com/training/basics/network-ops/managing.html
     *
     */
   /* public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // Checks to see if the device has a connection.
            if (networkInfo != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://www.google.com");
                            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                            urlc.setConnectTimeout(3000);
                            urlc.connect();
                            if (urlc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                Constants.isOnline = true;
                            }
                        } catch (MalformedURLException mue) {
                            // TODO Auto-generated catch block
                            mue.printStackTrace();
                            Constants.isOnline = false;
                        } catch (IOException ie) {
                            // TODO Auto-generated catch block
                            ie.printStackTrace();
                            Constants.isOnline = false;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (Constants.isOnline) {
                                    Toast.makeText(context, "Internet connection detected", Toast.LENGTH_SHORT).show();

                                    // Whether the sync should be refreshed
                                    if (Constants.refreshSync) {
                                        System.out.println("push");
                                        IOManager.sharedManager().pushMe();
                                        IOManager.sharedManager().cacheAll();
                                    }
                                    Constants.refreshSync = false;
                                } else {
                                    Toast.makeText(context, "Internet connection lost", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            } else {
                Constants.isOnline = false;
                Toast.makeText(context, "Internet connection lost", Toast.LENGTH_SHORT).show();
            }
        }
    }
*/


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
