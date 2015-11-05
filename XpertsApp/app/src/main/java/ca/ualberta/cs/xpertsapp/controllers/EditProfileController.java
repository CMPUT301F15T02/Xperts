package ca.ualberta.cs.xpertsapp.controllers;

import android.widget.EditText;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.User;

public class EditProfileController {

	//can you edit email?
	public void editProfile(EditText email, EditText name, EditText location) {
		User user = MyApplication.getLocalUser();
		user.setName(name.getText().toString());
		user.setLocation(location.getText().toString());
	}
}
