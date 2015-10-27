package ca.ualberta.cs.xpertsapp.interfaces;

public interface Observable {
	public void addObserver(Observer o);
	public void deleteObserver(Observer o);
	public void notifyObservers();
}
