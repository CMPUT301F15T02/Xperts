package ca.ualberta.cs.xpertsapp.model;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;

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

	private void addUser(User user) {
		user.addObserver(this);
		this.users.put(user.getID(), user);
	}

	public User getUser(String id) {
		// If we have the user loaded
		if (this.users.containsKey(id)) {
			return this.users.get(id);
		}
		// TODO:
		try {
			String loadedData = IOManager.sharedManager().fetchData(Constants.serverUserExtension() + id);
			SearchHit<User> loadedUser = (new Gson()).fromJson(loadedData, new TypeToken<SearchHit<User>>() {
			}.getType());
			if (loadedUser.isFound()) {
				this.addUser(loadedUser.getSource());
				return loadedUser.getSource();
			} else {
				User newUser = new User(Constants.deviceUUID());
				this.addUser(newUser);
				return newUser;
			}
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
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
		// TODO:
	}

	// Singleton
	private static UserManager instance = new UserManager();

	private UserManager() {
	}

	public static UserManager sharedManager() {
		return UserManager.instance;
	}

	// IObserver
	@Override
	public void notify(IObservable observable) {
		// TODO:
	}
}