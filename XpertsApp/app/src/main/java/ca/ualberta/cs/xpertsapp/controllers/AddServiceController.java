package ca.ualberta.cs.xpertsapp.controllers;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.AddServiceActivity;

public class AddServiceController {

    public void addService(EditText title, EditText description, Category category,CheckBox isprivate, CategoryList CL) {

        Service newService = ServiceManager.sharedManager().newService();
        newService.setName(title.getText().toString());
        newService.setDescription(description.getText().toString());
        newService.setCategory(category);
        newService.setShareable(isprivate.isChecked() ? Boolean.FALSE : Boolean.TRUE);
        User user = MyApplication.getLocalUser();
        user.addService(newService);
    }

    public void editService(EditText title, EditText description, Category category,CheckBox isprivate, CategoryList CL, String serviceID) {

        Service editedService = ServiceManager.sharedManager().getService(serviceID);
        editedService.setName(title.getText().toString());
        editedService.setDescription(description.getText().toString());
        editedService.setCategory(category);
        editedService.setShareable(isprivate.isChecked() ? Boolean.FALSE : Boolean.TRUE);
    }

    public void deleteService() {

    }

}