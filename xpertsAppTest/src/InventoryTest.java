import android.test.ActivityInstrumentationTestCase2;
import java.util.ArrayList;

// Test use cases relating to Inventory
public class InventoryTest extends ActivityInstrumentationTestCase2 {

    public InventoryTest() {
	      super(MainActivity.class);
    }

    // Test that a service is added and done correctly
    public void testAddService() {
	      Owner owner = new Owner("Bill");

    	  // serviceList should be null
        ArrayList<Service> serviceList = owner.getServices();
	      assertEquals(owner.getServices().size(), 0);

	      Service service0 = new Service("Lawnmowing", "General Labour", "description");
	      Service service1 = new Service("Plumbling", "Construction", "description");
	      owner.addService(service1);
	      assertEquals(owner.getServices().size(), 1);
	      owner.addService(service2);
	      assertEquals(owner.getServices().size(), 2);

	      assertEquals(owner.getServices().get(0), service0);
	      assertEquals(owner.getServices().get(1), service1);
    }

    public void testViewInventory() {
	      Owner owner = new Owner("Harry");

	      Service service0 = new Service("Lawnmowing", "General Labour", "description");
	      Service service1 = new Service("Plumbling", "Construction", "description");

	      ArrayList<Service> serviceList = new ArrayList<Service>();
	      serviceList.add(service0);
	      owner.addService(service0);
	      assertEquals(owner.viewInventory(), serviceList);
    }

    public void testDeleteService() {
	      Owner owner = new Owner("Jim");

	      Service service0 = new Service("Lawnmowing", "General Labour", "description");
	      Service service1 = new Service("Plumbling", "Construction", "description");
	      Service service2 = new Service("Math Help", "Tutoring", "description");

	      owner.addService(service0);
	      owner.addService(service1);
	      assertEquals(owner.getServices().size(), 2);

	      owner.deleteService(service0);
	      assertEquals(owner.getServices().size(), 1);

	      owner.addService(service2);
	      owner.deleteService(service1);
	      assertEquals(owner.getServices().size(), 1);

	      owner.deleteService(service2);
	      assertEquals(owner.getServices().size(), 0);
    }

    public void testShareable() {
	      Service service0 = new Service("Lawnmowing", "General Labour", "description");
	      service0.setShareable(true);
	      assertTrue(service0.isShareable());
    }

    public void testEdit() {
	      Service service0 = new Service("Haircutting", "Other", "description");
	      service0.editDescription("new description");
	      assertEquals(service0.getDescription(), "new description");
    }

    public void testCategory() {
	      Service service0 = new Service("Teeth Cleaning", "description");
	      service0.chooseCategory("Healthcare");
	      assertEquals(service0.getCategory(), "Healthcare");
    }

}
