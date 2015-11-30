package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.AddServiceController;
import ca.ualberta.cs.xpertsapp.model.CategoryList;
import ca.ualberta.cs.xpertsapp.model.Category;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;

/**
 * Activity that allows the user to add or edit a service.
 * It is called from either:
 * @see ViewProfileActivity
 * @see ServiceDetailsActivity
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
	private Button photoButton;
	public Button getPhotoButton() {return photoButton;}
	private Intent intent;
	//public Intent gettheIntent() {return intent;}
	private int REQUEST_TAKE_PHOTO = 1;
	private List<Bitmap> pictures;
	public List<Bitmap> getPictures() {return pictures;}


	/**
	 * This sets the title, description, category and private widgets.
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
		photoButton = (Button) findViewById(R.id.imageButton);
		pictures = new ArrayList<Bitmap>();
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
	 * @param view the save button
	 */
	public void saveService(View view) {
		int index =  getCategories().getSelectedItemPosition();
		Category category = CL.getCategories().get(index);
		if (intent.getStringExtra(Constants.IntentServiceName) == null){
			try {
				asc.addService(getTheTitle(), getDescription(), category, getPrivate(),getPictures());
			}
			catch (RuntimeException e){
				Toast.makeText(getApplicationContext(), "Runtime error",
						Toast.LENGTH_LONG).show();
			}
		}
		else{
			try{
				asc.editService(getTheTitle(), getDescription(), category, getPrivate(),intent.getStringExtra(Constants.IntentServiceName), getPictures());
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

	/**
	 * this code is from http://developer.android.com/training/camera/photobasics.html
	 * it is about saving photos to files. Useful for larger files
	 */
	String mCurrentPhotoPath;

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,
				".jpg",
				storageDir
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	public void dispatchTakePictureIntent(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				//Toast.makeText(this,photoFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
				getIntent().putExtra("filename", photoFile.getAbsolutePath());
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

			}
		}
	}

/* When add a picture is selected this method dispaches the take picture activity using the phone's camera
	public void dispatchTakePictureIntent(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
		}
	}
/*When the take picture activity returns this function stores the Bitmap image into the the activity variable pictures
when the save button is pressed the pictures are added to the service.
 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
			//Toast.makeText(this,getIntent().getStringExtra("filename"), Toast.LENGTH_LONG).show();
			//Toast.makeText(this,data.getStringExtra("filename"), Toast.LENGTH_LONG).show();
			Bitmap bMap = BitmapFactory.decodeFile(getIntent().getStringExtra("filename"));
			getPictures().add(bMap);
			/* old code from unsaved bitmaps
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			getPictures().add(imageBitmap);
			*/
		}
	}

}