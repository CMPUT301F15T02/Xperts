package ca.ualberta.cs.xpertsapp.model;

/**
 * Manages loading and saving data from disk and the network
 */
public class IOManager {
	public String fetchData(String meta) {
		// TODO:
		return null;
	}

	public void storeData(String data, String meta) {
		// TODO:
	}

	// Singleton
	private static IOManager instance = new IOManager();

	private IOManager() {
	}

	public static IOManager sharedManager() {
		return IOManager.instance;
	}
}