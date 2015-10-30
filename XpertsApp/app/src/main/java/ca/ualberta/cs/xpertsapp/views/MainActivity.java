package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.UsersController;
import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.models.User;

public class MainActivity extends Activity {

    private UsersController usersController;
    private IOManager ioManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button MyProfileBtn = (Button) findViewById(R.id.MyProfileBtn);
        MyProfileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewProfileActivity.class));
            }
        });

        Button BrowseBtn = (Button) findViewById(R.id.BrowseBtn);
        BrowseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BrowseServicesActivity.class));
            }
        });

        Button TradesBtn = (Button) findViewById(R.id.TradesBtn);
        TradesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TradesActivity.class));
            }
        });

        Button FriendsBtn = (Button) findViewById(R.id.FriendsBtn);
        FriendsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FriendsActivity.class));
            }
        });
    }

    public void viewProfile(View view) {
        // Create test user
        /*User user = new User();
        user.setEmail("hindle");
        Users users = new Users();
        usersController = new UsersController(users);
        Thread thread = new AddThread(user);
        thread.start();*/

        // Search user by my email
        ioManager = new IOManager();
        ioManager.search("*");
        // Need change email here
        Thread thread = new GetThread("hindle");
        thread.start();
    }

    class GetThread extends Thread {
        private String email;

        public GetThread(String email) {
            this.email = email;
        }

        @Override
        public void run() {
            user = ioManager.getUser(email);

            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(doFinishGet);
        }
    }

    private Runnable doFinishGet = new Runnable() {
        public void run() {
            Intent intent = new Intent(MainActivity.this, ViewProfileActivity.class);
            intent.putExtra("EMAIL", user.getEmail());
            startActivity(intent);
        }
    };

    class AddThread extends Thread {
        private User user;

        public AddThread(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            usersController.addUser(user);

            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
