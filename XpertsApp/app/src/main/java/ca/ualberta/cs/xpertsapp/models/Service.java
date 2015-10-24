package ca.ualberta.cs.xpertsapp.models;

import android.graphics.Bitmap;
import android.graphics.Picture;

import java.util.ArrayList;

public class Service {

    private String id;
    private String name;
    private String description;
    private String category;
    private ArrayList<Bitmap> photos;
    private boolean shareable;
    private User owner;

    public Service(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getShareable() {
        return shareable;
    }

    public User getOwner() {
        return owner;
    }




}