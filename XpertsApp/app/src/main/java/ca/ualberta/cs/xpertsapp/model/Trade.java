package ca.ualberta.cs.xpertsapp.model;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class Trade implements IObservable {
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