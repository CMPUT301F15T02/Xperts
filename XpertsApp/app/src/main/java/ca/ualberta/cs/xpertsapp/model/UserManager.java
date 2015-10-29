package ca.ualberta.cs.xpertsapp.model;

import java.util.HashMap;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.R;

public class UserManager {
	private static UserManager instance = new UserManager();

	private UserManager() {
	}

	public static UserManager sharedUserManager() {
		return UserManager.instance;
	}

	private Map<String, User> users = new HashMap<String, User>();

	public User findUser(String email) {
		if (email.equals(R.string.id_local_user)) {
			// TODO: search for the current user
		}
		if (this.users.containsKey(email)) {
			return this.users.get(email);
		}
		// TODO: Load the user
		return null;
	}
}