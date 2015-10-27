package ca.ualberta.cs.xpertsapp.datamanagers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import ca.ualberta.cs.xpertsapp.datamanagers.IO.SearchResponse;
import ca.ualberta.cs.xpertsapp.datamanagers.IO.SearchHit;
import ca.ualberta.cs.xpertsapp.datamanagers.IO.SimpleSearchCommand;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;


public class IOManager {

    private static final String TAG = "ElasticSearch";
    private Gson gson;
    private Users users = new Users();

    public Users getUsers() {
        return users;
    }

    public IOManager() {
        gson = new Gson();
    }

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

    public void search(final String search) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                searchUser(search, null);
            }
        });
        thread.start();
    }

    private void searchUser(String searchString, String field) {
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

        /*
        List<SearchHit<User>> list = esResponse.getHits().getHits();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                User user = list.get(i).getSource();
                users.add(user);
            }
            users.notifyObservers();
        }
        */
    }
}

