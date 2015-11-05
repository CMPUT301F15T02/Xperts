package ca.ualberta.cs.xpertsapp.UnitTests;

import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.IOManager;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.TradeManager;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.MainActivity;

public class TestCase extends ActivityInstrumentationTestCase2 {
	public TestCase() {
		super(MainActivity.class);
	}

	final String testLocalEmail = "test@email.com";
	final String testEmail1 = "david@xperts.com";
	final String testEmail2 = "seann@xperts.com";
	final String testEmail3 = "kathleen@xperts.com";
	final String testEmail4 = "huy@xperts.com";
	final String testEmail5 = "justin@xperts.com";
	final String testEmail6 = "hammad@xperts.com";

	protected SharedPreferences pref;

	@Override
	protected void setUp() throws Exception {
		// Prepare
		Constants.isTest = true;

		pref = MyApplication.getContext().getSharedPreferences(Constants.PREF_FILE, 0);
		pref.edit().putString(Constants.EMAIL_KEY, testLocalEmail);
		pref.edit().putBoolean(Constants.LOGGED_IN, true);
		pref.edit().apply();
		UserManager.sharedManager().registerUser(testLocalEmail);
		createMockData();

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// Cleanup
		UserManager.sharedManager().clearCache();
		ServiceManager.sharedManager().clearCache();
		TradeManager.sharedManager().clearCache();

		IOManager.sharedManager().deleteData(Constants.serverUserExtension());
		IOManager.sharedManager().deleteData(Constants.serverServiceExtension());
		IOManager.sharedManager().deleteData(Constants.serverTradeExtension());

		pref.edit().clear();
		pref.edit().apply();

		Constants.isTest = false;

		super.tearDown();
	}

	private void createMockData() {

		TestUser u1 = new TestUser(testEmail1,"David Skrundz","Calgary");
		TestUser u2 = new TestUser(testEmail2,"Seann Murdock","Vancouver");
		TestUser u3 = new TestUser(testEmail3,"Kathleen Baker","Toronto");
		TestUser u4 = new TestUser(testEmail4,"Huy Truong","Seattle");
		TestUser u5 = new TestUser(testEmail5,"Justin Stuehmer","Montreal");
		TestUser u6 = new TestUser(testEmail6,"Hammad Jutt","Edmonton");

		u1.addService(new TestService(u1.getEmail(), "beard pattered","observable tunes upholstery walling.",4,false));
		u1.addService(new TestService(u1.getEmail(), "oxide stagnant","professing final lynchpin's humored.",2,true));
		u1.addService(new TestService(u1.getEmail(), "wives diverged","syllabus's rugby rheumatism contort.",4,true));
		u1.addService(new TestService(u1.getEmail(), "slams vigilant","intonation dials hemoglobin menials.",3,true));
		u1.addService(new TestService(u1.getEmail(), "later worthy's","thirteen's pet's purchasing encores.",8,true));

		u2.addService(new TestService(u2.getEmail(), "vista parson's","victorious furry technology pledged.",0,false));
		u2.addService(new TestService(u2.getEmail(), "loved creasing","dissecting leery scrounging morally.",9,true));
		u2.addService(new TestService(u2.getEmail(), "breed shuttles","festooning sheen demoralize amended.",6,true));
		u2.addService(new TestService(u2.getEmail(), "blaze validate","straighten peony reconciles recover.",6,true));
		u2.addService(new TestService(u2.getEmail(), "slurs forgives","shuttering today keepsake's gripped.",2,true));

		u3.addService(new TestService(u3.getEmail(), "cocks obsessed","discolored drama overlaying bazaars.",3,true));
		u3.addService(new TestService(u3.getEmail(), "throw spectrum","techniques sloth princesses crown's.",8,true));
		u3.addService(new TestService(u3.getEmail(), "bonds burger's","exorbitant slink disgracing watches.",0,false));
		u3.addService(new TestService(u3.getEmail(), "champ motioned","watertight cut's tormenters linkage.",4,true));
		u3.addService(new TestService(u3.getEmail(), "trial collages","deliberate gal's appraisals drizzle.",8,true));

		u4.addService(new TestService(u4.getEmail(), "opals parlor's","attendants tummy subsisting oftener.",8,true));
		u4.addService(new TestService(u4.getEmail(), "cares procured","impervious mired wretcheder liner's.",6,true));
		u4.addService(new TestService(u4.getEmail(), "poled prospers","gracefully newsy assumption warmest.",4,true));
		u4.addService(new TestService(u4.getEmail(), "act's canary's","endearment raced transacted seniors.",6,true));
		u4.addService(new TestService(u4.getEmail(), "mends sulfur's","demolished earls infantries concave.",7,true));

		u5.addService(new TestService(u5.getEmail(), "feats delivery","consigning irony automatics imprint.",5,false));
		u5.addService(new TestService(u5.getEmail(), "leaks cushions","consonants rip's audition's starker.",0,true));
		u5.addService(new TestService(u5.getEmail(), "rains silenced","traversing pleat swivelling pastels.",9,true));
		u5.addService(new TestService(u5.getEmail(), "mossy headache","perfectest day's connecters saluted.",9,false));
		u5.addService(new TestService(u5.getEmail(), "kinks lighting","cautiously oases integrated memento.",0,true));

		u6.addService(new TestService(u6.getEmail(), "feats delivery", "consigning irony automatics imprint.", 5, false));
		u6.addService(new TestService(u6.getEmail(), "later worthy's","thirteen's pet's purchasing encores.",0,true));
		u6.addService(new TestService(u6.getEmail(), "throw spectrum","techniques sloth princesses crown's.",9,true));
		u6.addService(new TestService(u6.getEmail(), "mossy headache","perfectest day's connecters saluted.",9,false));
		u6.addService(new TestService(u6.getEmail(), "slams vigilant","intonation dials hemoglobin menials.",0,true));
	}
}