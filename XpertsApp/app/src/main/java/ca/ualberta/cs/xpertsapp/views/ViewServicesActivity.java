package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.datamanagers.IOManager;
import ca.ualberta.cs.xpertsapp.interfaces.Observable;
import ca.ualberta.cs.xpertsapp.interfaces.Observer;
import ca.ualberta.cs.xpertsapp.models.Service;
import ca.ualberta.cs.xpertsapp.models.User;
import ca.ualberta.cs.xpertsapp.models.Users;


public class ViewServicesActivity extends Activity implements Observer {

	private String userEmail;
	private User user;
	private Users users;
	private ArrayList<Service> services;
	private ListView serviceList;
	private ArrayAdapter<Service> servicesViewAdapter;
	private IOManager ioManager;
	private Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_services);

		serviceList = (ListView) findViewById(R.id.serviceList);
		users = new Users();
		ioManager = new IOManager(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		userEmail = getIntent().getExtras().getString("EMAIL");
		// Search user by email
		Thread thread = new GetThread(userEmail);
		thread.start();


		// Show details when click on a service
		serviceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Service service = services.get(pos);

				Intent intent = new Intent(mContext, ServiceDetailsActivity.class);
				intent.putExtra("SERVICE", service);
				startActivity(intent);
			}
		});

		// Delete service on long click
		serviceList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Service service = services.get(position);
				Toast.makeText(mContext, "Deleting " + service.getName(), Toast.LENGTH_LONG).show();

				services.remove(position);

				// Thread to overide user
				Thread thread = new AddThread(user);
				thread.start();

				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/** 
	 * Called when the model changes
	 */
	public void notifyUpdated(Observable observable) {
		// Thread to update adapter after an operation
		Runnable doUpdateGUIList = new Runnable() {
			public void run() {
				servicesViewAdapter.notifyDataSetChanged();
			}
		};
		runOnUiThread(doUpdateGUIList);
	}

	// Button's function
	public void add(View view) {
		Intent intent = new Intent(mContext, AddServiceActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
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

			runOnUiThread(doUpdateGUIDetails);
		}
	}

	private Runnable doUpdateGUIDetails = new Runnable() {
		public void run() {
			servicesViewAdapter.notifyDataSetChanged();
		}
	};

	class GetThread extends Thread {
		private String email;

		public GetThread(String email) {
			this.email = email;
		}

		@Override
		public void run() {
			user = ioManager.getUser(email);

			runOnUiThread(doFinishGet);
		}
	}

	private Runnable doFinishGet = new Runnable() {
		public void run() {
			services = user.getServices();
			servicesViewAdapter = new ArrayAdapter<Service>(ViewServicesActivity.this, R.layout.list_item, services);
			serviceList.setAdapter(servicesViewAdapter);		}
	};
}