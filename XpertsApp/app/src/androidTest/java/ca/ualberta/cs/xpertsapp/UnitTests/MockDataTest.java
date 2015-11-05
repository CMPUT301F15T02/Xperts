package ca.ualberta.cs.xpertsapp.UnitTests;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class MockDataTest extends TestCase {
	public MockDataTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		IOManager.sharedManager().deleteData(Constants.serverUserExtension() + UserManager.sharedManager().localUser().getEmail());

		super.tearDown();
	}

	public void testMockData() {
		User user = MyApplication.getLocalUser();
		User friend = UserManager.sharedManager().getUser(testEmail1);
		assertEquals(user.getEmail(), testLocalEmail);

		user.addFriend(friend);
		assertEquals(user.getFriends().get(0), friend);
	}
}