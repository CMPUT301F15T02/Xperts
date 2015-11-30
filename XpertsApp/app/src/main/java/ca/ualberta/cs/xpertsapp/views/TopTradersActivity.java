package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.TopTraderAdapter;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * This activity displays the list of top traders using the app
 */
public class TopTradersActivity extends Activity {
    private ListView friendsList;
    private TopTradersActivity activity = this;
    private ProfileController pc = new ProfileController();
    public ListView getFriendsList() {return friendsList;};
    private TopTraderAdapter topTraderAdapter;

    // For UI Test
    public TopTraderAdapter getTopTraderAdapter() {
        return topTraderAdapter;
    }

    /**
     * This sets the click listener for the user list.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_traders);

        friendsList = (ListView) findViewById(R.id.listView);

        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = topTraderAdapter.getItem(position).getEmail();
                Intent intent;
                if (email.equals(MyApplication.getLocalUser().getEmail())) {
                    intent = new Intent(activity, ViewProfileActivity.class);
                } else {
                    intent = new Intent(activity, FriendProfileActivity.class);
                    intent.putExtra("INTENT_EMAIL", email);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_traders, menu);
        return true;
    }

    /**
     * This initializes the friendListAdapter to display the top traders
     */
    @Override
    protected void onStart() {
        super.onStart();
        UserManager.sharedManager().clearCache();
        List<User> AllUsers = new ArrayList<User>();
        try {
            AllUsers = UserManager.sharedManager().findUsers("*");
        } catch (Exception e) {
            Context context = getApplicationContext();
            CharSequence text = "Failed to load top users, server busy. Please try again.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        Collections.sort(AllUsers, new Comparator<User>() {
            public int compare(User one, User two) {
                if (getTraderScore(one) >= getTraderScore(two)) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        topTraderAdapter = new TopTraderAdapter(this,AllUsers);
        friendsList.setAdapter(topTraderAdapter);
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
     * Calculates the top trader score for a user
     * @param user The user to calculate the trader score for
     * @return The number of in progress and completed trades for the user
     */
    public Integer getTraderScore(User user) {
        List<Trade> trades = user.getTrades();
        Integer score = 0;
        for(Trade t : trades) {
            int status = t.getStatus();
            if (status == 1 || status == 4)
                score++;
        }
        return score;
    }

}
