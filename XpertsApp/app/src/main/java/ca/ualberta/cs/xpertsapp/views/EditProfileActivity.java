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

/**
 * This activity allows the user to edit their profile. It is called from ViewProfileActivity.
 */
public class EditProfileActivity extends Activity {

    private EditText email;
    private EditText name;
    private EditText location;
    private Switch switch1;
    private Button saveButton;

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

    public Switch getSwitch1() {
        return switch1;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    private EditProfileController epc = new EditProfileController();

    /**
     * This fills in the text fields with the user's info.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        email = (EditText) findViewById(R.id.emailEditText);
        name = (EditText) findViewById(R.id.nameEditText);
        location = (EditText) findViewById(R.id.locationEditText);
        switch1 = (Switch) findViewById(R.id.switch1);
        saveButton = (Button) findViewById(R.id.saveButton);

        intent = getIntent();
        final User user = MyApplication.getLocalUser();
        email.setText(user.getEmail());
        name.setText(user.getName());
        location.setText(user.getLocation());
        // Listen on toggle switch

        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setChecked(user.getDownloadsEnabled());

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (switch1.isChecked()) {
                    user.setDownloadsEnabled(true);
                } else {
                    user.setDownloadsEnabled(false);
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

    /**
     * This is called when the save button is pushed. It edits the user's profile by
     * calling editProfile with the new values.
     * @param view
     */
    public void saveProfile(View view) {
        epc.editProfile(email,name,location);

        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
