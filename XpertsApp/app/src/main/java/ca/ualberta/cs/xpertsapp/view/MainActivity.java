package ca.ualberta.cs.xpertsapp.view;

import android.app.Activity;
import android.os.Bundle;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Thread thread = new Thread() {
			@Override
			public void run() {
				UserManager.sharedManager().localUser();
			}
		};
		thread.start();
	}
}