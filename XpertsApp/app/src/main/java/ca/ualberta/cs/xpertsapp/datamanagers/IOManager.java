package ca.ualberta.cs.xpertsapp.datamanagers;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.datamanagers.IO.SearchResponse;
import ca.ualberta.cs.xpertsapp.datamanagers.IO.SearchHit;
import ca.ualberta.cs.xpertsapp.datamanagers.IO.SimpleSearchCommand;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;

/**
 * IOManager responsible for I/O to local storage and server
 * Codes are from cmput301 lab
 */
public class IOManager {

    private static final String TAG = "IOManager";
    private Gson gson;
    private Activity activity;

    private Users users;

    public Users getUsers() {
        return users;
    }

    public IOManager(Activity activity) {
        this.activity = activity;

        gson = new Gson();
        users = new Users();
    }

    // Add a user to server
    public void addUserToServer(User user) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost addRequest = new HttpPost(users.getResourceUrl() + user.getEmail());

            StringEntity stringEntity = new StringEntity(gson.toJson(user));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete a user from server
    public void deleteUserOnServer(int userId) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpDelete deleteRequest = new HttpDelete(users.getResourceUrl() + userId);
            deleteRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(deleteRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search a user by email
    public User getUser(String email) {
        SearchHit<User> sh = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(users.getResourceUrl() + email);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<User>>() {
        }.getType();

        try {
            sh = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchHitType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sh.getSource();
    }

    // Search users by query
    public Users searchUsers(String searchString, String field) {
        Users result = new Users();
        SearchResponse<User> esResponse;

        HttpPost searchRequest = new HttpPost(users.getSearchUrl());

        String[] fields = null;
        if (field != null) {
            throw new UnsupportedOperationException("Not implemented!");
        }

        SimpleSearchCommand command = new SimpleSearchCommand(searchString);

        String query = gson.toJson(command);
        Log.i(TAG, "Json command: " + query);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringEntity);

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpClient.execute(searchRequest);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Parses the response of a search
         */
        Type searchResponseType = new TypeToken<SearchResponse<User>>() {
        }.getType();

        try {
            esResponse = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Log.d(TAG, "Server's response: " + esResponse);

        for (SearchHit<User> hit:esResponse.getHits().getHits()) {
            result.add(hit.getSource());
        }
        result.notifyObservers();

        return result;
    }

    // Read users from local storage
    public Users loadFromFile() {
        Users users;

        try {
            FileInputStream fis = activity.openFileInput("users.sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html retrieved 2015-09-21
            //Type listType = new TypeToken<ArrayList<String>>() {}.getType();
            users = gson.fromJson(in, Users.class);

        } catch (FileNotFoundException e) {
            users = new Users();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    // Load users from local storage
    public void writeToFile(Users users) {
        try {
            FileOutputStream fos = activity.openFileOutput("users.sav", 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(users, writer);
            Log.d("gson", gson.toJson(users));
            writer.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

