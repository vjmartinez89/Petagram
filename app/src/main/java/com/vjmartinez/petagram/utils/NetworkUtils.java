package com.vjmartinez.petagram.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.net.ConnectivityManagerCompat;

public class NetworkUtils {


    public static boolean deviceIsConnected(Context context){
        ConnectivityManager connect = (ConnectivityManager)ContextCompat.getSystemService(context, ConnectivityManager.class);

        // ARE WE CONNECTED TO THE NET
        if ( connect.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connect.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
            return true;
        }
        return false;
    }
}
