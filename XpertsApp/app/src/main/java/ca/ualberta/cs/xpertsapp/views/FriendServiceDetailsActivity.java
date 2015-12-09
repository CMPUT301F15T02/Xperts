package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.controllers.AddServiceController;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;

/**
 * This displays the info about a friend's service.
 * It is called from FriendProfileActivity.
 */
public class FriendServiceDetailsActivity extends Activity {

    private AddServiceController asc = new AddServiceController();
    private TextView theTitle;
    public TextView getTheTitle() {return theTitle;}
    private Button cloneBtn;
    public Button getCloneBtn() {return cloneBtn;}
    private Button tradeBtn;
    public Button getTradeBtn() {return tradeBtn;}
    private TextView category;
    public TextView getCategory() {return category;}
    private TextView description;
    public TextView getDescription() {return description;}
    private Intent intent;
    private ImageView imageView;
    public ImageView getImageView() {return imageView;}
    private FriendServiceDetailsActivity activity = this;
    private Service service;
    private CheckBox cb;

    /**
     * This sets all the TextViews with the service information.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_service_details);
        theTitle = (TextView) findViewById(R.id.friendServiceTV);
        category = (TextView) findViewById(R.id.friendCategory);
        imageView = (ImageView) findViewById(R.id.friendImageView);
        description = (TextView) findViewById(R.id.friendLongDescription);
        intent = getIntent();
        String Service_id = intent.getStringExtra(Constants.IntentServiceName);
        service = ServiceManager.sharedManager().getService(Service_id);
        theTitle.setText(service.getName());
        category.setText(service.getCategory().toString());
        description.setText(service.getDescription());
        try {
            if (!(service.getPictures().isEmpty())) {
                imageView.setImageBitmap(service.getPictures().get(0));
            }
        } catch (Exception e) {};
        cloneBtn = (Button) findViewById(R.id.cloneBtn2);
        cloneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cloneService();
            }
        });
        tradeBtn = (Button) findViewById(R.id.makeATrade2);
        tradeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToOfferTrade(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_service_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

        }
    }
    private void cloneService() {
        asc.cloneService(service.getName(), service.getDescription(), service.getCategory(), service.getPictures());
        Context context = getApplicationContext();
        CharSequence text = "Service Cloned";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void goToOfferTrade(View view) {
        Intent intent = new Intent(this, OfferTradeActivity.class);
        intent.putExtra("INTENT_SERVICEID", service.getID());
        startActivity(intent);
    }

}