package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

/**
 * Represents a Trade between two Users
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
	 * @return The User who created the trade
	 */
	public User getOwner() {
		return UserManager.sharedManager().getUser(this.owner);
	}

	/**
	 * @return The User who is receiving the trade
	 */
	public User getBorrower() {
		return UserManager.sharedManager().getUser(this.borrower);
	}

	/**
	 * @return The list of services the Initiator wants to trade away
	 */
	public List<Service> getOwnerServices() {
		// TODO:
		return null;
	}

	/**
	 * @param service The service to trade away
	 */
	public void addOwnerService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		if (service.getOwner() != this.getOwner()) throw new AssertionError();
		this.ownerServices.add(service.getID());
		this.notifyObservers();
	}

	/**
	 * @param service The service not to trade
	 */
	public void removeOwnerService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		if (service.getOwner() != this.getOwner()) throw new AssertionError();
		this.ownerServices.remove(service.getID());
		this.notifyObservers();
	}

	/**
	 * @return The services the initiator wants
	 */
	public List<Service> getBorrowerServices() {
		// TODO:
		return null;
	}

	/**
	 * @param service The service to add
	 */
	public void addBorrowerService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		if (service.getOwner() != this.getBorrower()) throw new AssertionError();
		this.borrowerServices.add(service.getID());
		this.notifyObservers();
	}

	/**
	 * @param service The service to remove
	 */
	public void removeBorrowerService(Service service) {
		if (!this.isEditable()) throw new AssertionError();
		if (service.getOwner() != this.getBorrower()) throw new AssertionError();
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
	 * @return if the trade is a counter
	 */
	public boolean isCounterOffer() {
		return this.isCounterOffer;
	}

	/**
	 * @return the state of the trade
	 */
	public TradeState getState() {
		return this.state;
	}

	void setState(TradeState state) {
		this.state = state;
	}

	private boolean isEditable() {
		return this.getOwner() == UserManager.sharedManager().localUser();
	}

	/**
	 * The user wants to accept the incoming trade
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
	 * The user wants to decline the incoming trade
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
	 * The sender wants to cancel the outgoing trade
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
	 * The trade parameters are set up and the trade should be sent
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
	public void addObserver(IObserver observer) {
		if (this.observers == null) {
			this.observers = new ArrayList<IObserver>();
		}
		this.observers.add(observer);
	}

	public void removeObserver(IObserver observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		this.lastUpdatedDate = new Date();
		for (IObserver observer : this.observers) {
			observer.notify(this);
		}
	}


}