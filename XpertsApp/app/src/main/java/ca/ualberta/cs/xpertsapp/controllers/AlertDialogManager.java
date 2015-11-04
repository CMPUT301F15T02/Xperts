package ca.ualberta.cs.xpertsapp.controllers;

/**
 * Easy way to display simple dialogs to the user
 * @author Hammad Jutt
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import ca.ualberta.cs.xpertsapp.R;

public class AlertDialogManager {
    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * */
    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Setting Dialog Title
        builder.setTitle(title);

        // Setting Dialog Message
        builder.setMessage(message);


        // Setting OK Button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // Showing Alert Message
        builder.create().show();
    }
}