package ca.ualberta.cs.xpertsapp.controllers;

import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * Created by kmbaker on 11/23/15.
 */
public class TradeController {
    public void createTrade(User borrower,ArrayList<Service> ownerServices) {
        Trade newTrade = TradeManager.sharedManager().newTrade(borrower, false);
        for (Service service : ownerServices) {
            newTrade.addOwnerService(service);
        }
        newTrade.commit();
    }
}
