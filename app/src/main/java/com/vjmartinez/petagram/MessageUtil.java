package com.vjmartinez.petagram;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class MessageUtil {

    /**
     * Show a Toast message
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
}
