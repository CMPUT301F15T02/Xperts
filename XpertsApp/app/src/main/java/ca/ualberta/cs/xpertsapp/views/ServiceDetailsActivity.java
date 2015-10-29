package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.models.Service;


public class ServiceDetailsActivity extends Activity {

	private Service service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_details);
	}

	@Override
	protected void onStart() {
		super.onStart();

		service = (Service) getIntent().getExtras().getSerializable("SERVICE");

		TextView name = (TextView) findViewById(R.id.detailsName);
		TextView description = (TextView) findViewById(R.id.detailsDescription);

		name.setText(service.getName());
		description.setText(service.getDescription());
	}
}
