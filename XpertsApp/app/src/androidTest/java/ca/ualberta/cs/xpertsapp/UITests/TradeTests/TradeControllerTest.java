package ca.ualberta.cs.xpertsapp.UITests.TradeTests;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.controllers.TradeController;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.Trade;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
/** This needs to be done
 * Created by kmbaker on 11/24/15.
 */
public class TradeControllerTest extends TestCase {
    public TradeControllerTest() {
        super();
    }

    final String testEmail1 = "david@xperts.com";
    final String testEmail2 = "seann@xperts.com";
    final String testEmail3 = "kathleen@xperts.com";
    final String testEmail4 = "huy@xperts.com";
    final String testEmail5 = "justin@xperts.com";
    final String testEmail6 = "hammad@xperts.com";
    private User u1;
    private User u2;
    private User u3;

    @Override
    protected void setUp2() {
        super.setUp2();
        UserManager.sharedManager().clearCache();
        ServiceManager.sharedManager().clearCache();
        TradeManager.sharedManager().clearCache();

        IOManager.sharedManager().deleteData(Constants.serverUserExtension());
        IOManager.sharedManager().deleteData(Constants.serverServiceExtension());
        IOManager.sharedManager().deleteData(Constants.serverTradeExtension());
        u1 = newTestUser(testEmail1,"David Skrundz","Calgary");
        u2 = newTestUser(testEmail2, "Seann Murdock", "Vancouver");
        u3 = newTestUser(testEmail3, "Kathleen Baker", "Toronto");
    }

    @Override
    protected void tearDown2() {
        super.tearDown2();
    }

    public void testCreateTrade() {
        setUp2();

        //createTrade(User owner,ArrayList<Service> borrowerServices, Service ownerService)
        Service testService = newTestService("U1 FirstService", "U1 FirstDescription", 0, true);
        u1.addService(testService);

        // Test offer trade to friend
        User user = MyApplication.getLocalUser();
        Service newService = ServiceManager.sharedManager().newService();
        newService.setName("new service");
        user.addService(newService);

        user.addFriend(u1);
        User myFriend = user.getFriends().get(0);

        ArrayList<Service> borrowerServices = new ArrayList<Service>();
        borrowerServices.add(newService);
        // Create a new trade
        TradeController tc = new TradeController();
        tc.createTrade(myFriend, borrowerServices, testService);

        assertEquals(TradeManager.sharedManager().getTrades().size(), 1);
        assertEquals(user.getTrades().size(), 1);
        assertEquals(u1.getTrades().size(), 1);

        Trade newTrade = user.getTrades().get(0);
        TradeManager.sharedManager().clearCache();
        Trade oldTrade = TradeManager.sharedManager().getTrade(user.getTrades().get(0).getID());
        assertEquals(oldTrade.getID(), newTrade.getID());

        IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + newService.getID());
        IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + newTrade.getID());
        tearDown2();
    }

    //deleteTrade(String id)
    public void testDeleteTrade() {
        setUp2();

        //createTrade(User owner,ArrayList<Service> borrowerServices, Service ownerService)
        Service testService = newTestService("U1 FirstService", "U1 FirstDescription", 0, true);
        u1.addService(testService);

        // Test offer trade to friend
        User user = MyApplication.getLocalUser();
        Service newService = ServiceManager.sharedManager().newService();
        newService.setName("new service");
        user.addService(newService);

        user.addFriend(u1);
        User myFriend = user.getFriends().get(0);

        ArrayList<Service> borrowerServices = new ArrayList<Service>();
        borrowerServices.add(newService);
        // Create a new trade
        TradeController tc = new TradeController();
        tc.createTrade(myFriend, borrowerServices, testService);

        assertEquals(TradeManager.sharedManager().getTrades().size(), 1);
        assertEquals(user.getTrades().size(), 1);
        assertEquals(u1.getTrades().size(), 1);

        tc.deleteTrade(user.getTrades().get(0).getID());

        assertEquals(TradeManager.sharedManager().getTrades().size(), 0);
        assertEquals(user.getTrades().size(), 0);
        assertEquals(u1.getTrades().size(), 0);

        tearDown2();
    }

    public void testGetPendingTrades() {
        setUp2();

        //getPendingTrades()
        //createTrade(User owner,ArrayList<Service> borrowerServices, Service ownerService)
        Service testService = newTestService("U1 FirstService", "U1 FirstDescription", 0, true);
        u1.addService(testService);

        // Test offer trade to friend
        User user = MyApplication.getLocalUser();
        Service newService = ServiceManager.sharedManager().newService();
        newService.setName("new service");
        user.addService(newService);

        user.addFriend(u1);
        User myFriend = user.getFriends().get(0);

        ArrayList<Service> borrowerServices = new ArrayList<Service>();
        borrowerServices.add(newService);
        // Create a new trade
        TradeController tc = new TradeController();
        tc.createTrade(myFriend, borrowerServices, testService);

        assertEquals(TradeManager.sharedManager().getTrades().size(), 1);
        assertEquals(user.getTrades().size(), 1);
        assertEquals(u1.getTrades().size(), 1);

        int pending = tc.getPendingTrades();
        assertEquals(pending, 1);
        Trade newTrade = user.getTrades().get(0);
        TradeManager.sharedManager().clearCache();
        Trade oldTrade = TradeManager.sharedManager().getTrade(user.getTrades().get(0).getID());
        assertEquals(oldTrade.getID(), newTrade.getID());

        IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + newService.getID());
        IOManager.sharedManager().deleteData(Constants.serverTradeExtension() + newTrade.getID());

        tearDown2();
    }
}
