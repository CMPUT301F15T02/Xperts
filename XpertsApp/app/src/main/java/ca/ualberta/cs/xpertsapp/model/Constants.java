package ca.ualberta.cs.xpertsapp.model;

import android.provider.Settings.Secure;

import ca.ualberta.cs.xpertsapp.MyApplication;

/**
 * A class with only static methods which are used to retrieve constant strings
 */
public class Constants {
	public static boolean allowOnline = true;
	public static boolean allowPhotoDownload = true;
	public static boolean isTest = false;

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
			return "users_test/";
		}
		return "users/";
	}

	/**
	 * @return The URL extension for Services
	 */
	public static String serverServiceExtension() {
		if (isTest) {
			return "services_test/";
		}
		return "services/";
	}

	/**
	 * @return The URL extension for Trades
	 */
	public static String serverTradeExtension() {
		if (isTest) {
			return "trades_test/";
		}
		return "trades/";
	}

	/**
	 * @return The URL extension for searching
	 */
	public static String serverSearchExtension() {
		return "_search?q=";
	}
}