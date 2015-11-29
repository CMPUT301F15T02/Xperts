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
 * This can create a trade or delete a trade. It is used in OfferTradeActivity and OutgoingOfferActivity.
 */
public class TradeController {
    /**
     * This creates a trade by making a new trade and adding the borrower services to the trade
     * and the owner service.
     * @param owner The owner of the trade
     * @param borrowerServices A list of services the borrower is offering in this trade.
     * @param ownerServices The service the borrower is asking for from the owner.
     */
    public void createTrade(User owner,ArrayList<Service> borrowerServices, ArrayList<Service> ownerServices, Boolean isCounter) {
        Trade newTrade = TradeManager.sharedManager().newTrade(owner, isCounter);
        for (Service service : borrowerServices) {
            newTrade.addBorrowerService(service);
        }
        for (Service service : ownerServices) {
            newTrade.addOwnerService(service);
        }
        newTrade.commit();
    }

    /**
     * This function deletes a trade from the system, given by the id.
     * @param id The id of the trade to be deleted. It must not be null.
     */
    public void deleteTrade(String id) {
        Trade deletedTrade = TradeManager.sharedManager().getTrade(id);
        MyApplication.getLocalUser().removeTrade(deletedTrade);
        deletedTrade.getOwner().removeTrade(deletedTrade);
    }

    /**
     * Get the count of pending trades a user has. This is used for the notifications on the main
     * menu screen.
     * @return the count of pending trades the user has.
     */
    public Integer getPendingTrades() {
        Integer pending = 0;
        User user = MyApplication.getLocalUser();
        for (Trade trade: user.getTrades()) {
            if (trade.getOwner().equals(user) && trade.getStatus()==0) {
                pending++;
            }
        }
        return pending;
    }
}
