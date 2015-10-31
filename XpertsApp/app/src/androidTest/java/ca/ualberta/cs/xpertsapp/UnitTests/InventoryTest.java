package ca.ualberta.cs.xpertsapp.UnitTests;

import junit.framework.TestCase;

import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class InventoryTest extends TestCase {
	// test_01_04_01 and test_01_05_01 cover the rest of this case
	public void test_01_01_01() {
		User user = UserManager.sharedManager().localUser();
		Service newService = ServiceManager.newService(user.getID());
		newService.setName("some new service");
		user.addService(newService.getID());

		assertTrue(user.getServices().size() == 1);
		assertTrue(ServiceManager.sharedManager().getServices().size() == 1);
		assertTrue(ServiceManager.sharedManager().getServices().get(0) == newService);
	}

	public void test_01_02_01() {
		// TODO: Test view inventory
		assertTrue(false);
	}

	public void test_01_03_01() {
		// TODO: Test view items in inventory
		assertTrue(false);
	}

	public void test_01_03_02() {
		// TODO: Test not listing items that are private
		assertTrue(false);
	}

	public void test_01_04_01() {
		// TODO: Test editing a service
		assertTrue(false);
	}

	public void test_01_05_01() {
		// TODO: Test removing a service
		assertTrue(false);
	}

	public void test_01_06_01() {
		// TODO: Test settings a category and make sure there are only 10
		assertTrue(false);
	}

	// 01_07_01 is not model
}