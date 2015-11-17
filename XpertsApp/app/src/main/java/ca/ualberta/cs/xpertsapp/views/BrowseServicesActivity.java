package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.BrowseController;
import ca.ualberta.cs.xpertsapp.controllers.ServiceListAdapter;
import ca.ualberta.cs.xpertsapp.model.Service;

/**
 * Activity to browse friends services and filter by category and/or textual query
 *
 */
public class BrowseServicesActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner categorySpinner;
    private ArrayAdapter<String> categoryAdapter;
    private SearchManager searchManager;
    private SearchView searchView;
    private BrowseController Controller;
    private ServiceListAdapter serviceAdapter;
    private List<Service> services;
    private ListView serviceList;
    private int currentCategory;
    private String currentQuery;


    /**
     * Sets the category spinner and search view in the options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browse_services, menu);

        categorySpinner = (Spinner)menu.findItem(R.id.category_spinner).getActionView();
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    /**
     * @return the searvice search search input view
     */
    public SearchView getSearchView() {
        return searchView;
    }

    /**
     * @return List of current services attached to the activity
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * @return ListView of current services attached to the activity
     */
    public ListView getServiceListView() {
        return serviceList;

    }

    /**
     * @return CategorySpinner view to select a category of services
     */
    public Spinner getCategorySpinner() {
        return categorySpinner;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets the serviceAdapter and categoryAdapter.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_services);

        assert(getActionBar() != null);
        getActionBar().setTitle("");

        currentCategory = 0;
        currentQuery = "";

        Controller = new BrowseController();

        serviceAdapter = new ServiceListAdapter(MyApplication.getContext(), new ArrayList<Service>());
        // Taken from: http://stackoverflow.com/questions/9863378/how-to-hide-one-item-in-an-android-spinner
        categoryAdapter = new ArrayAdapter<String>(MyApplication.getContext(),
                android.R.layout.simple_spinner_dropdown_item, Controller.getCategoryNames());
        serviceList = (ListView) findViewById(R.id.serviceList);
        serviceList.setAdapter(serviceAdapter);
    }

    /**
     * Handle any new incoming intents. Used to handle user searches
     */
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListView();
    }

    /**
     * Handles search intent and updates list of services
     */
    private void handleIntent (Intent intent) {
        //if intent is a search, use query to search and filter data
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getApplicationContext(), "You searched for : " + query, Toast.LENGTH_SHORT).show();
            currentQuery = query;
            updateListView();
        }
    }

    /**
     * On item selected listener for category spinner. Updates list of services when category changed
     * @param pos The index of the category selected in the spinner
     */
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        currentCategory = pos;
        updateListView();
    }
    /**
     * Executes in place of {@link #onItemSelected(AdapterView, View, int, long)} when no category selected
     */
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Gets the services matching the current category selected and current query
     */
    public void updateListView(){
        services = Controller.getServices(currentCategory, currentQuery);
        serviceAdapter.updateServiceList(services);
    }
}
