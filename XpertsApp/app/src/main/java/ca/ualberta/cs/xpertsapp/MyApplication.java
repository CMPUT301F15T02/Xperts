package ca.ualberta.cs.xpertsapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.LoginActivity;

/**
 * Custom application class to store login state and active user
 * Credits: http://stackoverflow.com/a/5114361/393009
 */
public class MyApplication extends Application {
	private static Context context;
	private static SharedPreferences preferences;
	private static SharedPreferences.Editor editor;

	private static final String FILE_NAME = Constants.PREF_FILE;
	public static final String EMAIL_KEY = Constants.EMAIL_KEY;
	public static final String LOGGED_IN = Constants.LOGGED_IN;
	int PRIVATE_MODE = 0;
	private static boolean online;

	/**
	 *  Sets up context and {@link SharedPreferences}
	 */
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
	 * @return The xperts shared preferences
	 */
	public static SharedPreferences getPreferences() { return MyApplication.preferences; }

	/**
	 * Displays login screen if user is not logged in
	 * @see #loginScreen()
	 */
	public static void loginCheck() {

		if(!MyApplication.preferences.getBoolean(LOGGED_IN, false) && !Constants.isTest){
			loginScreen();
		}
	}

	/**
	 * @return The active user's email stored in shared preferences
	 */
	public static String getLocalEmail() {
		return MyApplication.preferences.getString(EMAIL_KEY, null);
	}

	/**
	 * Registers the local user with the {@link UserManager} if not registered already
	 * @return The active user for the app
	 */
	public static User getLocalUser() {
		if(Constants.isTest){
			return UserManager.sharedManager().registerUser(Constants.testEmail);
		}
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
	 * Stores the login state and email into the shared preferences
	 */
	public static void login(String email){
		MyApplication.editor.putString(EMAIL_KEY, email);
		MyApplication.editor.putBoolean(LOGGED_IN, true);
		MyApplication.editor.commit();
	}

	/**
	 * Logs the user out. Removes login state and email from shared preferences and displays login screen.
	 * @see #loginScreen()
	 */
	public static void logout(){
		MyApplication.editor.clear();
		MyApplication.editor.commit();
		loginScreen();
		Toast.makeText(MyApplication.getContext(), "Already Logged Out", Toast.LENGTH_SHORT).show();
	}

	/**
	 * Displays a new login screen
	 * @see LoginActivity
	 */
	private static void loginScreen(){
		Intent login = new Intent(MyApplication.context, LoginActivity.class);
		login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MyApplication.context.startActivity(login);
	}

	// Doesn't work for emulator
	/*public static boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		return netInfo != null && netInfo.isConnected();
	}*/
}