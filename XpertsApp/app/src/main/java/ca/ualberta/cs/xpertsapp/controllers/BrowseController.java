package ca.ualberta.cs.xpertsapp.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;

public class BrowseController {

    List<Category> categories;


    public List<Service> getServices (){
        List<Service> services = new ArrayList<Service>();
        User user = MyApplication.getLocalUser();

        for (User friend : user.getFriends()) {
            services.addAll(friend.getServices());
        }
        return services;
    }

    public List<Service> getServices (String query){
        return ServiceManager.sharedManager().findServicesOfFriends(query);
    }

    public List<String> getCategoryNames() {
        List<Category> categories = CategoryList.sharedCategoryList().getCategories();
        List<String> CategoryNames = new ArrayList<String>();

        CategoryNames.add("All Categories");
        for(Category c: categories){
            CategoryNames.add(c.toString());
        }
        return CategoryNames;
    }

    public List<Service> selectCategory(int position) {
        Category selectedCategory = null;
        if (position > 0) {
            selectedCategory = CategoryList.sharedCategoryList().getCategories().get(position - 1);
        }

        List<Service> services = new ArrayList<Service>();
        User user = MyApplication.getLocalUser();

        for (User friend : user.getFriends()) {
            for (Service s: friend.getServices()) {
                if (s.getCategory() == selectedCategory)
                    services.add(s);
            }
        }

        return services;
    }





}
