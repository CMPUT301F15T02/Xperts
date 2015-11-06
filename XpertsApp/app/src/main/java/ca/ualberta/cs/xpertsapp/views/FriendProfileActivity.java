package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class FriendProfileActivity extends Activity {
    private ProfileController pc = new ProfileController();
    private User friend;
    private TextView name = (TextView) findViewById(R.id.friendName);
    private TextView email = (TextView) findViewById(R.id.friendEmail);
    private TextView location = (TextView) findViewById(R.id.friendCity);
    private ListView services; //?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        Intent intent = getIntent();

        String userEmail = intent.getStringExtra("email");
        friend = UserManager.sharedManager().getUser(userEmail);

        //print user's info to screen
        //TODO test this - need another user
        name.setText(friend.getName());
        email.setText(friend.getEmail());
        location.setText(friend.getLocation());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_profile, menu);
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

    public void deleteFriend(View view) {
        //TODO
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }
}
