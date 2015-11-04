package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;


public class ServiceDetailsActivity extends Activity {
	private TextView theTitle;
	public TextView getTheTitle() {return theTitle;};
	private TextView isPublic;
	public TextView getIsPublic() {return isPublic;};
	private TextView category;
	public TextView getCategory() {return category;};
	private TextView description;
	public TextView getDescription() {return description;};
	private Button editButton;
	public Button getEditButton() {return editButton;};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_details);
		theTitle = (TextView) findViewById(R.id.serviceTextView);
		isPublic = (TextView) findViewById(R.id.privacyTextView);
		category = (TextView) findViewById(R.id.categoryTextView);
		description = (TextView) findViewById(R.id.longDescriptionTextView);
		editButton = (Button) findViewById(R.id.editButton);
		Intent intent = getIntent();
		String Service_id = intent.getStringExtra(Constants.IntentServiceName);
		Service service = ServiceManager.sharedManager().getService(Service_id);
		theTitle.setText(service.getName());
		if (service.isShareable())
			{isPublic.setText(Constants.Shareable);}
		else
			{isPublic.setText(Constants.notShareable);}
		category.setText(service.getCategory().toString());
		description.setText(service.getDescription());

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
