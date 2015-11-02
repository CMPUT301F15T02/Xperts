package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class User implements IObservable {
	private String id;
	private String name;
	private String email;
	private List<String> friends = new ArrayList<String>();
	private List<String> services = new ArrayList<String>();
	private List<String> trades = new ArrayList<String>();

	User(String id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.isEditable()) {
			this.name = name;
			this.notifyObservers();
		}
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		if (this.isEditable()) {
			this.email = email;
			this.notifyObservers();
		}
	}

	public List<User> getFriends() {
		// TODO:
		return null;
	}

	public void addFriend(String id) {
		if (this.isEditable()) {
			this.friends.add(id);
			this.notifyObservers();
		}
	}

	public void removeFriend(String id) {
		if (this.isEditable()) {
			this.friends.remove(id);
			this.notifyObservers();
		}
	}

	public List<Service> getServices() {
		// TODO:
		return null;
	}

	public void addService(String id) {
		if (this.isEditable()) {
			this.services.add(id);
			this.notifyObservers();
		}
	}

	public void removeService(String id) {
		if (this.isEditable()) {
			this.services.remove(id);
			this.notifyObservers();
		}
	}

	public List<Trade> getTrades() {
		// TODO:
		return null;
	}

	public void addTrade(String id) {
		this.trades.add(id);
		this.notifyObservers();
	}

	public boolean isEditable() {
		return this == UserManager.sharedManager().localUser();
	}

	/// Observable

	private List<IObserver> observers = new ArrayList<IObserver>();

	public void addObserver(IObserver observer) {
		this.observers.add(observer);
	}

	public void removeObserver(IObserver observer) {
		this.observers.remove(observer);
	}

	public void notifyObservers() {
		for (IObserver observer : this.observers) {
			observer.notify(this);
		}
	}
}