package ca.ualberta.cs.xpertsapp.UnitTests;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class InventoryTest extends TestCase {
	public InventoryTest() {
		super();
	}

	private static User friend1;
	private static Service service1;
	private static Service service2;

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
				"\"services\":[\"1\", \"2\"]," +
				"\"trades\":[]" +
				"}";
		String service1String = "" +
				"{" +
				"\"category\":" +
				"{" +
				"\"name\":\"OTHER\"" +
				"}" +
				"," +
				"\"description\":\"\"," +
				"\"id\":\"1\"," +
				"\"name\":\"\"," +
				"\"owner\":\"\"," +
				"\"pictures\":[]," +
				"\"shareable\":false" +
				"}";
		String service2String = "" +
				"{" +
				"\"category\":" +
				"{" +
				"\"name\":\"OTHER\"" +
				"}" +
				"," +
				"\"description\":\"\"," +
				"\"id\":\"2\"," +
				"\"name\":\"\"," +
				"\"owner\":\"\"," +
				"\"pictures\":[]," +
				"\"shareable\":true" +
				"}";
		friend1 = (new Gson()).fromJson(friend1String, User.class);
		service1 = (new Gson()).fromJson(service1String, Service.class);
		service2 = (new Gson()).fromJson(service2String, Service.class);
		IOManager.sharedManager().storeData(friend1, Constants.serverUserExtension() + friend1.getID());
		IOManager.sharedManager().storeData(service1, Constants.serverServiceExtension() + service1.getID());
		IOManager.sharedManager().storeData(service2, Constants.serverServiceExtension() + service2.getID());
		friend1 = UserManager.sharedManager().getUser(friend1.getID());
		service1 = ServiceManager.sharedManager().getService(service1.getID());
		service2 = ServiceManager.sharedManager().getService(service2.getID());
	}

	@Override
	protected void tearDown() throws Exception {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + friend1.getID());
		Log.i("?>???????", service1.getID().toString());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + service1.getID());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + service2.getID());
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getID());

		super.tearDown();
	}

	// test_01_04_01 and test_01_05_01 cover the rest of this case
	// Also 01.02.01
	public void test_01_01_01() {
		// Test adding a service
		User user = UserManager.sharedManager().localUser();
		Service newService = ServiceManager.sharedManager().newService();
		newService.setName("some new service");
		user.addService(newService);

		assertEquals(user.getServices().size(), 1);
		assertEquals(ServiceManager.sharedManager().getServices().size(), 3);
		assertTrue(ServiceManager.sharedManager().getServices().contains(newService));

		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + newService.getID());
	}

	// 01.02.01 is not model

	// 01.03.01 is not model

	public void test_01_03_02() {
		// Test not listing items that are private
		User friend = UserManager.sharedManager().getUser("1");
		List<Service> friendServices = friend.getServices();

		assertEquals(friendServices.size(), 1);
	}

	public void test_01_04_01() {
		// TODO: Test editing a service
		User user = UserManager.sharedManager().localUser();
		Service newService = ServiceManager.sharedManager().newService();
		newService.setName("some new service");
		user.addService(newService);

		assertEquals(user.getServices().size(), 1);
		Service newService2 = user.getServices().get(0);
		assertEquals(newService, newService2);

		String newName = "a differnnt name";
		newService2.setName(newName);
		assertEquals(newService.getName(), newName);

		ServiceManager.sharedManager().clearCache();
		Service newwerrService22222 = ServiceManager.sharedManager().getService(newService.getID());
		assertEquals(newwerrService22222.getName(), newName);

		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + newwerrService22222.getID());
	}

	public void test_01_05_01() {
		// Test removing a service
		User user = UserManager.sharedManager().localUser();
		Service newService = ServiceManager.sharedManager().newService();
		newService.setName("some new service");
		user.addService(newService);
		assertEquals(user.getServices().size(), 1);

		user.removeService(newService.getID());
		assertEquals(user.getServices().size(), 0);

		IOManager.sharedManager().deleteData(Constants.serverServiceExtension() + newService.getID());
	}

	public void test_01_06_01() {
		// Test settings a category and make sure there are only 10
		Service newService = ServiceManager.sharedManager().newService();
		newService.setCategory(CategoryList.sharedCategoryList().getCategories().get(3));

		assertEquals(newService.getCategory(), CategoryList.sharedCategoryList().getCategories().get(3));
		assertEquals(CategoryList.sharedCategoryList().getCategories().size(), 10);
	}

	// 01_07_01 is not model
}