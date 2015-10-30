package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class UserManager implements IObserver {
	public static UserManager instance = new UserManager();

	private UserManager() {
	}

	/**
	 * @return The singleton instance of UserManager
	 */
	public static UserManager sharedManager() {
		return UserManager.instance;
	}

	private Map<String, User> users = new HashMap<String, User>();

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		for (User user : this.users.values()) {
			users.add(user);
		}
		return users;
	}

	private void addUser(User user) {
		user.addObserver(this);
		this.users.put(user.getID(), user);
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
		String loadedData = IOManager.sharedManager().fetchData("");
		if (loadedData.equals(Constants.nullDataString())) {
			User newUser = new User(Constants.deviceUUID(), "", "");
			this.addUser(newUser);
			return newUser;
		}
		// TODO: Load loaded data
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