package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

/**
 * This class is used to internally represent a real life user of this digital application for android.  Some functions run in O(1) time (maybe) and others do not.
 * Pl0x only have one instance of user representing a person at any given time.
 *
 * Use the user managers to manage users plz. DO NOT CREATE USERS ON YOUR OWN
 */
public class User implements IObservable {
	private String email;
	private String name = "";
	private String location = "";
	private Boolean downloadsEnabled = false;

	private List<String> friends = new ArrayList<String>();
	private List<String> services = new ArrayList<String>();
	private List<String> trades = new ArrayList<String>();

	// Constructor

	protected User(String email) {
		this.email = email;
	}

	// Get/Set

	/**
	 * Get the email
	 * @return The Users email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * get the name of the user
	 * @return the users name
	 */
	public String getName() {
		if (this.name.equals(""))
			return this.getEmail();
		return this.name;
	}

	/**
	 * set the name of the user
	 * @param name the users new name
	 */
	public void setName(String name) {
		if (!this.isEditable()) throw new AssertionError();
		this.name = name;
		this.notifyObservers();
	}

	/**
	 * get the location of the user
	 * @return the users location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * set the location of the user
	 * @param location the users new location
	 */
	public void setLocation(String location) {
		if (!this.isEditable()) throw new AssertionError();
		this.location = location;
		this.notifyObservers();
	}

	/**
	 * @return  true if downloads are enabled, false otherwise
	 */
	public Boolean getDownloadsEnabled() {
		return downloadsEnabled;
	}

	/**
	 * @param  downloadsEnabled boolean representing if downloads are enabled
	 */
	public void setDownloadsEnabled(Boolean downloadsEnabled) {
		this.downloadsEnabled = downloadsEnabled;
	}

	/**
	 * get a list of friends that I have (spoiler: its probably zero )
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
	 * add a friends
	 * @param friend The new friend
	 */
	public void addFriend(User friend) {
		if (!this.isEditable()) throw new AssertionError();
		this.friends.add(friend.getEmail());
		this.notifyObservers();
	}

	/**
	 * remove a friend from the user
	 * @param friend The old friend
	 */
	public void removeFriend(User friend) {
		if (!this.isEditable()) throw new AssertionError();
		this.friends.remove(friend.getEmail());
		this.notifyObservers();
	}

	/**
	 * get a list of services the user owns
	 * @return A List of services
	 */
	public List<Service> getServices() {
		List<Service> services = new ArrayList<Service>();
		for (String service : this.services) {
			Service s = ServiceManager.sharedManager().getService(service);
			if (this.isOwner() || s.isShareable()) {
				services.add(s);
			}
		}
		return services;
	}

	/**
	 * add a new service to the user
	 * @param service The new service
	 */
	public void addService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		this.services.add(service.getID());
		if(!service.getOwner().equals(this)) {
			service.setOwner(this.email);
		}
		ServiceManager.sharedManager().addService(service);
		ServiceManager.sharedManager().notify(service);
		this.notifyObservers();
	}

	/**
	 * remove service form the use. the service still exists, but is unlinked
	 * @param service the old service
	 */
	public void removeService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		this.services.remove(service.getID());

		ServiceManager.sharedManager().removeService(service);
		this.notifyObservers();
	}

	/**
	 * gets the number of trades that are 'new' (the user has to take an action)
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
	 * get a list of trades that the user takes part in
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
	 * add a new trade that the user will maybe be participating in.
	 * @param trade The new trade
	 */
	public void addTrade(Trade trade) {
		this.trades.add(trade.getID());
		TradeManager.sharedManager().addTrade(trade);
		TradeManager.sharedManager().notify(trade);
		this.notifyObservers();
	}

	/**
	 * remove trade from use. The trade still exists but is unlinked.
	 * @param trade the trade to be removed
	 */
	public void removeTrade(Trade trade) {
		this.trades.remove(trade.getID());
		trade.getOwner().removeTradeFromOwner(trade);
		TradeManager.sharedManager().removeTrade(trade);
		this.notifyObservers();
	}

	/**
	 * This is called from removeTrade() and is used to remove the trade from the owner as well.
	 * @param trade the trade that's being removed
	 */
	public void removeTradeFromOwner(Trade trade) {
		//TODO
		this.trades.remove(trade.getID());
		TradeManager.sharedManager().removeTrade(trade);
		this.notifyObservers();
	}

	/**
	 * @return whether the active user has permission to edit this user. returns true for tests.
	 */
	protected boolean isEditable() {
		return Constants.isTest || this == MyApplication.getLocalUser();
	}

	/**
	 * @return if the active user is this user
	 */
	protected boolean isOwner(){return this == MyApplication.getLocalUser();}

	/**
	 * @see IObservable
	 */
	private transient List<IObserver> observers = new ArrayList<IObserver>();

	@Override
	/** add an observer to the pool*/
	public void addObserver(IObserver observer) {
		if (this.observers == null) {
			this.observers = new ArrayList<IObserver>();
		}
		this.observers.add(observer);
	}

	@Override
	/** remove the obsever from the pool */
	public void removeObserver(IObserver observer) {
		this.observers.remove(observer);
	}

	@Override
	/** get told when something changes */
	public void notifyObservers() {
		for (IObserver observer : this.observers) {
			observer.notify(this);
		}
	}
}