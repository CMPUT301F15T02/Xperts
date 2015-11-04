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
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.AddServiceController;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.Service;


public class AddServiceActivity extends Activity {
	private AddServiceController asc = new AddServiceController();
	private Spinner Categories;
	public Spinner getCategories() {return Categories;};
	private EditText Title;
	public EditText gettheTitle() {return Title;};
	private EditText Description;
	public EditText getDescription() {return Description;};
	private CheckBox Private;
	public CheckBox getPrivate() {return Private;};
	private CategoryList CL;
	public CategoryList getCL() {return CL;};
	private Button SaveButton;
	public Button getSaveButton() {return SaveButton;};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_service);
		SaveButton = (Button) findViewById(R.id.saveButon);
		Categories = (Spinner) findViewById(R.id.spinner);
		Title =(EditText) findViewById(R.id.editText);
		Description = (EditText) findViewById(R.id.editText2);
		Private = (CheckBox) findViewById(R.id.checkBox);
		CL = CategoryList.sharedCategoryList();
		//Category.setAdapter();
		ArrayAdapter<Category> categoryarrayadapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item,getCL().getCategories());
		Categories.setAdapter(categoryarrayadapter);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}


	public void saveService(View view) {
		int index =  getCategories().getSelectedItemPosition();
		Category category = CL.getCategories().get(index);
		try {
			asc.addService(gettheTitle(), getDescription(), category, getPrivate(), getCL());
		}
		catch (RuntimeException e){
			Toast.makeText(getApplicationContext(), "Runtime error",
					Toast.LENGTH_LONG).show();
		}
		finish();
		//Intent intent = new Intent(this, ViewProfileActivity.class);
		//startActivity(intent);
	}


}
