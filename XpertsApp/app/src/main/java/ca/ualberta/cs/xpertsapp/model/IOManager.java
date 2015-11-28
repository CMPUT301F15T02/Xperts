package ca.ualberta.cs.xpertsapp.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;
import ca.ualberta.cs.xpertsapp.model.es.SearchResponse;

/**
 * Manages loading and saving data from disk and the network
 *
 * Limitation: does not delete local stuff when the server deletes it
 */
public class IOManager {
	// When writing to the server, we need to sleep to make sure the server can update before we fetch
	private static final int sleepTime = 500;

	private List<User> users = new ArrayList<User>();
	private List<Service> services = new ArrayList<Service>();
	private List<Trade> trades = new ArrayList<Trade>();

	public List<User> getUsers() {
		return users;
	}

	public List<Service> getServices() {
		return services;
	}

	public List<Trade> getTrades() {
		return trades;
	}

	/**
	 * Does Network Requests Asynchronously
	 * this is where the magic happens
	 */
	private class AsyncRequest extends AsyncTask<HttpUriRequest, Void, HttpResponse> {

		protected HttpResponse doInBackground(HttpUriRequest... request) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response;
			try {
				response = httpClient.execute(request[0]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return response;
		}
	}

	/**
	 * Push changes of local user
	 */
	public void pushToServer() {
		new Thread(new Runnable() {
			User user = MyApplication.getLocalUser();

			@Override
			public void run() {
				IOManager.sharedManager().storeData(user, Constants.serverUserExtension() + user.getEmail());
				for (Service service : user.getServices()) {
					IOManager.sharedManager().storeData(service, Constants.serverServiceExtension() + service.getID());
				}

				for (Trade trade : user.getTrades()) {
					IOManager.sharedManager().storeData(trade, Constants.serverTradeExtension() + trade.getID());
				}
			}
		}).start();
	}

	/**
	 * Cache users, services, trades
	 */
	public void pullFromServer() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				users = UserManager.sharedManager().findUsers("*");
				services = ServiceManager.sharedManager().findServices("*");
				//trades = TradeManager.sharedManager().findTrades("*");
			}
		}).start();
	}

	public <T> T fetchData(String meta, TypeToken<T> typeToken) {
		// TODO: LOOK LOCALLY
		HttpGet httpGet = new HttpGet(Constants.serverBaseURL() + meta);
		String loadedData;
		try {
			HttpResponse response = new AsyncRequest().execute(httpGet).get(); // Tier 1
			loadedData = convertStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			// TODO: load localyl
			throw new RuntimeException(e);
		}
		if (loadedData.equals("")) {
			// TODO: SHOULD NEVER HAPPEN
		}
		return (new Gson()).fromJson(loadedData, typeToken.getType());
	}

	public <T> void storeData(T data, String meta) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost addRequest = new HttpPost(Constants.serverBaseURL() + meta);
			addRequest.setEntity(new StringEntity((new Gson()).toJson(data)));
			addRequest.setHeader("Accept", "application/json");
			HttpResponse response = new AsyncRequest().execute(addRequest).get(); // Tier 1
			String status = response.getStatusLine().toString();
			Log.i("STORE STATUS: ", status);
//			Thread.sleep(sleepTime); // Sleep for 10ms because we need to let the server update
			// Cache locally
			FileOutputStream outputStream = MyApplication.getContext().openFileOutput(meta.replace('/', '_'), Context.MODE_PRIVATE);
			String outputString = "" +
					"{" +
					"	\"took\":0," +
					"	\"timed_out\":false," +
					"	\"_shards\":" +
					"		{" +
					"			\"total\":1," +
					"			\"successful\":1," +
					"			\"failed\":0" +
					"		}," +
					"	\"hits\":" +
					"		{" +
					"			\"total\":1," +
					"			\"max_score\":1," +
					"			\"hits\":" +
					"				[" +
					new StringEntity((new Gson()).toJson(data)) +
					"				]" +
					"		}" +
					"}";
			outputStream.write(outputString.getBytes());
			outputStream.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteData(String meta) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpDelete deleteRequest = new HttpDelete(Constants.serverBaseURL() + meta);
			deleteRequest.setHeader("Accept", "application/json");
			HttpResponse response = new AsyncRequest().execute(deleteRequest).get(); // Tier 1
			String status = response.getStatusLine().toString();
			Log.i("Delete Status: ", status);
//			Thread.sleep(sleepTime); // Sleep for 10ms because we need to let the server update
			MyApplication.getContext().deleteFile(meta.replace('/', '_'));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<SearchHit<T>> searchData(String searchMeta, TypeToken<SearchResponse<T>> typeToken) {
		String loadedData;
		if (Constants.isOnline) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Constants.serverBaseURL() + searchMeta);
			try {
				HttpResponse response = new AsyncRequest().execute(httpGet).get(); // Tier 1
				loadedData = convertStreamToString(response.getEntity().getContent());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			// Cache locally
			try {
				FileOutputStream outputStream = MyApplication.getContext().openFileOutput(searchMeta.replace('/', '_'), Context.MODE_PRIVATE);
				outputStream.write(loadedData.getBytes());
				outputStream.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				FileInputStream inputStream = MyApplication.getContext().openFileInput(searchMeta.replace('/', '_'));
				loadedData = convertStreamToString(inputStream);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		// Return found
		SearchResponse<T> loadedThings = (new Gson()).fromJson(loadedData, typeToken.getType());
		List<SearchHit<T>> hits = new ArrayList<SearchHit<T>>(loadedThings.getHits().getHits());
		Collections.sort(hits, new Comparator<SearchHit<T>>() {
			@Override
			public int compare(SearchHit<T> lhs, SearchHit<T> rhs) {
				return lhs.getScore().compareTo(rhs.getScore());
			}
		});
		return hits;
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