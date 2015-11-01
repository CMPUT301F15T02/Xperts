package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class User implements IObservable {
	private String id;
	private String name = "";
	private String contactInfo = "";
	private String location = "";
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
		if (!this.isEditable()) throw new AssertionError();
		this.name = name;
		this.notifyObservers();
	}

	public String getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		if (!this.isEditable()) throw new AssertionError();
		this.contactInfo = contactInfo;
		this.notifyObservers();
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		if (!this.isEditable()) throw new AssertionError();
		this.location = location;
		this.notifyObservers();
	}

	public List<User> getFriends() {
		List<User> friends = new ArrayList<User>();
		for (String friendID : this.friends) {
			friends.add(UserManager.sharedManager().getUser(friendID));
		}
		return friends;
	}

	public void addFriend(String friend) {
		if (!this.isEditable()) throw new AssertionError();
		this.friends.add(friend);
		this.notifyObservers();
	}

	public void removeFriend(String friend) {
		if (!this.isEditable()) throw new AssertionError();
		this.friends.remove(friend);
		this.notifyObservers();
	}

	public List<Service> getServices() {
		List<Service> services = new ArrayList<Service>();
		for (String service : this.services) {
			Service s = ServiceManager.sharedManager().getService(service);
			if (this.isEditable() || s.isShareable()) {
				services.add(s);
			}
		}
		return services;
	}

	public void addService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		this.services.add(service.getID());
		ServiceManager.sharedManager().addService(service);
		ServiceManager.sharedManager().notify(service);
		this.notifyObservers();
	}

	public void removeService(String service) {
		if (!this.isEditable()) throw new AssertionError();
		this.services.remove(service);
		this.notifyObservers();
	}

	public List<Trade> getTrades() {
		List<Trade> trades = new ArrayList<Trade>();
		for (String trade : this.trades) {
			trades.add(TradeManager.sharedManager().getTrade(trade));
		}
		return trades;
	}

	void addTrade(String trade) {
		this.trades.add(trade);
		this.notifyObservers();
	}

	private boolean isEditable() {
		return this == UserManager.sharedManager().localUser();
	}

	// IObservable
	private transient List<IObserver> observers = new ArrayList<IObserver>();

	@Override
	public void addObserver(IObserver observer) {
		if (this.observers == null) {
			this.observers = new ArrayList<IObserver>();
		}
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