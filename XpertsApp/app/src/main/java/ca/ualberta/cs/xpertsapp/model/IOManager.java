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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.es.SearchHit;
import ca.ualberta.cs.xpertsapp.model.es.SearchResponse;

/**
 * Manages loading and saving data from disk and the network
 * <p/>
 * Limitation: does not delete local stuff when the server deletes it
 */
public class IOManager {
	// When writing to the server, we need to sleep to make sure the server can update before we fetch
	private static final int sleepTime = 500;
	// Singleton
	private static IOManager instance = new IOManager();

	private IOManager() {
	}

	// Helper
	private static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static IOManager sharedManager() {
		return IOManager.instance;
	}

	public <T> T fetchData(String meta, TypeToken<T> typeToken) {
		String loadedData;
		if (Constants.allowOnline) {
			HttpGet httpGet = new HttpGet(Constants.serverBaseURL() + meta);
			try {
				HttpResponse response = new AsyncRequest().execute(httpGet).get(); // Tier 1
				loadedData = convertStreamToString(response.getEntity().getContent());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			// Cache locally
			try {
				FileOutputStream outputStream = MyApplication.getContext().openFileOutput(meta.replace('/', '_'), Context.MODE_PRIVATE);
				outputStream.write(loadedData.getBytes());
				outputStream.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				FileInputStream inputStream = MyApplication.getContext().openFileInput(meta.replace('/', '_'));
				loadedData = convertStreamToString(inputStream);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
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
		if (Constants.allowOnline) {
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
				FileOutputStream outputStream = MyApplication.getContext().openFileOutput("search_" + searchMeta.replace('/', '_'), Context.MODE_PRIVATE);
				outputStream.write(loadedData.getBytes());
				outputStream.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				FileInputStream inputStream = MyApplication.getContext().openFileInput("search_" + searchMeta.replace('/', '_'));
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

	public void clearCache() {
		for (File file : MyApplication.getContext().getFilesDir().listFiles()) {
			if (file.getAbsolutePath() != null) {
				file.delete();
			}
		}
	}

	/**
	 * Does Network Requests Asynchronously
	 * this is where the magic happens
	 */
	private class AsyncRequest extends AsyncTask<HttpUriRequest, Void, HttpResponse> {

		protected HttpResponse doInBackground(HttpUriRequest... request) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = null;
			try {
				response = httpClient.execute(request[0]);
			} catch (Exception e) {
				//throw new RuntimeException(e);
			}
			return response;
		}
	}

	// Read only friends, services or trades of local user from local storage
	public <T> ArrayList<T> loadFromFile(Context context, TypeToken<ArrayList<T>> typeToken, String filename) {
		ArrayList<T> objects = new ArrayList<T>();
		try {
			FileInputStream fis = context.openFileInput(filename);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson = new Gson();
			objects = gson.fromJson(in, typeToken.getType());

		} catch (FileNotFoundException e) {
			return objects;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return objects;
	}

	// Write to local storage
	public <T> void writeToFile(ArrayList<T> objects, Context context, String filename) {
		try {
			FileOutputStream fos = context.openFileOutput(filename, 0);
			OutputStreamWriter writer = new OutputStreamWriter(fos);
			Gson gson = new Gson();
			gson.toJson(objects, writer);
			Log.d("gson", gson.toJson(objects));
			writer.flush();
			fos.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Replace a user from users
	// Have to remove then re add
	public void writeUserToFile(User user) {
		ArrayList<User> diskUsers = loadFromFile(MyApplication.getContext(), new TypeToken<ArrayList<User>>() {
		}, Constants.diskUser);
		for (User u : diskUsers) {
			if (u.getEmail().equals(user.getEmail())) {
				diskUsers.remove(u);
				break;
			}
		}
		diskUsers.add(user);
		//Constants.usersSync = true;
		writeToFile(diskUsers, MyApplication.getContext(), Constants.diskUser);
	}

	// Get a user from users
	public User loadUserFromFile(String email) {
		ArrayList<User> diskUsers = loadFromFile(MyApplication.getContext(), new TypeToken<ArrayList<User>>() {
		}, Constants.diskUser);
		User diskUser = null;
		for (User user : diskUsers) {
			if (user.getEmail().equals(email)) {
				diskUser = user;
				break;
			}
		}
		return diskUser;
	}
}