package ca.ualberta.cs.xpertsapp.controllers;

import android.widget.EditText;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.User;

public class EditProfileController {

	//can you edit email?
	public void editProfile(EditText email, EditText name, EditText location) {
		User user = MyApplication.getLocalUser();
		String newEmail = email.getText().toString();
		String newName = name.getText().toString();
		String newLocation = location.getText().toString();

		if (newName.length() > 0) {
			user.setName(newName);
		}
		if (newLocation.length() > 0) {
			user.setLocation(newLocation);
		}
	}
}
