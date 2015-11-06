package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.User;

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void addService(View view) {
        Intent intent = new Intent(this, AddServiceActivity.class);
        startActivity(intent);
    }
}