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
    private Boolean isEditable;

    public Trade(String id, Boolean isCounter) {
        this.id = id;
        this.isCounter = isCounter;
        status = new TradeStatePending();
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
        return ownerServices;
    }

    public void addOwnerService(Service service) {
        ownerServices.add(service);
    }

    public void removeOwnerService(Service service) {
        ownerServices.remove(service);
    }

    public ArrayList<Service> getBorrowerServices() {
        return borrowerServices;
    }

    public void addBorrowerService(Service service) {
        borrowerServices.add(service);
    }

    public void removeBorrowerService(Service service) {
        borrowerServices.remove(service);
    }

    public Date getProposedDate() {
        return start;
    }

    public Date getLastUpdatedDate() {
        return updated;
    }

    public Boolean isCounterOffer() {
        return isCounter;
    }

    private Boolean isEditable() {
        //change this to check if owner is current user and status is pending and return correct bool
        return Boolean.FALSE;
    }

    public void setTradeState(TradeState state) {
        status = state;
    }

    public TradeState getTradeState() {
        return status;
    }

    public void accept() {
        status.accept(this);
    }

    public void decline() {
        status.decline(this);
    }

    public void cancel() {
        status.cancel(this);
    }
}
