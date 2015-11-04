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
    public User searchUsers(String email) throws InvalidParameterException {
        String friendSearchString = "contactInfo:"+email;
        List<User> results = UserManager.sharedManager().findUsers(friendSearchString);
        if (results.size() != 1) {
            throw new InvalidParameterException();
        }
        User soonFriend = results.get(0);
        return soonFriend;
    }

    //needs to throw exception if friend not found
    public void addFriend(String email) throws InvalidParameterException{
        User user = UserManager.sharedManager().localUser();
        try {
            User friend = searchUsers(email);
            user.addFriend(friend.getID());
        }
        catch (InvalidParameterException e) {
            throw new InvalidParameterException();
        }
    }

    public void deleteFriend() {

    }

    public User getUser(String email) {
        return null;
    }
}
