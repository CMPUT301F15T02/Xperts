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
import ca.ualberta.cs.xpertsapp.model.User;

public class EditProfileActivity extends Activity {

    private EditText Email;
    private EditText Name;
    private EditText Location;
    private Switch switch1;
    private Intent intent;

    public EditText getEmail() {
        return Email;
    }

    public EditText getName() {
        return Name;
    }

    public EditText getLocation() {
        return Location;
    }

    public Switch getSwitch1() {
        return switch1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);
        Email = (EditText) findViewById(R.id.emailEditText);
        Name = (EditText) findViewById(R.id.nameEditText);
        Location = (EditText) findViewById(R.id.locationEditText);
        switch1 = (Switch) findViewById(R.id.switch1);

        intent = getIntent();

        // Listen on toggle switch
        final User user = MyApplication.getLocalUser();
        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setChecked(user.getToggleEnabled());

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (switch1.isChecked()) {
                    user.setToggleEnabled(true);
                } else {
                    user.setToggleEnabled(false);
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
        //Intent intent = new Intent(this, ViewProfileActivity.class);
        //startActivity(intent);
        finish();
    }
}
