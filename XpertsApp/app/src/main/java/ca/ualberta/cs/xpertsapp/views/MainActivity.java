package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.UserManager;

public class MainActivity extends Activity {
	private Button viewProfileButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		// Link to UI
		this.viewProfileButton = (Button) this.findViewById(R.id.mainActivity_ProfileButton);
		final MainActivity self = this;
		this.viewProfileButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(self, ProfileActivity.class);
				intent.putExtra(self.getString(R.string.id_user_email), UserManager.sharedUserManager().findUser(self.getString(R.string.id_local_user)).getEmail());
				self.startActivity(intent);
			}
		});
	}

	public Button getViewProfileButton() {
		return this.viewProfileButton;
	}
}