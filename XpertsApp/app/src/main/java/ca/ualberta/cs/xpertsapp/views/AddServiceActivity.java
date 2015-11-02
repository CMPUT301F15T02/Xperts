package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Service;


public class AddServiceActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_service);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	public void saveService(View view) {
		Intent intent = new Intent(this, ViewProfileActivity.class);
		startActivity(intent);
	}
}


