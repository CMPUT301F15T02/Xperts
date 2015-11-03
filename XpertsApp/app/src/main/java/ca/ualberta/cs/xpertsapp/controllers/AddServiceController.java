package ca.ualberta.cs.xpertsapp.controllers;

import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.User;

public class AddServiceController {

    public User owner;
    public Service service;

    public void addService(Service service) {
        owner.addService(service);
    }

    public void removeService(Service service) {
        owner.removeService("A");
    }
}
