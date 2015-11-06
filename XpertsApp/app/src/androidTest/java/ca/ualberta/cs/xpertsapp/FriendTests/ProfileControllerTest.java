package ca.ualberta.cs.xpertsapp.FriendTests;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * Created by kmbaker on 11/3/15.
 */

public class ProfileControllerTest extends TestCase {
    public ProfileControllerTest() {
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
    protected void setUp() throws Exception {
        super.setUp();
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
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAddFriend() {
        // Test add friends by searching for email
        User user = MyApplication.getLocalUser();
        String email = "email1@u.ca";
        //should not exist
        ProfileController pc = new ProfileController();
        assertEquals(user.getFriends().size() == 0, true);
        User friend = pc.addFriend(email);
        assertEquals(friend, null);
        assertEquals(user.getFriends().size() == 0, true);
        String email2= "kathleen@xperts.com";
        //should exist
        friend = pc.addFriend(email2);
        assertEquals(user.getFriends().contains(u3), true);
        assertEquals(u3.getFriends().contains(user),true);
    }

    public void testDeleteFriend() {
        // Test delete friend
        User user = MyApplication.getLocalUser();
        String email = "david@xperts.com";
        ProfileController pc = new ProfileController();
        User friend = pc.addFriend(email);
        assertEquals(user.getFriends().contains(u1),true);
        assertEquals(u1.getFriends().contains(user),true);
        assertEquals(user.getFriends().contains(u3),false);
        assertEquals(user.getFriends().size() == 1, true);
        pc.deleteFriend(friend);
        assertEquals(user.getFriends().size() == 0, true);
        assertEquals(friend.getFriends().size() == 0, true);
    }
}