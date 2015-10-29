import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by murdock on 10/8/15.
 */
public class FriendTest extends ActivityInstrumentationTestCase2 {
    public FriendTest() {
        super(com.ualberta.murdock.project.MainActivity.class);
    }
    public void testAddFriend() {
        User user = new User("Bob");
        User user2 = new User("Jan");
        user.addfriend(Jan);
        assertTrue(user.getFriends().contains(Jan);
    }

    public void testHasFriend() {
        User user = new User("Bob");
        User user2 = new User("Jan");
        user.addfriend(Jan);
        assertEquals(user.addfriend(Jan), 0); //have addfriend return 1 on success 0 on failure
    }

    public void testFriendNameDoesNotExist() {
        User user = new User("Bob");
        User user2 = new User("Jan");
        assertEquals(user.addfriend(asd1q2dasd), 0); //have addfriend return 1 on success 0 on failure
    }

    public void testRemoveFriend() {
        User user = new User("Bob");
        User user2 = new User("Jan");
        assertEquals(user.addfriend(Jan), 1); //have addfriend return 1 on success 0 on failure
        user.removefriend(Jan);
        assertFalse(user.getFriends().contains(Jan);
    }

    public void testViewProfile() {
        User user = new User("Bob");
        User user2 = new User("Jan");
        User user3 = new User("Ben");
        user.addfriend(Jan);
        assertEquals(user.navigateprofile(user2),1); //User can see profile of friend 
        assertEquals(user.navigateprofile(user3),1); //User can see profile of non-friend
    }
}
