package ca.ualberta.cs.xpertsapp.models;

import android.graphics.Picture;

import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {
    private String name;
    private String email;
    private String location;
    private ArrayList<User> friends = new ArrayList<User>();
    private ArrayList<Service> services = new ArrayList<Service>();
    private Picture picture = null;
    private ArrayList<Trade> tradeHistory = new ArrayList<Trade>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void addService(Service service) {
        services.add(service);
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
