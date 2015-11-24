package ca.ualberta.cs.xpertsapp.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ca.ualberta.cs.xpertsapp.R;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;

/**
 * This displays the info about a friend's service.
 * It is called from FriendProfileActivity.
 */
public class FriendServiceDetailsActivity extends Activity {
    private TextView theTitle;
    public TextView getTheTitle() {return theTitle;}
    private TextView category;
    public TextView getCategory() {return category;}
    private TextView description;
    public TextView getDescription() {return description;}
    private Intent intent;
    private FriendServiceDetailsActivity activity = this;
    private Service service;

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
        description = (TextView) findViewById(R.id.friendLongDescription);
        intent = getIntent();
        String Service_id = intent.getStringExtra(Constants.IntentServiceName);
        service = ServiceManager.sharedManager().getService(Service_id);
        theTitle.setText(service.getName());
        category.setText(service.getCategory().toString());
        description.setText(service.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
    public void goToOfferTrade(View view) {
        Intent intent = new Intent(this, OfferTradeActivity.class);
        intent.putExtra("INTENT_SERVICEID", service.getID());
        startActivity(intent);
    }

}