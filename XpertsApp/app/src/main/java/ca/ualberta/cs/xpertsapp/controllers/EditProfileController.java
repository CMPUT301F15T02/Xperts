package ca.ualberta.cs.xpertsapp.controllers;

import android.widget.EditText;

import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class EditProfileController {

	public void editProfile(EditText email, EditText name, EditText location) {

		User editedUser = UserManager.sharedManager().getUser(email.getText().toString());

	}
}
