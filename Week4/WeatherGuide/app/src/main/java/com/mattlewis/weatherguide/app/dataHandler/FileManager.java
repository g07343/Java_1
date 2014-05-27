//Matthew Lewis
//Term 1405
//Week 3-4 FileManager class

package com.mattlewis.weatherguide.app.dataHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.util.Log;


public class FileManager {

    //create a tag for debugging
    static String TAG = "FILEMANAGMENT - FileManager";

    //this method is responsible for saving data to the device
    static public void SaveData(JSONArray data, Context context) {

        //convert our JSONArray to a string for saving
        String serializedData = data.toString();

        try {
            //save our json out as a generic 'weather' file
            FileOutputStream fos = context.openFileOutput("weather", Context.MODE_PRIVATE);
            try {
                fos.write(serializedData.getBytes());
                fos.close();
                System.out.println("File written successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    //this function reads our data in the event MainActivity can't detect internet or location
    static public JSONArray ReadData(Context context) {
        StringBuilder builder = new StringBuilder();
        JSONArray savedArray;
        try {
            FileInputStream fis = context.openFileInput("weather");
            BufferedInputStream bis = new BufferedInputStream(fis);
            while (bis.available() != 0)
            {
                char c = (char) bis.read();
                builder.append(c);
            }
            bis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        //if we didn't find a file, the stringbuffer will contain 'null'
        if (builder.length() != 4)
        {
            try {
                savedArray = new JSONArray(builder.toString());
                System.out.println("FILE FOUND/SET");
                return savedArray;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
        }
        return null;
    }
}
