package ca.ualberta.cs.xpertsapp.InventoryTests;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import ca.ualberta.cs.xpertsapp.controllers.AddServiceController;
import ca.ualberta.cs.xpertsapp.views.AddServiceActivity;
import ca.ualberta.cs.xpertsapp.views.BrowseServicesActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;
import ca.ualberta.cs.xpertsapp.views.ViewProfileActivity;

public class AddInventoryServiceTest extends ActivityInstrumentationTestCase2 {
        private AddServiceController asc = new AddServiceController();
        private Spinner Categories;
        private EditText Title;
        private EditText Description;
        private CheckBox Private;
        private Button saveButton;
        private Button profileButton;
        private Button addServiceButton;
        AddServiceActivity activity2;
        Instrumentation instrumentation;
        Instrumentation.ActivityMonitor monitor;
        Instrumentation.ActivityMonitor monitor2;
        private static final int TIME_OUT = 5000;

        public AddInventoryServiceTest() {
                super(MainActivity.class);

        }

        @Override
        protected void setUp() throws Exception {
                super.setUp();
                instrumentation = getInstrumentation();
                monitor = instrumentation.addMonitor(ViewProfileActivity.class.getName(), null, false);
                monitor2 = instrumentation.addMonitor(AddServiceActivity.class.getName(), null, false);
        }

        @Override
        protected void tearDown() throws Exception {
                activity2.finish();
                getInstrumentation().removeMonitor(monitor);
                super.tearDown();
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
                ViewProfileActivity activity1 = (ViewProfileActivity) instrumentation.waitForMonitorWithTimeout(monitor, TIME_OUT);
                assertNotNull(activity1);
                addServiceButton = activity1.getAddService();
                activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                addServiceButton.performClick();
                        }
                });
                getInstrumentation().waitForIdleSync(); // makes sure that all the threads finish

                activity2 = (AddServiceActivity) instrumentation.waitForMonitorWithTimeout(monitor2, TIME_OUT);
                assertNotNull(activity2);

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