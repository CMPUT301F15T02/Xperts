package ca.ualberta.cs.xpertsapp.models;

import android.graphics.Bitmap;
import android.graphics.Picture;

import java.io.Serializable;
import java.util.ArrayList;

public class Service implements Serializable {

    private String id;
    private String name;
    private String description;
    private Category category;
    private ArrayList<Bitmap> photos;
    private boolean shareable;
    private User owner;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getShareable() {
        return shareable;
    }

    public User getOwner() {
        return owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}