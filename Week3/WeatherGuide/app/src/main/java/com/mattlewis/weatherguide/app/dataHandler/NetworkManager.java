package com.mattlewis.weatherguide.app.dataHandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mattlewis.weatherguide.app.MainActivity;

/**
 * Created by Matt on 5/18/14.
 */
public class NetworkManager {

    static String TAG = "NETWORK DATA - NETWORKMANAGER";
    //Context context = MainActivity.context;

    public static String urlString = "http://api.wunderground.com/api/a57ee1fa24cc205a/conditions/q/63104.json";


    public static Boolean connectionStatus(Context context) {
        //create initial boolean to set true/false depending on network conditions
        Boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        //check to make sure we have a valid object
        if (networkInfo != null)
        {   //check the result to make sure it is actually connected and set boolean to true if so
            if (networkInfo.isConnected())
            {
                Log.i(TAG, "Connection type:  " + networkInfo.getTypeName());
                connected = true;
            }
        }
        return connected;
    }
}
