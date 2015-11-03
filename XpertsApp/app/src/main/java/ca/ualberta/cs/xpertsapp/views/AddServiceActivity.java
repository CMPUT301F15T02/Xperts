package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
<<<<<<< HEAD
import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.models.Service;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;
=======
import ca.ualberta.cs.xpertsapp.model.Service;
>>>>>>> hammad


public class AddServiceActivity extends Activity {

<<<<<<< HEAD
	private IOManager ioManager;
	private Users users;
	private User user;
=======
>>>>>>> hammad

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_service);

<<<<<<< HEAD
		users = new Users();
		ioManager = new IOManager(this);
=======
>>>>>>> hammad
	}

	@Override
	protected void onStart() {
		super.onStart();
<<<<<<< HEAD

		user = (User) getIntent().getExtras().getSerializable("User");
=======
>>>>>>> hammad
	}

	// Button's function
	public void saveService(View view) {
<<<<<<< HEAD
		TextView id = (TextView) findViewById(R.id.detailsIdText);
		TextView name = (TextView) findViewById(R.id.detailsNameText);
		TextView description = (TextView) findViewById(R.id.detailsDescriptionText);

		Service newService = new Service();
		newService.setId(id.getText().toString());
		newService.setName(name.getText().toString());
		newService.setDescription(description.getText().toString());

		user.addService(newService);
		// Execute the thread to write to server
		Thread thread = new AddThread(user);
		thread.start();
	}

	class AddThread extends Thread {
		private User user;

		public AddThread(User user) {
			this.user = user;
		}

		@Override
		public void run() {
			ioManager.addUserToServer(user);

			// Give some time to get updated info
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			runOnUiThread(doFinishAdd);
		}
=======
		Intent intent = new Intent(this, ViewProfileActivity.class);
		startActivity(intent);
>>>>>>> hammad
	}
}


