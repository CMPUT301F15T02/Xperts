package ca.ualberta.cs.xpertsapp.model;

import android.provider.Settings.Secure;

import ca.ualberta.cs.xpertsapp.MyApplication;

/**
 * A class with only static methods which are used to retrieve constant strings
 */
public class Constants {
	/**
	 * @return The string representing the devices UUID
	 */
	public static String deviceUUID() {
		return Secure.getString(MyApplication.getAppContext().getContentResolver(), Secure.ANDROID_ID);
	}

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
		return "users/";
	}

	/**
	 * @return The URL extension for Services
	 */
	public static String serverServiceExtension() {
		return "services/";
	}

	/**
	 * @return The URL extension for Trades
	 */
	public static String serverTradeExtension() {
		return "trades/";
	}
}