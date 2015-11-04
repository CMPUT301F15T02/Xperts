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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.security.InvalidParameterException;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.model.User;

public class FriendsActivity extends Activity {
    private ListView friendsList;
    private FriendsActivity activity = this;
    private ProfileController pc = new ProfileController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        friendsList = (ListView) findViewById(R.id.listView);

        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, FriendProfileActivity.class);
                User friend = (User) friendsList.getItemAtPosition(position);
                intent.putExtra("id", friend.getID());
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

    //http://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog november 3, 2015
    public void addFriend(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final EditText editText = new EditText(activity);
        builder.setMessage("Enter Email");
        builder.setView(editText);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String email = editText.getText().toString();
                //call to search for user from controller
                //try {
                    pc.searchUsers(email);
                    //TODO update list view with friend
               //} catch (RuntimeException e) {
                    //no user with that email exists
                    Context context = getApplicationContext();
                    CharSequence text = "No user with that email exists!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
               //}
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
