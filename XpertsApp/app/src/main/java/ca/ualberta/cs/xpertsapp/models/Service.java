package ca.ualberta.cs.xpertsapp.models;

import android.graphics.Picture;

import java.util.ArrayList;

public class Service {

    private String name;
    private String description;
    private Category category;
    private ArrayList<Picture> photos;
    private boolean shareable;
    private User owner;

    public Service(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}