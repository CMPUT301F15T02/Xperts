package ca.ualberta.cs.xpertsapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.manager.UserManager;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testModel() {
		User user = UserManager.sharedUserManager().localUser();

    }
}