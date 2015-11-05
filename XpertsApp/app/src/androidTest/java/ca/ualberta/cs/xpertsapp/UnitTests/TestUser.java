package ca.ualberta.cs.xpertsapp.UnitTests;

import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class TestUser extends User {

	TestUser(String email, String name, String location) {
		super(email);
		this.setName(name);
		this.setLocation(location);
		this.addObserver(UserManager.sharedManager());
		this.notifyObservers();
//		IOManager.sharedManager().storeData(this, Constants.serverUserExtension() + email);
	}

	@Override
	protected boolean isEditable() {
		return true;
	}

}