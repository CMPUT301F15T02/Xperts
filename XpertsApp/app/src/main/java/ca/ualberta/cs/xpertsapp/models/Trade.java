package ca.ualberta.cs.xpertsapp.models;

import java.util.ArrayList;
import java.util.Date;


public class Trade {
    private int id;
    private ArrayList<Service> borrowerServices = new ArrayList<Service>();
    private ArrayList<Service> ownerServices = new ArrayList<Service>();
    private Date start;
    private Date updated;
    private Boolean isCounter;
    private TradeState status;
}
