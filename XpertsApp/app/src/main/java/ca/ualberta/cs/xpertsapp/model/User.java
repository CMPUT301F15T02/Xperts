package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

/**
 * Represents a user
 */
public class User implements IObservable {
	private String email;
	private String name = "";
	private String location = "";
	private List<String> friends = new ArrayList<String>();
	private List<String> services = new ArrayList<String>();
	private List<String> trades = new ArrayList<String>();

	// Constructor

	protected User(String email) {
		this.email = email;
	}

	// Get/Set

	/**
	 * @return The Users email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @return the users name
	 */
	public String getName() {
		if (this.name.equals(""))
			return this.getEmail();
		return this.name;
	}

	/**
	 * @param name the users new name
	 */
	public void setName(String name) {
		if (!this.isEditable()) throw new AssertionError();
		this.name = name;
		this.notifyObservers();
	}

	/**
	 * @return the users location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * @param location the users new location
	 */
	public void setLocation(String location) {
		if (!this.isEditable()) throw new AssertionError();
		this.location = location;
		this.notifyObservers();
	}

	/**
	 * @return A List of the users friends
	 */
	public List<User> getFriends() {
		List<User> friends = new ArrayList<User>();
		for (String friendID : this.friends) {
			friends.add(UserManager.sharedManager().getUser(friendID));
		}
		return friends;
	}

	/**
	 * @param friend The new friend
	 */
	public void addFriend(User friend) {
		if (!this.isEditable()) throw new AssertionError();
		this.friends.add(friend.getEmail());
		this.notifyObservers();
	}

	/**
	 * @param friend The old friend
	 */
	public void removeFriend(User friend) {
		if (!this.isEditable()) throw new AssertionError();
		this.friends.remove(friend.getEmail());
		this.notifyObservers();
	}

	/**
	 * @return A List of services
	 */
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

	/**
	 * @param service The new service
	 */
	public void addService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		this.services.add(service.getID());
		ServiceManager.sharedManager().addService(service);
		ServiceManager.sharedManager().notify(service);
		this.notifyObservers();
	}

	/**
	 * @param service the old service
	 */
	public void removeService(String service) {
		if (!this.isEditable()) throw new AssertionError();
		this.services.remove(service);
		this.notifyObservers();
	}

	/**
	 * @return the number of trades proposed to the user that are waiting for their review
	 */
	public int newTrades() {
		int count = 0;
		for (Trade trade : this.getTrades()) {
			if (trade.status == 0 && trade.getOwner() != this) {
				++count;
			}
		}
		return count;
	}

	/**
	 * @return A List of trades that user has participated in
	 */
	public List<Trade> getTrades() {
		List<Trade> trades = new ArrayList<Trade>();
		for (String trade : this.trades) {
			trades.add(TradeManager.sharedManager().getTrade(trade));
		}
		return trades;
	}

	/**
	 * @param trade The new trade
	 */
	void addTrade(Trade trade) {
		this.trades.add(trade.getID());
		this.notifyObservers();
	}

	protected boolean isEditable() {
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