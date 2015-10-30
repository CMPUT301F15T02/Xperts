package ca.ualberta.cs.xpertsapp.model;

/**
 * Manages loading and saving data from disk and the network
 */
public class IOManager {
	/**
	 * The singleton instance of IOManager
	 */
	private static IOManager instance = new IOManager();

	/**
	 * Private constructor so we only ever have one of it
	 */
	private IOManager() {
	}

	/**
	 * @return The singleton instance of IOManager
	 */
	public static IOManager sharedManager() {
		return IOManager.instance;
	}

	/**
	 * @param meta The data that dictates what should be looked for
	 * @return The found data or Constants.nullDataString() if nothing was found
	 */
	public String fetchData(String meta) {
		// TODO:
		return Constants.nullDataString();
	}

	/**
	 * @param data The data to save
	 * @param meta Information about where to save the data so it can be loaded again
	 */
	public void storeData(String data, String meta) {
		// TODO:
	}
}