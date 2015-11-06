package ca.ualberta.cs.xpertsapp.controllers;

import java.security.InvalidParameterException;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

/**
 * Created by kmbaker on 11/3/15.
 */
public class ProfileController {
    //adds friend to local user unless user doesn't exist, then returns null
    //should probably be an exception
    public User addFriend(String email){
        User user = MyApplication.getLocalUser();
        User friend = UserManager.sharedManager().getUser(email);
        if (friend == null) {
            return null;
        }
        user.addFriend(friend);
        friend.addFriend(user);
        return friend;
    }
    //deletes friend from local user
    public void deleteFriend(User friend) {
        User user = MyApplication.getLocalUser();
        user.removeFriend(friend);
        friend.removeFriend(user);
    }
}
