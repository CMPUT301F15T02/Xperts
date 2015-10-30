package ca.ualberta.cs.xpertsapp.interfaces;

public interface IObservable {
	void addObserver(IObserver o);
	void removeObserver(IObserver o);
	void notifyObservers();
}