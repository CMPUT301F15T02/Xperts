package ca.ualberta.cs.xpertsapp.UnitTests;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class ConfigTest extends TestCase {
	public ConfigTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getID());

		super.tearDown();
	}

	// 10.01.01 is not model

	public void test_10_02_01() {
		// Test edit profile
		User user = UserManager.sharedManager().localUser();

		String newName = "Some user's name";
		String newLocation = "canada";
		String newContact = "2390832490838042";
		user.setName(newName);
		user.setLocation(newLocation);
		user.setContactInfo(newContact);

		UserManager.sharedManager().clearCache();
		user = UserManager.sharedManager().localUser();
		assertEquals(user.getName(), newName);
		assertEquals(user.getLocation(), newLocation);
		assertEquals(user.getContactInfo(), newContact);
	}
}