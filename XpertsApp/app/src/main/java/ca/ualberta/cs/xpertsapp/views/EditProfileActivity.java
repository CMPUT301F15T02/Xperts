package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.EditProfileController;
import ca.ualberta.cs.xpertsapp.model.User;

public class EditProfileActivity extends Activity {

    private EditText email;
    private EditText name;
    private EditText location;
    private Switch downloads;
    private Intent intent;

    public EditText getEmail() {
        return email;
    }

    public EditText getName() {
        return name;
    }

    public EditText getLocation() {
        return location;
    }

    private EditProfileController epc = new EditProfileController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);
        email = (EditText) findViewById(R.id.emailEditText);
        name = (EditText) findViewById(R.id.nameEditText);
        location = (EditText) findViewById(R.id.locationEditText);
        downloads = (Switch) findViewById(R.id.switch1);
        User user = MyApplication.getLocalUser();
        intent = getIntent();
        email.setText(user.getEmail());
        name.setText(user.getName());
        location.setText(user.getLocation());

        downloads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //enable photo downloads
                } else {
                    //disable photo downloads
                }
            }
        });
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

    public void saveProfile(View view) {
        epc.editProfile(email,name,location);

        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
