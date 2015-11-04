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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.List;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;

public class BrowseServicesActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner categorySpinner;
    List<Category> categories;
    ArrayAdapter<Category> categoryAdapter;

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


        categories = CategoryList.sharedCategoryList().getCategories();
        Category all = new Category("All Categories");
        categories.add(0, all);

        // Taken from: http://stackoverflow.com/questions/9863378/how-to-hide-one-item-in-an-android-spinner
        categoryAdapter = new ArrayAdapter<Category>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,categories);

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
        Toast.makeText(getApplicationContext(), "You selected : " + categories.get(pos), Toast.LENGTH_SHORT).show();
        // TODO: Update view with selected category
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}
