package ca.ualberta.cs.xpertsapp.model;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * A class with only static methods which are used to retrieve constant strings
 */
public class Constants {
	/**
	 * @return The string used to represent wanting to load the local user
	 */
	public static String localUserString() { return "ølocal¶userø"; }

	/**
	 * @return The string representing the devices UUID
	 */
	public static String deviceUUID() {
		TelephonyManager tManager = (TelephonyManager)(new Activity()).getSystemService(Context.TELEPHONY_SERVICE);
		return tManager.getDeviceId();
	}

	/**
	 * @return The string used to represent the absence of data from the IOManager
	 */
	public static String nullDataString() { return "øøøøøøøøøø"; }
}