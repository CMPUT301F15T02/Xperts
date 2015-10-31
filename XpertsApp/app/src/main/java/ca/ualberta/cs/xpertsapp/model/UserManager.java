package ca.ualberta.cs.xpertsapp.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;

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
		return this.getUser(Constants.deviceUUID());
	}

	public User getUser(String id) {
		if (this.users.containsKey(id)) {
			return this.users.get(id);
		}
		String loadedData = IOManager.sharedManager().fetchData(Constants.serverUserExtension() + id);
		try {
			SearchHit<User> loadedUser = (new Gson()).fromJson(loadedData, new TypeToken<SearchHit<User>>() {
			}.getType());
			if (loadedUser.isFound()) {
				this.addUser(loadedUser.getSource());
				return loadedUser.getSource();
			} else {
				User newUser = new User(Constants.deviceUUID(), "", "");
				this.addUser(newUser);
				return newUser;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void clearCache() {
		this.users.clear();
		// Make sure to reload the local user
		this.localUser();
	}

	/// Observer

	public void notify(IObservable observable) {
		User user = (User) observable;
		IOManager.sharedManager().storeData((new Gson()).toJson(user), Constants.serverUserExtension());
	}
}