package ca.ualberta.cs.xpertsapp.model;

/**
 * A class with only static methods which are used to retrieve constant strings
 */
public class Constants {
	public static boolean isOnline = true;
	public static boolean userSync = false;
	public static boolean usersSync = false;
	public static boolean servicesSync = false;
	public static boolean tradesSync = false;

	public static boolean allowOnline = true;
	public static boolean allowPhotoDownload = true;
	public static boolean isTest = false;
	public static final String PREF_FILE = "XpertsPreferences";
	public static final String EMAIL_KEY = "email";
	public static final String LOGGED_IN = "loggedIn";

	public static final String IntentServiceName = "Intent_Service_Name";

	public static final String Shareable = "Public";
	public static final String notShareable = "Private";
	public static String testEmail = "test@email.com";

	/**
	 * @return The base URL of the server
	 */
	public static String serverBaseURL() {
		return "http://cmput301.softwareprocess.es:8080/cmput301f15t02/";
	}

	/**
	 * @return The URL extension for Users
	 */
	public static String serverUserExtension() {
		if (isTest) {
			return "users_test2/";
		}
		return "users/";
	}

	/**
	 * @return The URL extension for Services
	 */
	public static String serverServiceExtension() {
		if (isTest) {
			return "services_test2/";
		}
		return "services/";
	}

	/**
	 * @return The URL extension for Trades
	 */
	public static String serverTradeExtension() {
		if (isTest) {
			return "trades_test2/";
		}
		return "trades/";
	}

	/**
	 * @return The URL extension for Images
	 */
	public static String serverImageExtension() {
		if (isTest) {
			return "images_test2/";
		}
		return "images/";
	}

	/**
	 * @return The URL extension for searching
	 */
	public static String serverSearchExtension() {
		return "_search?size=999&q=";
	}

	/**
	 * @return The filename for caching Users
	 */
	public static String diskUser() {
		if (isTest) {
			return "users_test.sav";
		}
		return "users.sav";
	}

	/**
	 * @return The filename for caching Services
	 */
	public static String diskService() {
		if (isTest) {
			return "services_test.sav";
		}
		return "services.sav";
	}
}