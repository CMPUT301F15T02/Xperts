package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

/**
 * Represents a Trade of services between two Users. A trade will consist of 1 item from the owner's inventory
 * and 0 or more items from the borrower's inventory.
 * Counter trades are initialized with the items from the original trade and isCounterOffer is set to true.
 */
public class Trade implements IObservable {
	private String id;
	private boolean isCounterOffer;
	private String owner;
	private String borrower;
	private List<String> borrowerServices = new ArrayList<String>();
	private List<String> ownerServices = new ArrayList<String>();
	private final Date proposedDate = new Date();
	private Date lastUpdatedDate = new Date();
	int status = 0;
	private transient TradeState state = new TradeStatePending();

	// Constructor

	/**
	 * Constructor for a trade. These parameters must not be null.
	 * @param id
	 * @param isCounterOffer true if is a counter offer, false if not.
	 * @param owner String identifying the owner of the service the borrower wants.
	 * @param borrower String identifying the borrower of the service.
	 */
	Trade(String id, boolean isCounterOffer, String owner, String borrower) {
		this.id = id;
		this.isCounterOffer = isCounterOffer;
		this.owner = owner;
		this.borrower = borrower;
	}

	// Get/Set

	/**
	 * @return The Trades id
	 */
	public String getID() {
		return this.id;
	}

	/**
	 * @return The User who will accept or decline the trade.
	 */
	public User getOwner() {
		return UserManager.sharedManager().getUser(this.owner);
	}

	/**
	 * @return The User who created the trade
	 */
	public User getBorrower() {
		return UserManager.sharedManager().getUser(this.borrower);
	}

	/**
	 * This should only ever have one item in the list for non counter trades
	 * @return The services the borrower wants
	 */
	public List<Service> getOwnerServices() {
		// TODO:
		List<Service> services = new ArrayList<Service>();
		ServiceManager sm = ServiceManager.sharedManager();
		for (String id : ownerServices) {
			services.add(sm.getService(id));
		}
		return services;
	}

	/**
	 * Adds a service that the borrower wants.
	 * @param service The owner's service the borrower wants.
	 */
	public void addOwnerService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		if (service.getOwner() != this.getBorrower()) throw new AssertionError();
		this.ownerServices.add(service.getID());
		this.notifyObservers();
	}

	/**
	 * Removes an owner's service from the trade.
	 * @param service The owner's service to remove from the trade
	 */
	public void removeOwnerService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		if (service.getOwner() != this.getBorrower()) throw new AssertionError();
		this.ownerServices.remove(service.getID());
		this.notifyObservers();
	}

	/**
	 * @return The services the initiator offers
	 */
	public List<Service> getBorrowerServices() {
		// TODO:
		List<Service> services = new ArrayList<Service>();
		ServiceManager sm = ServiceManager.sharedManager();
		for (String id : borrowerServices) {
			services.add(sm.getService(id));
		}
		return services;
	}

	/**
	 * Adds a service the borrower will trade.
	 * @param service The borrower's service to add to the trade.
	 */
	public void addBorrowerService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		if (service.getOwner() != this.getOwner()) throw new AssertionError();
		this.borrowerServices.add(service.getID());
		this.notifyObservers();
	}

	/**
	 * Removes the borrower's service from the trade.
	 * @param service The borrower's service to remove
	 */
	public void removeBorrowerService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		if (service.getOwner() != this.getOwner()) throw new AssertionError();
		this.borrowerServices.remove(service.getID());
		this.notifyObservers();
	}

	/**
	 * @return the date the trade was created
	 */
	public Date getProposedDate() {
		return this.proposedDate;
	}

	/**
	 * @return the date the trade was last modified
	 */
	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	/**
	 * @return if the trade is a counter trade
	 */
	public boolean isCounterOffer() {
		return this.isCounterOffer;
	}

	/**
	 * The states could be accepted, cancelled, declined, or pending.
	 * @return the state of the trade
	 */
	public TradeState getState() {
		return this.state;
	}

	void setState(TradeState state) {
		this.state = state;
	}

	/**
	 * status = 0 -> pending
	 * status = 1 -> accepted
	 * status = 2 -> cancelled
	 * status = 3 -> declined
	 * @return an int saying the state the trade is in
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return true if editable, false if not
	 */
	private boolean isEditable() {
		return this.getBorrower() == MyApplication.getLocalUser();
	}

	/**
	 * The user wants to accept the incoming trade. The state is changed.
	 */
	public void accept() {
		if (this.state == null) {
			if (this.status == 0) {
				this.state = new TradeStatePending();
			} else if (this.status == 1) {
				this.state = new TradeStateAccepted();
			} else if (this.status == 2) {
				this.state = new TradeStateCancelled();
			} else if (this.status == 3) {
				this.state = new TradeStateDeclined();
			} else {
				throw new RuntimeException("Invalid status");
			}
		}
		this.state.accept(this);
	}

	/**
	 * The user wants to decline the incoming trade. The state is changed.
	 */
	public void decline() {
		if (this.state == null) {
			if (this.status == 0) {
				this.state = new TradeStatePending();
			} else if (this.status == 1) {
				this.state = new TradeStateAccepted();
			} else if (this.status == 2) {
				this.state = new TradeStateCancelled();
			} else if (this.status == 3) {
				this.state = new TradeStateDeclined();
			} else {
				throw new RuntimeException("Invalid status");
			}
		}
		this.state.decline(this);
	}

	/**
	 * The sender wants to cancel the outgoing trade. The state is changed.
	 */
	public void cancel() {
		if (this.state == null) {
			if (this.status == 0) {
				this.state = new TradeStatePending();
			} else if (this.status == 1) {
				this.state = new TradeStateAccepted();
			} else if (this.status == 2) {
				this.state = new TradeStateCancelled();
			} else if (this.status == 3) {
				this.state = new TradeStateDeclined();
			} else {
				throw new RuntimeException("Invalid status");
			}
		}
		this.state.cancel(this);
	}

	/**
	 * The trade parameters are set up and the trade should be sent. This notifies observers
	 * @see #notifyObservers()
	 */
	public void commit() {
		UserManager.sharedManager().getUser(this.owner).addTrade(this);
		UserManager.sharedManager().getUser(this.borrower).addTrade(this);
		TradeManager.sharedManager().addTrade(this);
		this.notifyObservers();
	}

	// IObservable
	private transient List<IObserver> observers = new ArrayList<IObserver>();

	@Override
	/** Add an observer */
	public void addObserver(IObserver observer) {
		if (this.observers == null) {
			this.observers = new ArrayList<IObserver>();
		}
		this.observers.add(observer);
	}

	/** Remove the observer */
	public void removeObserver(IObserver observer) {
		this.observers.remove(observer);
	}

	@Override
	/** notify all observers of a change */
	public void notifyObservers() {
		this.lastUpdatedDate = new Date();
		for (IObserver observer : this.observers) {
			observer.notify(this);
		}
	}


}