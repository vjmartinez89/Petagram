package com.vjmartinez.petagram.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

public class MessageUtil {

    /**
     * Show a Toast message
     * @author Victor Martinez
     * @param context, The message context
     * @param message, The message to show
     * @param length, The message duration (0= Toast.LENGTH_SHORT, 1= Toast.LENGTH_LONG)
     */
    public static void showToast(Context context, String message, int length){
        //Asegura el uso de un valor correcto de duracion
        if(length < 0 || length > 1){
            length = Toast.LENGTH_LONG;
        }
        Toast.makeText(context, message, length).show();
    }

    /**
     * Show a Snack Bar message
     * @author Victor Martinez
     * @param v, The message view
     * @param message, The message to show
     * @param length, The message duration (0= Snackbar.LENGTH_LONG, 1= Snackbar.LENGTH_SHORT)
     */
    public static void showSimpleSnackBar(View v, String message, int length){
        //Asegura el uso de un valor correcto de duracion
        if(length != 0 && length != -1){
            length = Snackbar.LENGTH_LONG;
        }
        Snackbar.make(v, message, length).show();
    }


    /**
     * Show a Snack Bar message
     * @author Victor Martinez
     * @param v, The message view
     * @param message, The message to show
     * @param length, The message duration (0= Snackbar.LENGTH_LONG, 1= Snackbar.LENGTH_SHORT)
     * @return a SnackBar object
     */
    public static Snackbar createSnackBar(View v, String message, int length){
        //Asegura el uso de un valor correcto de duracion
        if(length != 0 && length != -1){
            length = Snackbar.LENGTH_LONG;
        }
        return Snackbar.make(v, message, length);
    }

    /**
     * Build and show an alert dialog
     * @author Victor Martinez
     * @param context The alert dialog context
     * @param title The alert title
     * @param message The alert message
     */
    public  static void showAlertDialog(Context context, String title, String message) {
        showAlertDialog(context, title, message, true, false, null, null);
    }

    /**
     * Build and show an alert dialog
     * @author Victor Martinez
     * @param context The alert dialog context
     * @param title The alert title
     * @param message The alert message
     * @param cancelable is Cancelable?
     * @param isConfirmation set Confirmation button?
     * @param yesButtonText Yes button text (Mandatory when isConfirmation = true)
     * @param noButtonText No button text (Mandatory when isConfirmation = true)
     */
    public  static void showAlertDialog(Context context, String title, String message, @Nullable  boolean cancelable,
                                        @Nullable  boolean isConfirmation,  @Nullable String yesButtonText,  @Nullable String noButtonText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message)
                .setCancelable(cancelable);
        if(cancelable && !isConfirmation){
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
        if(isConfirmation && !StringUtils.isEmpty(yesButtonText) && !StringUtils.isEmpty(noButtonText)) {
            builder.setPositiveButton(yesButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            })
            .setNegativeButton(noButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        AlertDialog alert = builder.create();
        alert.show();
    }
}
