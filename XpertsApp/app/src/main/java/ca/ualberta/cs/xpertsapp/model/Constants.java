package ca.ualberta.cs.xpertsapp.model;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class Constants {
	public static String localUserString() { return "ølocal¶userø"; }

	public static String deviceUUID() {
		TelephonyManager tManager = (TelephonyManager)(new Activity()).getSystemService(Context.TELEPHONY_SERVICE);
		return tManager.getDeviceId();
	}
}