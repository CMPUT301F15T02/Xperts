package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.FriendsListAdapter;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * This activity displays the list of friends a user has and
 * allows a user to search for and add friends.
 */
public class FriendsActivity extends Activity {
    private ListView friendsList;
    private FriendsActivity activity = this;
    private ProfileController pc = new ProfileController();
    public ListView getFriendsList() {return friendsList;};
    private FriendsListAdapter friendsListAdapter;

    // For UI Test
    private Button buttonAddFriend;
    private EditText editTextEmail;
    private AlertDialog alertDialog;

    public Button getButtonAddFriend() {
        return buttonAddFriend;
    }

    public EditText getEditTextEmail() {
        return editTextEmail;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public FriendsListAdapter getFriendsListAdapter() {
        return friendsListAdapter;
    }

    /**
     * This sets the click listener for the friends list.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        buttonAddFriend = (Button) findViewById(R.id.addFriend);
        buttonAddFriend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addFriend(v);
            }
        });
        friendsList = (ListView) findViewById(R.id.listView);

        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, FriendProfileActivity.class);
                intent.putExtra("INTENT_EMAIL",friendsListAdapter.getItem(position).getEmail());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    /**
     * This initializes the friendListAdapter to display the friends a user has.
     */
    @Override
    protected void onStart() {
        super.onStart();
        UserManager.sharedManager().clearCache();
        User user = MyApplication.getLocalUser();
        List<User> Friends = user.getFriends();
        friendsListAdapter = new FriendsListAdapter(this,Friends);
        friendsList.setAdapter(friendsListAdapter);
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
     * The method that's called when add friend button pushed.
     * It doesn't allow a user to add themselves or to add a friend again.
     * Used:
     * http://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog accessed November 3, 2015
     * for assistance in setting up an AlertDialog
     * @param view
     */
    public void addFriend(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        editTextEmail = new EditText(activity);
        builder.setMessage("Enter Email");
        builder.setView(editTextEmail);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String email = editTextEmail.getText().toString();
                if (email.equals(MyApplication.getLocalEmail())) {
                    Context context = getApplicationContext();
                    CharSequence text = "You can't add yourself!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    //call to search for user from controller
                    User friend = pc.addFriend(email);
                    if (friend == null) {
                        //TODO update list view with friend
                        //no user with that email exists
                        Context context = getApplicationContext();
                        CharSequence text = "No user with that email exists!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (friend.equals(MyApplication.getLocalUser())) {
                        Context context = getApplicationContext();
                        CharSequence text = "You can't add yourself!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        friendsListAdapter.updateFriendsList(MyApplication.getLocalUser().getFriends());
                        Context context = getApplicationContext();
                        CharSequence text = "Friend added";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
