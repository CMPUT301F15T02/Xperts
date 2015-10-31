package ca.ualberta.cs.xpertsapp.model;

import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;

public class ServiceManager implements IObserver {

	// Singleton
	private static ServiceManager instance = new ServiceManager();

	private ServiceManager() {}

	/// IObserver
	@Override
	public void notify(IObservable observable) {
		// TODO: Save the changes
	}
}