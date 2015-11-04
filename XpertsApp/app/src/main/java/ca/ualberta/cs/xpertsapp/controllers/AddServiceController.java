package ca.ualberta.cs.xpertsapp.controllers;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.AddServiceActivity;

public class AddServiceController {

    public void addService(EditText title, EditText description, Category category,CheckBox isprivate, CategoryList CL) {
        String Title = title.getText().toString();
        String Description = description.getText().toString();


        Boolean shareable;
        if (isprivate.isChecked()){
            shareable = Boolean.FALSE;
        } else {
            shareable = Boolean.TRUE;
        }

        Service newService = ServiceManager.sharedManager().newService();
        newService.setName(Title);
        newService.setDescription(Description);
        newService.setCategory(category);
        newService.setShareable(shareable);
        User user = UserManager.sharedManager().localUser();
        user.addService(newService);

    }

}


