package ca.ualberta.cs.xpertsapp.controllers;

import android.widget.EditText;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.User;

public class EditProfileController {

	//can you edit email?

	/**
	 *
	 * @param email we wont use this
	 * @param name this is a string name for a user. A user may want to edit their name when they make a typo.
	 * @param location this is a description of were a user is in the world. This way User's can trade with people close to them
	 */

	public void editProfile(EditText email, EditText name, EditText location) {
		User user = MyApplication.getLocalUser();
		//String newEmail = email.getText().toString();
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
