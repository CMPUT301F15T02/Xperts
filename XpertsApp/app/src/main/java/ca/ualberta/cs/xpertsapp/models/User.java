package ca.ualberta.cs.xpertsapp.models;

import android.graphics.Picture;

import java.util.ArrayList;

/**
 * Created by murdock on 10/16/15.
 */
public class User {
    private String name;
    private String email;
    private String location;
    private ArrayList<User> friends = new ArrayList<User>();
    private ArrayList<Service> services = new ArrayList<Service>();
    private Picture picture;
    private ArrayList<Trade> tradeHistory = new ArrayList<Trade>();

}
