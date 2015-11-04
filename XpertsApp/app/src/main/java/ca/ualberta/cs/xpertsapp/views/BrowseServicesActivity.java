package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;

public class BrowseServicesActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner categorySpinner;
    List<Category> categories;
    ArrayAdapter<String> categoryAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_services, menu);


        categorySpinner = (Spinner)menu.findItem(R.id.category_spinner).getActionView();
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        getActionBar().setTitle("");

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_services);

        ListView serviceList = (ListView) findViewById(R.id.serviceList);
//        List<Service> services = ServiceManager.sharedManager().getServices();

        List<Service> services = new ArrayList<Service>();
        User user = MyApplication.getLocalUser();

        for (User friend : user.getFriends()) {
            services.addAll(friend.getServices());
        }

        serviceList.setAdapter(new ServiceListAdapter(this, services));

        categories = CategoryList.sharedCategoryList().getCategories();

        List<String> CategoryNames = new ArrayList<String>();
        CategoryNames.add("All Categories");
        for(Category c: categories){
            CategoryNames.add(c.toString());
        }

        // Taken from: http://stackoverflow.com/questions/9863378/how-to-hide-one-item-in-an-android-spinner
        categoryAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, CategoryNames);

        handleIntent(getIntent());
    }



    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void handleIntent (Intent intent) {
        //if intent is a search, use query to search and filter data
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getApplicationContext(), "You searched for : " + query, Toast.LENGTH_SHORT).show();

            //TODO: use the query to search services and update view
        }
        //TODO: otherwise display all items
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        Category selected = null;
        if (pos > 0) {
            selected = categories.get(pos - 1);
        }
        Toast.makeText(getApplicationContext(), "You selected : " + selected, Toast.LENGTH_SHORT).show();

        // TODO: Update view with selected category
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


}
