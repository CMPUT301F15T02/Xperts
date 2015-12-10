package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.adapters.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.User;

/**
 * Activity that allows user to view their profile. It is called from MainActivity.
 * From here a user can add services, edit their profile, or view their services.
 */
public class ViewProfileActivity extends Activity {
    private Button editProfile;
    public Button getEditProfile() {return editProfile;};
    private Button addService;
    public Button getAddService() {return addService;};
    private ListView serviceList;
    public ListView getServiceList() {return serviceList;};
    private ViewProfileActivity activity = this;
    private ServiceListAdapter serviceListAdapter;

    private TextView name;
    private TextView email;
    private TextView location;

    public TextView getName() {
        return name;
    }

    public TextView getEmail() {
        return email;
    }

    public TextView getLocation() {
        return location;
    }
    List<Service> services;

    /**
     * Sets text for user's name, email and location and the click listener for ListView of services.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        name = (TextView) findViewById(R.id.profileName);
        email = (TextView) findViewById(R.id.profileEmail);
        location = (TextView) findViewById(R.id.profileCity);

        editProfile = (Button) findViewById(R.id.editButton);
        addService = (Button) findViewById(R.id.add);
        serviceList = (ListView) findViewById(R.id.serviceList);
        User user = MyApplication.getLocalUser();

        name.setText(user.getName());
        email.setText(user.getEmail());
        location.setText(user.getLocation());

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, ServiceDetailsActivity.class);
                intent.putExtra(Constants.IntentServiceName,serviceListAdapter.getItem(position).getID());
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_profile, menu);
        return true;
    }

    /**
     * Sets serviceList adapter to display user's services.
     */
    @Override
    protected void onStart() {
        super.onStart();

        User user = MyApplication.getLocalUser();
        List<Service> Services = user.getServices();
        serviceListAdapter = new ServiceListAdapter(this,Services);
        serviceList.setAdapter(serviceListAdapter);
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
     * Method called when edit button is pushed. It starts EditProfileActivity.
     */
    public void editProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    /**
     * Method called when add service button is pushed. It starts AddServiceActivity.
     */
    public void addService(View view) {
        Intent intent = new Intent(this, AddServiceActivity.class);
        startActivity(intent);
    }
}