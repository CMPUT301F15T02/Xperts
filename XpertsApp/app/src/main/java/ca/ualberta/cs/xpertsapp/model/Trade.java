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
	private TradeState state = new TradeStatePending();

	// Constructor

	Trade(String id, boolean isCounterOffer, String owner, String borrower) {
		this.id = id;
		this.isCounterOffer = isCounterOffer;
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
		assert this.isEditable();
		this.ownerServices.add(service);
		this.notifyObservers();
	}

	public void removeOwnerService(String service) {
		assert this.isEditable();
		this.ownerServices.remove(service);
		this.notifyObservers();
	}

	public List<Service> getBorrowerServices() {
		// TODO:
		return null;
	}

	public void addBorrowerService(String service) {
		assert this.isEditable();
		this.borrowerServices.add(service);
		this.notifyObservers();
	}

	public void removeBorrowerService(String service) {
		assert this.isEditable();
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

	public boolean isEditable() {
		return this.getOwner() == UserManager.sharedManager().localUser();
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
		this.lastUpdatedDate = new Date();
		for (IObserver observer : this.observers) {
			this.observers.notify();
		}
	}
}