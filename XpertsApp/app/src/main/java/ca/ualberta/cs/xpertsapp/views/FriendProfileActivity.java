package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class FriendProfileActivity extends Activity {
    private ProfileController pc = new ProfileController();
    private User friend;
    private TextView name;
    private TextView email;
    private TextView location;
    private Button delete;
    private Intent intent;
    private ListView services;
    private ServiceListAdapter serviceListAdapter;
    private FriendProfileActivity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        intent = getIntent();

        name = (TextView) findViewById(R.id.friendName);
        email = (TextView) findViewById(R.id.friendEmail);
        location = (TextView) findViewById(R.id.friendCity);
        services = (ListView) findViewById(R.id.friendServiceList);
        delete = (Button) findViewById(R.id.deleteButton);


        String userEmail = intent.getStringExtra("INTENT_EMAIL");
        friend = UserManager.sharedManager().getUser(userEmail);
        //print user's info to screen
        //TODO this screws up going back from FriendServiceDetailsActivity
        name.setText(friend.getName());
        email.setText(friend.getEmail());
        location.setText(friend.getLocation());


        services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, FriendServiceDetailsActivity.class);
                intent.putExtra(Constants.IntentServiceName, serviceListAdapter.getItem(position).getID());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_profile, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Service> Services;
        Services = friend.getServices();
        serviceListAdapter = new ServiceListAdapter(this,Services);
        services.setAdapter(serviceListAdapter);
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

    public void deleteFriend(View view) {
        //delete the friend
        pc.deleteFriend(friend);
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }
}
