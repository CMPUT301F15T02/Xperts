package ca.ualberta.cs.xpertsapp.models;

import java.util.ArrayList;
import java.util.Date;


public class Trade {
    private String id;
    private ArrayList<Service> borrowerServices = new ArrayList<Service>();
    private ArrayList<Service> ownerServices = new ArrayList<Service>();
    private Date start;
    private Date updated;
    private Boolean isCounter;
    private TradeState status;

    public Trade(String id, Boolean isCounter) {
        this.id = id;
        this.isCounter = isCounter;
    }

    public String getId() {
        return id;
    }

    public User getOwner() {
        return null;
    }

    public User getBorrower() {
        return null;
    }

    public ArrayList<Service> getOwnerServices() {
        return null;
    }

    public void addOwnerService(Service service) {
    }

    public void removeOwnerService(Service service) {

    }

    public ArrayList<Service> getBorrowerServices() {
        return null;
    }

    public void addBorrowerService(Service service) {

    }

    public void removeBorrowerService(Service service) {

    }

    public Date getProposedDate() {
        return null;
    }

    public Date getLastUpdatedDate() {
        return null;
    }

    public Boolean isCounterOffer() {
        return isCounter;
    }
    /*
    public TradeStatus getStatus() {
        return null;
    }*/

    private Boolean isEditable() {
        return null;
    }

}
