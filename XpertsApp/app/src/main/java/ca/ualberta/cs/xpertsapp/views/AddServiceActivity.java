package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.AddServiceController;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;

/**
 * Activity that allows the user to add or edit a service.
 * It is called from ViewProfileActivity or ServiceDetailsActivity.
 */
public class AddServiceActivity extends Activity {
	private AddServiceController asc = new AddServiceController();
	private Spinner Categories;
	public Spinner getCategories() {return Categories;}
	private EditText Title;
	public EditText getTheTitle() {return Title;}
	private EditText Description;
	public EditText getDescription() {return Description;}
	private CheckBox Private;
	public CheckBox getPrivate() {return Private;}
	private CategoryList CL;
	public CategoryList getCL() {return CL;}
	private Button SaveButton;
	public Button getSaveButton() {return SaveButton;}
	private Intent intent;

	/**
	 * This sets the title, description, category and private widgets.
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_service);
		SaveButton = (Button) findViewById(R.id.saveButon);
		Categories = (Spinner) findViewById(R.id.spinner);
		Title = (EditText) findViewById(R.id.editText);
		Description = (EditText) findViewById(R.id.editText2);
		Private = (CheckBox) findViewById(R.id.checkBox);
		CL = CategoryList.sharedCategoryList();
		//Category.setAdapter();
		ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item,getCL().getCategories());
		Categories.setAdapter(categoryArrayAdapter);
		intent = getIntent();
		if (intent.getStringExtra(Constants.IntentServiceName)!= null){
			String Service_id = intent.getStringExtra(Constants.IntentServiceName);
			Service service = ServiceManager.sharedManager().getService(Service_id);
			Title.setText(service.getName());
			Description.setText(service.getDescription());
			if (service.isShareable()){
				Private.setChecked(false);
			} else {
				Private.setChecked(true);
			}
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * This is called when the save button is pressed. It adds a service or edits a service if it already exists.
	 * @param view
	 */
	public void saveService(View view) {
		int index =  getCategories().getSelectedItemPosition();
		Category category = CL.getCategories().get(index);
		if (intent.getStringExtra(Constants.IntentServiceName) == null){
			try {
				asc.addService(getTheTitle(), getDescription(), category, getPrivate(), getCL());
			}
			catch (RuntimeException e){
				Toast.makeText(getApplicationContext(), "Runtime error",
						Toast.LENGTH_LONG).show();
			}
		}
		else{
			try{
				asc.editService(getTheTitle(), getDescription(), category, getPrivate(), getCL(),intent.getStringExtra(Constants.IntentServiceName));
			}
			catch (RuntimeException e){
				Toast.makeText(getApplicationContext(), "Runtime error",
						Toast.LENGTH_LONG).show();
			}
		}
		setResult(RESULT_OK);
		Intent intent = new Intent(this, ViewProfileActivity.class);
		startActivity(intent);
	}
}