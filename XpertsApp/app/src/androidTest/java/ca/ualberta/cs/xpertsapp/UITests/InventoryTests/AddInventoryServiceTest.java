package ca.ualberta.cs.xpertsapp.UITests.InventoryTests;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.xpertsapp.MyApplication;
import ca.ualberta.cs.xpertsapp.UnitTests.TestCase;
import ca.ualberta.cs.xpertsapp.controllers.AddServiceController;
import ca.ualberta.cs.xpertsapp.model.Constants;
import ca.ualberta.cs.xpertsapp.model.Service;
import ca.ualberta.cs.xpertsapp.model.ServiceManager;
import ca.ualberta.cs.xpertsapp.model.UserManager;
import ca.ualberta.cs.xpertsapp.views.AddServiceActivity;
import ca.ualberta.cs.xpertsapp.views.MainActivity;
import ca.ualberta.cs.xpertsapp.views.ViewProfileActivity;

public class AddInventoryServiceTest extends TestCase {
        private AddServiceController asc = new AddServiceController();
        private Spinner Categories;
        private EditText Title;
        private EditText Description;
        private CheckBox Private;
        private Button saveButton;
        private Button profileButton;
        private Button addServiceButton;
        private Button imageButton;
        private List<Bitmap> pictures;
        AddServiceActivity activity2;
        private Intent intent;
        Instrumentation instrumentation;
        Instrumentation.ActivityMonitor monitor;
        Instrumentation.ActivityMonitor monitor2;
        private static final int TIME_OUT = 5000;

        public AddInventoryServiceTest() {
                super();

        }

        @Override
        protected void setUp2() {
                super.setUp2();
                instrumentation = getInstrumentation();
                monitor = instrumentation.addMonitor(ViewProfileActivity.class.getName(), null, false);
                monitor2 = instrumentation.addMonitor(AddServiceActivity.class.getName(), null, false);
        }

        @Override
        protected void tearDown2() {
                activity2.finish();
                getInstrumentation().removeMonitor(monitor);
                super.tearDown2();
        }

        /**
         * UC01.03.01
         */


        public void testAddService(){
                setUp2();

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

                imageButton = activity2.getPhotoButton();
                pictures = activity2.getPictures();
                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                Bitmap bmp = Bitmap.createBitmap(100, 100, conf);
                pictures.add(0,bmp);
                /*activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                imageButton.performClick();

                        }
                });
*/
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
                Boolean check = Boolean.FALSE;
                List<Service> services = UserManager.sharedManager().getUser(Constants.testEmail).getServices();
                for(int i = 0; i < services.size(); i++){
                        if (services.get(i).getName() == "Test Service 1"){
                                check = Boolean.TRUE;
                        }
                }
                assertEquals(check,Boolean.TRUE);
                tearDown2();
        }
}