package ca.ualberta.cs.xpertsapp.UnitTests;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;

public class TestUser extends User {
	TestUser(String email, String name, String location) {
		super(email);
		this.setName(name);
		this.setLocation(location);
		IOManager.sharedManager().storeData(this, Constants.serverUserExtension() + this.getEmail());
	}
}