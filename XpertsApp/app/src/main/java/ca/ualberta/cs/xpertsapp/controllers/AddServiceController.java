package ca.ualberta.cs.xpertsapp.controllers;

import android.graphics.Bitmap;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.AddServiceActivity;

/**
 * This controller communicates with  {@link AddServiceActivity}  and the model
 */
public class AddServiceController {

    /**
     * This function takes the inputted information and converts it into a Service object in the database
     * @param title this is the string value of the Service's title passed as a EditText from the {@link AddServiceActivity} there are no character restrictions. Ex "Write JavaDoc"
     * @param description this is a description of the service passed as an EditText from the  {@link AddServiceActivity}  that a user writes to better describe the service to other users. Ex "I write excellent JavaDoc for java projects. This includes descriptions and examples of different variables and functions. And I don't forget about runime."
     * @param category is the category of a Service. Category objects are used by User's to better focus their search results.Category objects have a string title. Ex "Computers"
     * @param isPrivate is the setting that allows a user to keep a Service unsearchable by other Users. It is passed as a CheckBox from  {@link AddServiceActivity} . For example User Bob might want to keep his "Write JavaDoc" private because it is highly in demand. This way he isn't spammed by 301 students but is still able to offer his service to other User's who have a service that he wants.
     */
     public void addService(EditText title, EditText description, Category category,CheckBox isPrivate, List<Bitmap> pictures) {

        Service newService = ServiceManager.sharedManager().newService();
        newService.setName(title.getText().toString());
        newService.setDescription(description.getText().toString());
        newService.setCategory(category);
        newService.setShareable(isPrivate.isChecked() ? Boolean.FALSE : Boolean.TRUE);
        for (int i = 0; i < pictures.size();i++)
            {
                newService.addPicture(pictures.get(i));
            }
        MyApplication.getLocalUser().addService(newService);
    }

    /**
     * This function takes the inputted information and converts it into a Service object in the database
     * @param title this is the string value of the Service's title passed as a EditText
     * @param description this is a description of the service passed as an EditText
     * @param category is the category of a Service.
     */
    public void cloneService(String title, String description, Category category, List<Bitmap> pictures) {

        Service newService = ServiceManager.sharedManager().newService();
        newService.setName(title);
        newService.setDescription(description);
        newService.setCategory(category);
        newService.setShareable(Boolean.TRUE);
        for (int i = 0; i < 1;i++)
        {
            newService.addPicture(pictures.get(i));
        }
        MyApplication.getLocalUser().addService(newService);
    }

    /**
     *This function takes the inputted information from the  {@link AddServiceActivity}  and converts it into a service object in the database
     * @param title see above
     * @param description "" ""
     * @param category "" ""
     * @param isPrivate "" ""
     * @param id this is the existing service ID of the Service which is being edited. This is used to edit information instead of creating a new Service.
     */
    public void editService(EditText title, EditText description, Category category,CheckBox isPrivate, String id, List<Bitmap> pictures) {

        Service editedService = ServiceManager.sharedManager().getService(id);
        editedService.setName(title.getText().toString());
        editedService.setDescription(description.getText().toString());
        editedService.setCategory(category);
        for (int i = 0; i < pictures.size();i++)
        {
            editedService.addPicture(pictures.get(i));
        }
        editedService.setShareable(isPrivate.isChecked() ? Boolean.FALSE : Boolean.TRUE);
    }

    /**
     * This function deletes a selected service
     * @param id This is the service ID of the service which is being deleted
     */

    public void deleteService(String id) {

        Service deletedService = ServiceManager.sharedManager().getService(id);
        MyApplication.getLocalUser().removeService(deletedService);
    }

}