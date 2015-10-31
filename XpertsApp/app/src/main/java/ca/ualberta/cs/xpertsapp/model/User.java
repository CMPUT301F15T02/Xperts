package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class User implements IObservable {
	private String id;
	private String name = "";
	private String contactInfo = "";
	private List<String> friends = new ArrayList<String>();
	private List<String> services = new ArrayList<String>();
	private List<String> trades = new ArrayList<String>();

	// Constructor

	User(String id) {
		this.id = id;
	}

	// Get/Set

	public String getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		assert this.isEditable();
		this.name = name;
		this.notifyObservers();
	}

	public String getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		assert this.isEditable();
		this.contactInfo = contactInfo;
		this.notifyObservers();
	}

	public List<User> getFriends() {
		// TODO:
		return null;
	}

	public void addFriend(String id) {
		assert this.isEditable();
		this.friends.add(id);
		this.notifyObservers();
	}

	public void removeFriend(String id) {
		assert this.isEditable();
		this.friends.remove(id);
		this.notifyObservers();
	}

	public List<Service> getServices() {
		// TODO:
		return null;
	}

	public void addService(String id) {
		assert this.isEditable();
		this.services.add(id);
		this.notifyObservers();
	}

	public void removeService(String id) {
		assert this.isEditable();
		this.services.remove(id);
		this.notifyObservers();
	}

	public List<Trade> getTrades() {
		// TODO:
		return null;
	}

	public void addTrade(String id) {
		this.trades.add(id);
		this.notifyObservers();
	}

	private boolean isEditable() {
		return this == UserManager.sharedManager().localUser();
	}

	// IObservable
	private List<IObserver> observers = new ArrayList<IObserver>();

	@Override
	public void addObserver(IObserver observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (IObserver observer : this.observers) {
			observer.notify(this);
		}
	}
}