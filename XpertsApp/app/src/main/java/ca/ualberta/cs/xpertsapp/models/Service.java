package ca.ualberta.cs.xpertsapp.models;

import java.util.ArrayList;

public class Service {

    private String name;
    private String description;
    private Category category;
    private ArrayList<pics> photos;
    private boolean shareable;
    private User owner;

    public Service(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}