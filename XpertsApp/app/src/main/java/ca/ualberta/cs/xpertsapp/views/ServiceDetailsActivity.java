package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Service;


public class ServiceDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_details);
	}

	@Override
	protected void onStart() {
		super.onStart();


	}

	public void editService(View view) {
		Intent intent = new Intent(this, AddServiceActivity.class);
		startActivity(intent);
	}
}
