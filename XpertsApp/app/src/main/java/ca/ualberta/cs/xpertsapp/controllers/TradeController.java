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
    public void createTrade(User owner,ArrayList<Service> borrowerServices, Service ownerService) {
        Trade newTrade = TradeManager.sharedManager().newTrade(owner, false);
        for (Service service : borrowerServices) {
            newTrade.addBorrowerService(service);
        }
        newTrade.addOwnerService(ownerService);
        newTrade.commit();
    }

    /**
     * This function deletes a trade given by the id.
     * @param id The id of the trade to be deleted. It must not be null
     */
    public void deleteTrade(String id) {
        Trade deletedTrade = TradeManager.sharedManager().getTrade(id);
        MyApplication.getLocalUser().removeTrade(deletedTrade);
    }
}
