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

/**
 *  Helper functions for the browse activity to get appropriate list of services
 *  @author Hammad Jutt
 */
public class BrowseController {

    List<Category> categories;


    /**
     * Gets all of the services from the local user's friends
     * @return list of services form local user's friends
     */
    public List<Service> getServices () {
        List<Service> services = new ArrayList<Service>();
        User user = MyApplication.getLocalUser();

        for (User friend : user.getFriends()) {
            services.addAll(friend.getServices());
        }
        return services;
    }

    /**
     * Gets all of the services from the local user's friends in a particular category
     * @param categoryNum: the number of the category, or 0 for all categories
     * @return the list of services from the local user's friends belonging to the selected category
     */
    public List<Service> getServices (int categoryNum) {
        Category selectedCategory = null;
        if (categoryNum == 0)
            return getServices();

        if (categoryNum > 0) {
            selectedCategory = CategoryList.sharedCategoryList().getCategories().get(categoryNum - 1);
        }

        List<Service> services = new ArrayList<Service>();
        User user = MyApplication.getLocalUser();

        for (User friend : user.getFriends()) {
            for (Service s: friend.getServices()) {
                if (s.getCategory().equals(selectedCategory))
                    services.add(s);
            }
        }
        return services;
    }

    /**
     * Gets all of the services from the local user's friends in a particular category and matching
     * the given query.
     * @param categoryNum: the number of the category, or 0 for all categories;
     * @param query: the textual query to search services with
     * @return the list of services from the local user's friends belonging to the selected category
     * and matching the given query
     */
    public List<Service> getServices (int categoryNum, String query) {
        if(query.equals(""))
            return getServices(categoryNum);

        List<Service> services = ServiceManager.sharedManager().findServicesOfFriends(query);

        if (categoryNum > 0) {
            Category selectedCategory = null;
            selectedCategory = CategoryList.sharedCategoryList().getCategories().get(categoryNum - 1);

            for (Service s: services) {
                if (!s.getCategory().equals(selectedCategory))
                    services.remove(s);
            }
        }

        return services;
    }

    /**
     * Generates list of possible categories to filter by
     * @return List of strings representing each category and an option for All Categories
     */
    public List<String> getCategoryNames() {
        List<Category> categories = CategoryList.sharedCategoryList().getCategories();
        List<String> CategoryNames = new ArrayList<String>();

        CategoryNames.add("All Categories");
        for(Category c: categories){
            CategoryNames.add(c.toString());
        }
        return CategoryNames;
    }
}
