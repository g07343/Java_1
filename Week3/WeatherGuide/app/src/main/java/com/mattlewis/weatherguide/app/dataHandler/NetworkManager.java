package com.mattlewis.weatherguide.app.dataHandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class NetworkManager {

    static String TAG = "NETWORK DATA - NETWORKMANAGER";
    //Context context = MainActivity.context;

    public static String _urlString = "http://api.wunderground.com/api/a57ee1fa24cc205a/conditions/q/63104.json";


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


    public static String getResponse(URL url) {
        String response;
        try {
            URLConnection connection = url.openConnection();
            BufferedInputStream buffer = new BufferedInputStream(connection.getInputStream());
            byte[] contextByte = new byte[1024];
            int byteRead;
            StringBuffer responseBuffer = new StringBuffer();

            while((byteRead = buffer.read(contextByte)) != -1)
            {
               response = new String(contextByte, 0, byteRead);
                responseBuffer.append(response);
            }
            response = responseBuffer.toString();
            System.out.println("RESPONSE RECEIVED WAS:  " + response);
        } catch (IOException e) {
            e.printStackTrace();
            response = "Error retrieving remote data";
            System.out.println(response);
        }
        return response;
    }

    public static class getData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String responseString = "";
            try {
                URL url = new URL(_urlString);
                responseString = getResponse(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                responseString = "Error within the getData function!";
                System.out.println(responseString);
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
