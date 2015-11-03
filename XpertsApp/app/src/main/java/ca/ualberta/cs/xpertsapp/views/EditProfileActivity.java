package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;

public class EditProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

    }

    // Button's function
    public void saveProfile(View view) {

        // Check if email changed
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        String newEmail = emailEditText.getText().toString();

        if (newEmail.length() != 0) {
            changeEmail(newEmail);
        }

        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

    public void changeEmail(String newEmail) {
        Users users = new IOManager(this).loadFromFile();
        User user = users.get(0);

        user.setEmail(newEmail);

        // Save change to local
        new IOManager(this).writeToFile(users);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
