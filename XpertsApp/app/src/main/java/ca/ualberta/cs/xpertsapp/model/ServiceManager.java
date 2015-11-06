package ca.ualberta.cs.xpertsapp.model;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.interfaces.IObservable;
import ca.ualberta.cs.xpertsapp.interfaces.IObserver;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;
import ca.ualberta.cs.xpertsapp.model.es.SearchResponse;

/**
 * Manages services
 */
public class ServiceManager implements IObserver {

	private Map<String, Service> services = new HashMap<String, Service>();

	/**
	 * @param id the id of the service to look for
	 * @return the service or null if it doesn't exist
	 */
	public Service getService(String id) {
		// If service is loaded
		if (this.services.containsKey(id)) {
			return this.services.get(id);
		}
		// TODO:
		try {
			SearchHit<Service> loadedService = IOManager.sharedManager().fetchData(Constants.serverServiceExtension() + id, new TypeToken<SearchHit<Service>>() {
			});
			if (loadedService.isFound()) {
				this.addService(loadedService.getSource());
				return loadedService.getSource();
			} else {
				// TODO:
				return null;
			}
		} catch (JsonIOException e) {
			throw new RuntimeException(e);
		} catch (JsonSyntaxException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the list of loaded services
	 */
	public List<Service> getServices() {
		return new ArrayList<Service>(this.services.values());
	}

	void addService(Service service) {
		service.addObserver(this);
		this.services.put(service.getID(), service);
	}

	/**
	 * @return create a new service attached to the local user
	 */
	public Service newService() {
		return new Service(UUID.randomUUID().toString(), MyApplication.getLocalUser().getEmail());
	}

	void removeService(Service service) {
		this.services.remove(service.getID());
		service.removeObserver(this);
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension()+service.getID());
	}

	/**
	 * clear the loaded services
	 */
	public void clearCache() {
		this.services.clear();
	}

	/**
	 * @param meta the meta to search for
	 * @return the list of found services
	 */
	public List<Service> findServices(String meta) {
		List<SearchHit<Service>> found = IOManager.sharedManager().searchData(Constants.serverServiceExtension() + Constants.serverSearchExtension() + meta, new TypeToken<SearchResponse<Service>>() {
		});
		List<Service> services = new ArrayList<Service>();
		for (SearchHit<Service> service : found) {
			services.add(this.getService(service.getSource().getID()));
		}
		return services;
	}

	/**
	 * @param meta the meta to search for
	 * @return the list of services that belong to friends of local user
	 */
	public List<Service> findServicesOfFriends(String meta) {
		List<SearchHit<Service>> found = IOManager.sharedManager().searchData(Constants.serverServiceExtension() + Constants.serverSearchExtension() + meta, new TypeToken<SearchResponse<Service>>() {
		});
		List<Service> services = new ArrayList<Service>();
		for (SearchHit<Service> service : found) {
			if (MyApplication.getLocalUser().getFriends().contains(UserManager.sharedManager().getUser(service.getSource().getOwner().getEmail()))) {
				services.add(this.getService(service.getSource().getID()));
			}
		}
		return services;
	}

	// Singleton
	private static ServiceManager instance = new ServiceManager();

	private ServiceManager() {
	}

	public static ServiceManager sharedManager() {
		return ServiceManager.instance;
	}

	/// IObserver
	@Override
	public void notify(IObservable observable) {
		// TODO:
		IOManager.sharedManager().storeData(observable, Constants.serverServiceExtension() + ((Service) observable).getID());
	}
}