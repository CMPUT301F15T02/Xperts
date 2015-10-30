package ca.ualberta.cs.xpertsapp.InventoryTests;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.datamanagers.ServiceManager;
import ca.ualberta.cs.xpertsapp.models.Service;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class AddInventoryServiceTest extends ActivityInstrumentationTestCase2 {
    public AddInventoryServiceTest() {
        super(Application.class);

        String name = "Haircuts";
        String id = "1";

        Service service1 = new Service();
        service1.setName(name);
        assertEquals(name, service1.getName());
        service1.setId(id);
        ServiceManager serviceManager = ServiceManager.sharedServiceManager();
        serviceManager.addService(service1);
        assertEquals(service1, serviceManager.getService(id));

        Service service2 = new Service();
        service2.setName("Plumbing");
        service2.setId("2");
        serviceManager.addService(service2);
        Service service3 = new Service();
        service3.setName("Website Design");
        service3.setId("3");
        serviceManager.addService(service3);

        ArrayList<Service> myServices = new ArrayList<Service>();
        myServices.add(service1);
        myServices.add(service2);
        myServices.add(service3);

        assertEquals(myServices, serviceManager.getServices());

        myServices.clear();
        serviceManager.clearCache();

        assertEquals(myServices, serviceManager.getServices());
            
    }

}