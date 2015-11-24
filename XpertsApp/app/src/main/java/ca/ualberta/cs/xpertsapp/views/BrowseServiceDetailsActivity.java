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

public class BrowseServiceDetailsActivity extends Activity {
    private TextView theTitle;
    public TextView getTheTitle() {return theTitle;}
    private TextView category;
    public TextView getCategory() {return category;}
    private TextView description;
    public TextView getDescription() {return description;}
    private Intent intent;
    private BrowseServiceDetailsActivity activity = this;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_service_details);
        theTitle = (TextView) findViewById(R.id.serviceTitle2);
        category = (TextView) findViewById(R.id.categoryTextView2);
        description = (TextView) findViewById(R.id.longDescriptionTextView2);
        intent = getIntent();
        String Service_id = intent.getStringExtra("INTENT_SERVICEID");
        service = ServiceManager.sharedManager().getService(Service_id);
        theTitle.setText(service.getName());
        category.setText(service.getCategory().toString());
        description.setText(service.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_service_details, menu);
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

    public void goToOfferTrade(View view) {
        Intent intent = new Intent(this, OfferTradeActivity.class);
        intent.putExtra("INTENT_SERVICEID", service.getID());
        startActivity(intent);
    }
}
