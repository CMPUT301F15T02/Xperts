package ca.ualberta.cs.xpertsapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

/**
 * Represents a Service. Has an id, name, description, category, pictures, shareable boolean and owner.
 */
public class Service implements IObservable {
	private String id;
	private String name = "";
	private String description = "";
	private Category category = CategoryList.sharedCategoryList().otherCategory();
	private List<Bitmap> pictures = new ArrayList<Bitmap>();
	private boolean shareable = true;
	private String owner = "";

	/**
	 * Constructor
	 * @param id random uuid
	 * @param owner String of owner
	 */
	protected Service(String id, String owner) {
		this.id = id;
		this.owner = owner;
	}

	protected Service(String id) {
		this.id = id;
	}

	/**
	 * set the email of the owner
	 * @param email of the owner
	 */
	public void setOwner(String email) {
		if (!this.isEditable()) throw new AssertionError();
		this.owner = email;
	}

	/**
	 * @return the id of the service
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return the name of the service
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the new name of the service
	 */
	public void setName(String name) {
		if (!this.isEditable()) throw new AssertionError();
		this.name = name;
		this.notifyObservers();
	}

	/**
	 * @return the description of the service
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the new descripion of the service
	 */
	public void setDescription(String description) {
		if (!this.isEditable()) throw new AssertionError();
		this.description = description;
		this.notifyObservers();
	}

	/**
	 * @return the category of the service
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the new category of the service
	 */
	public void setCategory(Category category) {
		if (!this.isEditable()) throw new AssertionError();
		this.category = category;
		this.notifyObservers();
	}

	/**
	 * @return the list of images
	 */
	public List<Bitmap> getPictures() {
		return pictures;
	}

	/**
	 * @param picture the image to add
	 */
	public void addPicture(Bitmap picture) {
		if (!this.isEditable()) throw new AssertionError();
		pictures.add(picture);
		this.notifyObservers();
	}

	/**
	 * @param picture the image to remove
	 */
	public void removePicture(Bitmap picture) {
		if (!this.isEditable()) throw new AssertionError();
		pictures.remove(picture);
		this.notifyObservers();
	}

	/**
	 * @return whether or not the service can be seen by others
	 */
	public boolean isShareable() {
		return shareable;
	}

	/**
	 * @param shareable toggle sharing
	 */
	public void setShareable(boolean shareable) {
		if (!this.isEditable()) throw new AssertionError();
		this.shareable = shareable;
		this.notifyObservers();
	}

	/**
	 * @return the owner of the service
	 */
	public User getOwner() {
		return UserManager.sharedManager().getUser(this.owner);
	}

	protected boolean isEditable() {
		// Compare two different objects
		//return Constants.isTest || this.getOwner().getEmail().equals(MyApplication.getLocalUser().getEmail());
		return Constants.isTest || this.getOwner() == MyApplication.getLocalUser();
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
	@Override
	public String toString() {
		return this.name;
	}
}