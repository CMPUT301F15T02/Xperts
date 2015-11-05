package ca.ualberta.cs.xpertsapp.BrowseTests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import ca.ualberta.cs.xpertsapp.controllers.AddServiceController;
import ca.ualberta.cs.xpertsapp.views.AddServiceActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;
import ca.ualberta.cs.xpertsapp.views.ViewProfileActivity;

public class BrowseServicesTest extends ActivityInstrumentationTestCase2 {
        private AddServiceController asc = new AddServiceController();
        private Spinner Categories;
        private EditText Title;
        private EditText Description;
        private CheckBox Private;
        private Button saveButton;
        private Button profileButton;
        private Button addServiceButton;


        public BrowseServicesTest() {
                super(MainActivity.class);

        }
        /**
         * UC01.03.01
         */


        public void testAddService(){
                setActivityInitialTouchMode(true);
                //Navigate from Main menu
                MainActivity activity = (MainActivity) getActivity();
                profileButton = activity.getMyProfileBtn();
                activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                profileButton.performClick();
                        }
                });
                getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish
                //Navigate from View profile
                ViewProfileActivity activity1 = (ViewProfileActivity) getActivity();
                addServiceButton = activity1.getAddService();
                activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                addServiceButton.performClick();
                        }
                });
                getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish
                AddServiceActivity activity2 = (AddServiceActivity) getActivity();

                Title = activity2.getTheTitle();
                activity.runOnUiThread(new Runnable() {
                        public void run() {
                                Title.setText("Test Service 1");
                        }
                });
                Categories = activity2.getCategories();
                activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                Categories.setSelection(2);
                        }
                });
                Description = activity2.getDescription();
                activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                Description.setText("This is a description of an example service.");
                        }
                });
                Private = activity2.getPrivate();
                activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                Private.setChecked(false);
                        }
                });
                saveButton = activity2.getSaveButton();
                activity.runOnUiThread(new Runnable() {
                        public void run() {
                                saveButton.performClick();
                        }
                });
                getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish
        }
}