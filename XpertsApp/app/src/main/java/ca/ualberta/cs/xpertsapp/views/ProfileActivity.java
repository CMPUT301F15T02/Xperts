package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.User;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class ProfileActivity extends Activity {
	private Button newServiceButton;

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_profile);

		// Link to UI
		this.newServiceButton = (Button)this.findViewById(R.id.profileActivity_NewService);

		// Load from Intent
		Intent intent = getIntent();
		// TODO: CONVERT ID TO STRING
		String email = intent.getStringExtra(R.string.id_user_email);
		this.user = UserManager.sharedUserManager().findUser(email);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_profile, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}
		
		return super.onOptionsItemSelected(item);
	}

	public Button getNewServiceButton() {
		return this.newServiceButton;
	}
}