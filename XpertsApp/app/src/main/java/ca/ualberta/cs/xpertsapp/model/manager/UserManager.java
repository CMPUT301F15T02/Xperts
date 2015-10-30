package ca.ualberta.cs.xpertsapp.model.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.User;

public class UserManager implements IObserver {
	public static UserManager instance = new UserManager();

	public static UserManager sharedUserManager() {
		return UserManager.instance;
	}

	private UserManager() {
	}

	private Map<String, User> users = new HashMap<String, User>();

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		for (User user : this.users.values()) {
			users.add(user);
		}
		return users;
	}

	public User localUser() {
		return this.getUser(Constants.localUserString());
	}

	public User getUser(String id) {
		if (id.equals(Constants.localUserString())) {
			return this.getUser(Constants.deviceUUID());
		}
		if (this.users.containsKey(id)) {
			return this.users.get(id);
		}
		// TODO: Load from IOManager
		return null;
	}

	public void clearCache() {
		this.users.clear();
		// Make sure to reload the local user
		this.getUser(Constants.localUserString());
	}

	/// Observer

	public void notify(IObservable observable) {
		// TODO: Save the changes
	}
}