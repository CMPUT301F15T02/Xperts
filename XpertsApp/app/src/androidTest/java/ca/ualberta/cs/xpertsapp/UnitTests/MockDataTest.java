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

	final String testEmail1 = "david@xperts.com";
	final String testEmail2 = "seann@xperts.com";
	final String testEmail3 = "kathleen@xperts.com";
	final String testEmail4 = "huy@xperts.com";
	final String testEmail5 = "justin@xperts.com";
	final String testEmail6 = "hammad@xperts.com";

	@Override
	protected void setUp2() {
		super.setUp2();
		User u1 = newTestUser(testEmail1,"David Skrundz","Calgary");
		User u2 = newTestUser(testEmail2,"Seann Murdock","Vancouver");
		User u3 = newTestUser(testEmail3,"Kathleen Baker","Toronto");
		User u4 = newTestUser(testEmail4,"Huy Truong","Seattle");
		User u5 = newTestUser(testEmail5,"Justin Stuehmer","Montreal");
		User u6 = newTestUser(testEmail6,"Hammad Jutt","Edmonton");

		u1.addService(newTestService("beard pattered","observable tunes upholstery walling.",4,false));
		u1.addService(newTestService("oxide stagnant","professing final lynchpin's humored.",2,true));
		u1.addService(newTestService("wives diverged","syllabus's rugby rheumatism contort.",4,true));
		u1.addService(newTestService("slams vigilant","intonation dials hemoglobin menials.",3,true));
		u1.addService(newTestService("later worthy's", "thirteen's pet's purchasing encores.", 8, true));
		u1.addFriend(u2);
		u1.addFriend(u3);
		u1.addFriend(u4);
		u1.addFriend(u5);
		u1.addFriend(u6);

		u2.addService(newTestService("vista parson's", "victorious furry technology pledged.", 0, false));
		u2.addService(newTestService("loved creasing", "dissecting leery scrounging morally.", 9, true));
		u2.addService(newTestService("breed shuttles", "festooning sheen demoralize amended.", 6, true));
		u2.addService(newTestService("blaze validate", "straighten peony reconciles recover.", 6, true));
		u2.addService(newTestService("slurs forgives", "shuttering today keepsake's gripped.", 2, true));
		u2.addFriend(u1);
		u2.addFriend(u3);
		u2.addFriend(u4);
		u2.addFriend(u5);
		u2.addFriend(u6);

		u3.addService(newTestService("cocks obsessed", "discolored drama overlaying bazaars.", 3, true));
		u3.addService(newTestService("throw spectrum", "techniques sloth princesses crown's.", 8, true));
		u3.addService(newTestService("bonds burger's", "exorbitant slink disgracing watches.", 0, false));
		u3.addService(newTestService("champ motioned", "watertight cut's tormenters linkage.", 4, true));
		u3.addService(newTestService("trial collages", "deliberate gal's appraisals drizzle.", 8, true));
		u3.addFriend(u1);
		u3.addFriend(u2);
		u3.addFriend(u4);
		u3.addFriend(u5);
		u3.addFriend(u6);

		u4.addService(newTestService("opals parlor's", "attendants tummy subsisting oftener.", 8, true));
		u4.addService(newTestService("cares procured", "impervious mired wretcheder liner's.", 6, true));
		u4.addService(newTestService("poled prospers", "gracefully newsy assumption warmest.", 4, true));
		u4.addService(newTestService("act's canary's", "endearment raced transacted seniors.", 6, true));
		u4.addService(newTestService("mends sulfur's", "demolished earls infantries concave.", 7, true));
		u4.addFriend(u1);
		u4.addFriend(u2);
		u4.addFriend(u3);
		u4.addFriend(u5);
		u4.addFriend(u6);

		u5.addService(newTestService("feats delivery", "consigning irony automatics imprint.", 5, false));
		u5.addService(newTestService("leaks cushions", "consonants rip's audition's starker.", 0, true));
		u5.addService(newTestService("rains silenced", "traversing pleat swivelling pastels.", 9, true));
		u5.addService(newTestService("mossy headache", "perfectest day's connecters saluted.", 9, false));
		u5.addService(newTestService("kinks lighting", "cautiously oases integrated memento.", 0, true));
		u5.addFriend(u1);
		u5.addFriend(u2);
		u5.addFriend(u3);
		u5.addFriend(u4);
		u5.addFriend(u6);

		u6.addService(newTestService("feats delivery", "consigning irony automatics imprint.", 5, false));
		u6.addService(newTestService("later worthy's", "thirteen's pet's purchasing encores.", 0, true));
		u6.addService(newTestService("throw spectrum", "techniques sloth princesses crown's.", 9, true));
		u6.addService(newTestService("mossy headache", "perfectest day's connecters saluted.", 9, false));
		u6.addService(newTestService("slams vigilant", "intonation dials hemoglobin menials.", 0, true));
		u6.addFriend(u1);
		u6.addFriend(u2);
		u6.addFriend(u3);
		u6.addFriend(u4);
		u6.addFriend(u5);

	}

	@Override
	protected void tearDown2(){
		super.tearDown2();
	}

	public void testMockData() {
		setUp2();

		User user = MyApplication.getLocalUser();
		User friend = UserManager.sharedManager().getUser(testEmail1);
		assertEquals(user.getEmail(), testLocalEmail);

		user.addFriend(friend);
		assertEquals(user.getFriends().get(0), friend);

		tearDown2();
	}

}