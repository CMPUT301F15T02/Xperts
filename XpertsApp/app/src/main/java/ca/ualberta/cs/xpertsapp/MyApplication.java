package ca.ualberta.cs.xpertsapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * FROM: http://stackoverflow.com/a/5114361/393009
 */
public class MyApplication extends Application {
	private static Context context;
	private static SharedPreferences preferences;

	public void onCreate() {
		super.onCreate();
		MyApplication.context = getApplicationContext();
		MyApplication.preferences = MyApplication.context.getSharedPreferences("XpertsPreferences", Context.MODE_PRIVATE);
	}

	public static Context getAppContext() {
		return MyApplication.context;
	}

	public static SharedPreferences getPreferences() { return MyApplication.preferences; }
}