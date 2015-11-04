package ca.ualberta.cs.xpertsapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.LoginActivity;

/**
 * FROM: http://stackoverflow.com/a/5114361/393009
 */
public class MyApplication extends Application {
	private static Context context;
	private static SharedPreferences preferences;
	private static SharedPreferences.Editor editor;

	private static final String FILE_NAME = Constants.PREF_FILE;
	public static final String EMAIL_KEY = Constants.EMAIL_KEY;
	public static final String LOGGED_IN = Constants.LOGGED_IN;
	int PRIVATE_MODE = 0;

	public void onCreate() {
		super.onCreate();
		MyApplication.context = getApplicationContext();
		MyApplication.preferences = MyApplication.context.getSharedPreferences(FILE_NAME, PRIVATE_MODE);
		MyApplication.editor = MyApplication.preferences.edit();
	}

	/**
	 * @return The application context
	 */
	public static Context getContext() {
		return MyApplication.context;
	}

	/**
	 * @return The shared preferences
	 */
	public static SharedPreferences getPreferences() { return MyApplication.preferences; }

	/**
	 * Displays login screen if user is not logged in
	 */
	public static void loginCheck() {
		if(!MyApplication.preferences.getBoolean(LOGGED_IN, false)){
			loginScreen();
		}
	}

	/**
	 * @return The email stored in shared preferences
	 */
	public static String getLocalEmail() {
		return MyApplication.preferences.getString(EMAIL_KEY, null);
	}

	/**
	 * @return The email stored in shared preferences
	 */
	public static User getLocalUser() {
		String email = MyApplication.getLocalEmail();
		if (email == null) {
			throw new RuntimeException();
		}
		User user = UserManager.sharedManager().getUser(email);
		if (user == null) {
			user = UserManager.sharedManager().registerUser(email);
		}
		return user;
	}

	/**
	 * Logs the user in
	 */
	public static void login(String email){
		MyApplication.editor.putString(EMAIL_KEY, email);
		MyApplication.editor.putBoolean(LOGGED_IN, true);
		MyApplication.editor.commit();
	}

	/**
	 * Logs the user out and displays login screen
	 */
	public static void logout(){
		if(!MyApplication.preferences.getBoolean(LOGGED_IN, false)){
			MyApplication.editor.clear();
			MyApplication.editor.commit();
			loginScreen();
		}
	}

	/**
	 * Displays a new login screen
	 */
	private static void loginScreen(){
		Intent login = new Intent(MyApplication.context, LoginActivity.class);
		login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MyApplication.context.startActivity(login);
	}

}