package ca.ualberta.cs.xpertsapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class Service implements IObservable {

	private String id;
	private String name = "";
	private String description = "";
	private String category = "";
	private List<Bitmap> pictures;
	private boolean shareable = true;
	private String owner = "";

	Service(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setNam

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public List<Bitmap> getPictures() {
		return pictures;
	}

	public boolean isShareable() {
		return shareable;
	}

	public String getOwner() {
		return owner;
	}

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
		for (IObserver observer : this.observers) {
			observer.notify(this);
		}
	}
}