package ca.ualberta.cs.xpertsapp.FriendTests;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.controllers.ProfileController;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * Created by kmbaker on 11/3/15.
 */

public class ProfileControllerTest extends TestCase {
    public ProfileControllerTest() {
        super();
    }

    private User friend1;
    private User friend2;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String friend1String = "" +
                "{" +
                "\"contactInfo\":\"email1@u.ca\"," +
                "\"friends\":[]," +
                "\"id\":\"1\"," +
                "\"location\":\"British\"," +
                "\"name\":\"The Clown Guy\"," +
                "\"services\":[]," +
                "\"trades\":[]" +
                "}";
        String friend2String = "" +
                "{" +
                "\"contactInfo\":\"Don't contact me pl0x.\"," +
                "\"friends\":[]," +
                "\"id\":\"2\"," +
                "\"location\":\"Canada\"," +
                "\"name\":\"Ronald\"," +
                "\"services\":[]," +
                "\"trades\":[]" +
                "}";
        friend1 = (new Gson()).fromJson(friend1String, User.class);
        friend2 = (new Gson()).fromJson(friend2String, User.class);
        IOManager.sharedManager().storeData(friend1, Constants.serverUserExtension() + friend1.getEmail());
        IOManager.sharedManager().storeData(friend2, Constants.serverUserExtension() + friend2.getEmail());
        friend1 = UserManager.sharedManager().getUser(friend1.getEmail());
        friend2 = UserManager.sharedManager().getUser(friend2.getEmail());
    }

    @Override
    protected void tearDown() throws Exception {
        // Cleanup
        IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend1.getEmail());
        IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend2.getEmail());
        IOManager.sharedManager().deleteData(Constants.serverUserExtension() + MyApplication.getLocalUser().getEmail());

        super.tearDown();
    }

    public void testSearchUsers() {
        // Test search for user in all users using email
        User user = MyApplication.getLocalUser();
        String email = "email1@u.ca";
        ProfileController pc = new ProfileController();
        User friend = pc.searchUsers(email);
        assertEquals(email, friend.getEmail());
    }

    public void testAddFriend() {
        // Test add friends by searching for email
        User user = MyApplication.getLocalUser();
        String email = "email1@u.ca";
        ProfileController pc = new ProfileController();
        assertEquals(user.getFriends().size() ==0, true);
        pc.addFriend(email);
        assertEquals(UserManager.sharedManager().getUser(user.getFriends().get(0).getEmail()), friend1);
    }

    public void testDeleteFriend() {
        // Test delete friend
        User user = MyApplication.getLocalUser();
        String email = "email1@u.ca";
        ProfileController pc = new ProfileController();
        pc.addFriend(email);
        assertEquals(UserManager.sharedManager().getUser(user.getFriends().get(0).getEmail()), friend1);
        assertEquals(user.getFriends().size() == 1, true);
        pc.deleteFriend(UserManager.sharedManager().getUser(user.getFriends().get(0).getEmail()));
        assertEquals(user.getFriends().size() == 0, true);
    }

    public void testGetUser() {
        // Test get user from id
        User user = MyApplication.getLocalUser();
        String id = "1";
        ProfileController pc = new ProfileController();
        User friend = pc.getUser(id);
        assertEquals(id, friend.getEmail());
        assertEquals(friend.getLocation(), "British");
    }
}