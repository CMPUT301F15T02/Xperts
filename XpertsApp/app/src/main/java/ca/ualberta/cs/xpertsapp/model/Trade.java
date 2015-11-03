package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.interfaces.TradeState;

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

	public String getID() {
		return this.id;
	}

	public User getOwner() {
		return UserManager.sharedManager().getUser(this.owner);
	}

	public User getBorrower() {
		return UserManager.sharedManager().getUser(this.borrower);
	}

	public List<Service> getOwnerServices() {
		// TODO:
		return null;
	}

	public void addOwnerService(String service) {
		if (!this.isEditable()) throw new AssertionError();
		if (ServiceManager.sharedManager().getService(service).getOwner() != this.getOwner()) throw new AssertionError();
		this.ownerServices.add(service);
		this.notifyObservers();
	}

	public void removeOwnerService(String service) {
		if (!this.isEditable()) throw new AssertionError();
		if (ServiceManager.sharedManager().getService(service).getOwner() != this.getOwner()) throw new AssertionError();
		this.ownerServices.remove(service);
		this.notifyObservers();
	}

	public List<Service> getBorrowerServices() {
		// TODO:
		return null;
	}

	public void addBorrowerService(String service) {
		if (!this.isEditable()) throw new AssertionError();
		if (ServiceManager.sharedManager().getService(service).getOwner() != this.getBorrower()) throw new AssertionError();
		this.borrowerServices.add(service);
		this.notifyObservers();
	}

	public void removeBorrowerService(String service) {
		if (!this.isEditable()) throw new AssertionError();
		if (ServiceManager.sharedManager().getService(service).getOwner() != this.getBorrower()) throw new AssertionError();
		this.borrowerServices.remove(service);
		this.notifyObservers();
	}

	public Date getProposedDate() {
		return this.proposedDate;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public boolean isCounterOffer() {
		return this.isCounterOffer;
	}

	public TradeState getState() {
		return this.state;
	}

	void setState(TradeState state) {
		this.state = state;
	}

	private boolean isEditable() {
		return this.getOwner() == UserManager.sharedManager().localUser();
	}

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

	public void commit() {
		UserManager.sharedManager().getUser(this.owner).addTrade(this.getID());
		UserManager.sharedManager().getUser(this.borrower).addTrade(this.getID());
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