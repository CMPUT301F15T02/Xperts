package ca.ualberta.cs.xpertsapp.controllers;

import java.security.InvalidParameterException;
import java.util.List;

import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * Created by kmbaker on 11/3/15.
 */
public class ProfileController {
    //searches all users for user with this email
    public User searchUsers(String email){
        String friendSearchString = "contactInfo:"+email;
        List<User> results = UserManager.sharedManager().findUsers(friendSearchString);
        User soonFriend = results.get(0);
        return soonFriend;
    }

    //adds friend to local user
    public void addFriend(String email){
        User user = UserManager.sharedManager().localUser();
        User friend = searchUsers(email);
        user.addFriend(friend.getID());
    }
    //deletes friend from local user
    public void deleteFriend(User friend) {
        User user = UserManager.sharedManager().localUser();
        user.removeFriend(friend.getID());
    }
    //TODO figure out what this is for
    public User getUser(String id) {
        String friendSearchString = "id:"+id;
        List<User> results = UserManager.sharedManager().findUsers(friendSearchString);
        User friend = results.get(0);
        return friend;
    }
}
