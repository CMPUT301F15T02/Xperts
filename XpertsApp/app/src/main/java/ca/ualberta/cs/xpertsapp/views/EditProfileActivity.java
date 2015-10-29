package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;

import ca.ualberta.cs.xpertsapp.R;

public class EditProfileActivity extends Activity {
	private Button addPhotoButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_edit_profile);

		this.addPhotoButton = (Button) this.findViewById(R.id.editProfileActivity_AddImage);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}
		
		return super.onOptionsItemSelected(item);
	}

	public Button getAddPhotoButton() {
		return this.addPhotoButton;
	}

	public List<Bitmap> getPhotos() {
		return null;
	}
}
