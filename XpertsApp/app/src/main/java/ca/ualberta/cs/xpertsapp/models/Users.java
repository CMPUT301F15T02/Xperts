package ca.ualberta.cs.xpertsapp.models;

import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.interfaces.Observable;
import ca.ualberta.cs.xpertsapp.interfaces.Observer;

// Code from cmput301's lab
public class Users extends ArrayList<User> implements Observable {

	private volatile ArrayList<Observer> observers = new ArrayList<Observer>();
	private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t02/user/";
	private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t02/user/_search";

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void deleteObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.notifyUpdated(this);
		}
	}

	public String getResourceUrl() {
		return RESOURCE_URL;
	}

	public String getSearchUrl() {
		return SEARCH_URL;
	}

	/**
	 * Java wants this, we don't need it for Gson/Json
	 */
	private static final long serialVersionUID = 3199561696102797345L;

}
