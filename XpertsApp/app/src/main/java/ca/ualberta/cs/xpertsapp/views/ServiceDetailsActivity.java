package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.AddServiceController;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;


public class ServiceDetailsActivity extends Activity {
	private AddServiceController asc = new AddServiceController();
	private TextView theTitle;
	public TextView getTheTitle() {return theTitle;}
	private TextView isPublic;
	public TextView getIsPublic() {return isPublic;}
	private TextView category;
	public TextView getCategory() {return category;}
	private TextView description;
	public TextView getDescription() {return description;}
	private Button editButton;
	public Button getEditButton() {return editButton;}
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_details);
		theTitle = (TextView) findViewById(R.id.serviceTextView);
		isPublic = (TextView) findViewById(R.id.privacyTextView);
		category = (TextView) findViewById(R.id.categoryTextView);
		description = (TextView) findViewById(R.id.longDescriptionTextView);
		editButton = (Button) findViewById(R.id.editButton);
		intent = getIntent();
		String Service_id = intent.getStringExtra(Constants.IntentServiceName);
		Service service = ServiceManager.sharedManager().getService(Service_id);
		theTitle.setText(service.getName());
		if (service.isShareable())
			{isPublic.setText(Constants.Shareable);}
		else
			{isPublic.setText(Constants.notShareable);}
		category.setText(service.getCategory().toString());
		description.setText(service.getDescription());
		Notified();

 	}
	public void Notified(){
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			// I'm pretty sure we need to call notified here
			//Notified();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_service_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.delete_service) {
			asc.deleteService(getIntent().getStringExtra(Constants.IntentServiceName));
			Intent intent2 = new Intent(this, ViewProfileActivity.class);
			startActivity(intent2);
		}
		if (id == R.id.edit_service) {
			Intent intent3 = new Intent(this, AddServiceActivity.class);
			intent3.putExtra(Constants.IntentServiceName,intent.getStringExtra(Constants.IntentServiceName));
			startActivityForResult(intent3, 1);
		}

		return super.onOptionsItemSelected(item);
	}
}
