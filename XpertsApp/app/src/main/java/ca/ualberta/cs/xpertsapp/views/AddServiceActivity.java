package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.models.Service;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;


public class AddServiceActivity extends Activity {

	private IOManager ioManager;
	private Users users;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_service);

		users = new Users();
		ioManager = new IOManager(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		user = (User) getIntent().getExtras().getSerializable("User");
	}

	// Button's function
	public void saveService(View view) {
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
	}

	// Thread that close the activity after finishing add
	private Runnable doFinishAdd = new Runnable() {
		public void run() {
			finish();
		}
	};
}


