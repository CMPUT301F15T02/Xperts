package ca.ualberta.cs.xpertsapp.model;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

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
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(Constants.serverBaseURL() + meta);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			return convertStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// TODO: Look locally
	}

	/**
	 * @param data The data to save
	 * @param meta Information about where to save the data so it can be loaded again
	 */
	public void storeData(String data, String meta) {
		// TODO: Save remotely
		// TODO: Save locally
	}

	private static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}