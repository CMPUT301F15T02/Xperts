package ca.ualberta.cs.xpertsapp.models;

import android.graphics.Bitmap;

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

    public Service() {
        this("Enter a service name.", "1");
    }

    public Service(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Service(String id, String name, String description, Category category, boolean shareable, User owner) {
        this.id = id;
        this.name =name;
        this.description = description;
        this.category = category;
        this.shareable = shareable;
        this.owner = owner;
    }

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