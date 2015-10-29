package ca.ualberta.cs.xpertsapp.UnitTests;

import junit.framework.TestCase;

import ca.ualberta.cs.xpertsapp.models.User;

/**
 * Created by hammadjutt on 2015-10-29.
 */
public class UserTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

    }
    public void tearDown() throws Exception {

    }

    public void testCreate(){
        User user = new User("bob", "email@web.com");
        assertEquals(user.getEmail(),"email@web.com");
        assertEquals(user.getName(),"bob");
    }

    public void testAddFriend(){
        User user = new User("bob", "email@web.com");
        User user2 = new User("sally","email@web.ca");
        assertEquals(user.getFriends().size(),0);
        user.addFriend(user2);
        assertEquals(user.getFriends().size(),1);
    }

    public void testRemoveFriend(){
        User user = new User("bob", "email@web.com");
        User user2 = new User("sally","email@web.ca");
        assertEquals(user.getFriends().size(),0);
        user.addFriend(user2);
        assertEquals(user.getFriends().size(),1);
        user.removeFriend(user2);
        assertEquals(user.getFriends().size(),0);
    }

    public void testTrades(){
        User user = new User("bob", "email@web.com");
        User user2 = new User("sally","email@web.ca");
    }
}