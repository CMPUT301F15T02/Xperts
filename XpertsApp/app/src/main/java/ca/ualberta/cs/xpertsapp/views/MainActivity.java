package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import ca.ualberta.cs.xpertsapp.R;

public class MainActivity extends Activity {
	private Button viewProfileButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		this.viewProfileButton = (Button)this.findViewById(R.id.mainActivity_ProfileButton);
	}

	public Button getViewProfileButton() {
		return this.viewProfileButton;
	}
}