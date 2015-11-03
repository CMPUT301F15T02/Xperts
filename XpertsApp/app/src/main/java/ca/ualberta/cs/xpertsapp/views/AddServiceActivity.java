package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Service;


public class AddServiceActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_service);
		EditText Title =(EditText) findViewById(R.id.editText);
		EditText Description = (EditText) findViewById(R.id.editText2);
		Spinner Category = (Spinner) findViewById(R.id.spinner);
		CheckBox Private = (CheckBox) findViewById(R.id.checkBox);
		CategoryList CL = CategoryList.sharedCategoryList();
		//Category.setAdapter();

	}

	@Override
	protected void onStart() {
		super.onStart();
	}


	public void saveService(View view) {
		//EditText title =
		Intent intent = new Intent(this, ViewProfileActivity.class);
		startActivity(intent);
	}


}



