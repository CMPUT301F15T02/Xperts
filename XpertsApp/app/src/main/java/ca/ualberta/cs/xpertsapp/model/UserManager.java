package ca.ualberta.cs.xpertsapp.model;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;
import ca.ualberta.cs.xpertsapp.model.es.SearchResponse;

/**
 * Manages the loaded users to allow circular references without getting stuck in a loading loop
 */
public class UserManager implements IObserver {
	private Map<String, User> users = new HashMap<String, User>();

	// Get/Set

	/**
	 * @return A List of loaded Users
	 */
	public List<User> getUsers() {
		return new ArrayList<User>(this.users.values());
	}

	/**
	 * @param user Registers this as an observer on User and adds it to the List
	 */
	private void addUser(User user) {
		user.addObserver(this);
		this.users.put(user.getEmail(), user);
	}

	/**
	 * @param email The email of the user
	 * @return The User with that email or null
	 */
	public User getUser(String email) {
		// If we have the user loaded
		if (this.users.containsKey(email)) {
			return this.users.get(email);
		}
		// TODO: Only return a user if one existed
		try {
			SearchHit<User> loadedUser = IOManager.sharedManager().fetchData(Constants.serverUserExtension() + email, new TypeToken<SearchHit<User>>() {
			});
			if (loadedUser.isFound()) {
				this.addUser(loadedUser.getSource());
				return loadedUser.getSource();
			} else {
				return null;
			}
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return The User that is currently signed in or null
	 */
	public User localUser() {
		String email = MyApplication.getPreferences().getString("email", null);
		if (email == null) {
			return null;
		}
		User user = this.getUser(email);
		if (user == null) {
			user = this.registerUser(email);
		}
		return user;
	}

	/**
	 * @param email The email to register the user as
	 * @return The registered user or null if the email is taken
	 */
	public User registerUser(String email) {
		User foundUser = this.getUser(email);
		if (foundUser == null) {
			User newUser = new User(email);
			this.addUser(newUser);
			return newUser;
		}
		return null;
	}

	/**
	 * Get a list of users sorted by match relevance
	 * <p/>
	 * Uses the url [...]/_search?q='%s'
	 *
	 * @param meta What to search for
	 * @return The list of matching users with the most relevant first
	 */
	public List<User> findUsers(String meta) {
		List<SearchHit<User>> found = IOManager.sharedManager().searchData(Constants.serverUserExtension() + Constants.serverSearchExtension() + meta, new TypeToken<SearchResponse<User>>() {
		});
		List<User> users = new ArrayList<User>();
		for (SearchHit<User> user : found) {
			users.add(this.getUser(user.getSource().getEmail()));
		}
		return users;
	}

	/**
	 * Clears the local cache of loaded users
	 */
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
		IOManager.sharedManager().storeData(observable, Constants.serverUserExtension() + ((User) observable).getEmail());
	}
}