package ca.ualberta.cs.xpertsapp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

import ca.ualberta.cs.xpertsapp.model.es.SearchHit;

/**
 * Manages loading and saving data from disk and the network
 */
public class IOManager {
	public <T> T fetchData(String meta) {
		// TODO: LOOK LOCALLY
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(Constants.serverBaseURL() + meta);
		String loadedData = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			loadedData = convertStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			// TODO:
			throw new RuntimeException(e);
		}
		if (loadedData.equals("")) {
			// TODO: SHOULD NEVER HAPPEN
		}
		SearchHit<T> loadedThing = (new Gson()).fromJson(loadedData, new TypeToken<SearchHit<T>>() {
		}.getType());
		if (loadedThing.isFound()) {
			return loadedThing.getSource();
		} else {
			// TODO: SHOULD NEVER HAPPEN
		}
		return null;
	}

	public <T> void storeData(T data, String meta) {
		// TODO: STORE LOCALLY
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost addRequest = new HttpPost(Constants.serverBaseURL() + meta);
			addRequest.setEntity(new StringEntity((new Gson()).toJson(data)));
			addRequest.setHeader("Accept", "application/json");
			HttpResponse response = httpClient.execute(addRequest);
			String status = response.getStatusLine().toString();
			Log.i("STATUS: ", status);
		} catch (Exception e) {
			// TODO:
			throw new RuntimeException(e);
		}
	}

	// Helper
	private static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	// Singleton
	private static IOManager instance = new IOManager();

	private IOManager() {
	}

	public static IOManager sharedManager() {
		return IOManager.instance;
	}
}