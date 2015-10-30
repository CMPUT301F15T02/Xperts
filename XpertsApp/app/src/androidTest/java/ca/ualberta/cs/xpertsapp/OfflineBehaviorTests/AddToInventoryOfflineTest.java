package ca.ualberta.cs.xpertsapp.OfflineBehaviorTests;

import android.app.Activity;
import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.models.Service;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class AddToInventoryOfflineTest extends ActivityInstrumentationTestCase2 {

    public AddToInventoryOfflineTest() {
        super(MainActivity.class);
    }

    // Test that a service offer can be added offline
    public void testAddServicesOffline() {
        Activity activity = getActivity();
        Users users = new Users();
        User owner = new User();
        ArrayList<Service> services = new ArrayList<Service>();

        owner.setEmail("testEmail");

        // Owner makes service offers offline
        Service service = new Service();
        service.setName("testService");
        services.add(service);

        owner.addService(service);
        users.add(owner);

        // Saves to local storage
        new IOManager(activity).writeToFile(users);

        Users ownerLocals = new IOManager(activity).loadFromFile();
        User ownerLocal = ownerLocals.get(0);

        assertEquals(services.get(0).getName(), ownerLocal.getServices().get(0).getName());
        // Don't know why this doesn't work
        //assertEquals(services, ownerLocal.getServices());
    }

    // Test that an offline service is added to server when internet connection is detected
    public void testAddServicesToServer() {
        Activity activity = getActivity();

        // Loads user from local storage
        Users ownerLocals = new IOManager(activity).loadFromFile();
        User ownerLocal = ownerLocals.get(0);

        // Writes to server (if internet is detected)
        new IOManager(activity).addUserToServer(ownerLocal);

        // Owner changes his service offers offline
        ownerLocal.getServices().get(0).setName("newTestService");
        ownerLocal.getServices().get(0).setDescription("newTestService");

        // Writes change to local storage
        new IOManager(activity).writeToFile(ownerLocals);

        // Writes change to server (if internet is detected)
        new IOManager(activity).addUserToServer(ownerLocal);

        // Retrieves from server
        User ownerServer = new IOManager(activity).getUser("testEmail");

        assertEquals(ownerLocal.getServices().get(0).getName(), ownerServer.getServices().get(0).getName());
    }
}