package ca.ualberta.cs.xpertsapp.interfaces;

public interface IObservable {
	void addObserver(IObserver observer);

	void removeObserver(IObserver observer);

	void notifyObservers();
}