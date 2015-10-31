package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class UserManager implements IObserver {
	private Map<String, User> users = new HashMap<String, User>();

	// Get/Set
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		for (User user : this.users.values()) {
			users.add(user);
		}
		return users;
	}

	public User getUser(String id) {
		// If we have the user loaded
		if (this.users.containsKey(id)) {
			return this.users.get(id);
		}
		// TODO:
		return null;
	}

	public User localUser() {
		return this.getUser(Constants.deviceUUID());
	}

	public List<User> findUsers(String meta) {
		// TODO:
		return null;
	}

	public void clearCache() {
		this.users.clear();
		this.localUser();
	}

	// Singleton
	private static UserManager instance = new UserManager();

	private UserManager() {
	}

	public UserManager sharedManager() {
		return UserManager.instance;
	}

	// IObserver
	@Override
	public void notify(IObservable observable) {
		// TODO:
	}
}