package ca.ualberta.cs.xpertsapp;

import android.app.Application;
import android.content.Context;

/**
 * FROM: http://stackoverflow.com/a/5114361/393009
 */
public class MyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}