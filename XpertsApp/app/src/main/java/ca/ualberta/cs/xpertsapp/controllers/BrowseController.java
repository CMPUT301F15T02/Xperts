package ca.ualberta.cs.xpertsapp.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;

public class BrowseController extends Activity {

    private List<Category> categories;
    private ArrayAdapter<Category> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = CategoryList.sharedCategoryList().getCategories();
        categories.add(0, new Category("All Categories"));

        categoryAdapter = new ArrayAdapter<Category>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,categories);

    }


    public List<Category> getCategories() {
        return categories;
    }

    public ArrayAdapter<Category> getCategoryAdapter() {
        return categoryAdapter;
    }



}
