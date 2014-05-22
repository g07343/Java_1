//Matthew Lewis
//Term 1405
//Week 3-4 FileManager class

package com.mattlewis.weatherguide.app.dataHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class FileManager {

    //this method is responsible for saving data to the device
    static public void SaveData(JSONArray data, Context context) {

        //convert our JSONArray to a string for saving
        String serializedData = data.toString();

        try {
            FileOutputStream fos = context.openFileOutput("weather", Context.MODE_PRIVATE);
            try {
                fos.write(serializedData.getBytes());
                fos.close();
                System.out.println("File written successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error writing data");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error in outer try/catch");
        }
    }

    static public JSONArray ReadData(Context context) {
        StringBuffer buffer = new StringBuffer();
        JSONArray savedArray;
        try {
            FileInputStream fis = context.openFileInput("weather");
            BufferedInputStream bis = new BufferedInputStream(fis);
            while (bis.available() != 0)
            {
                char c = (char) bis.read();
                buffer.append(c);
            }
            bis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if we didn't find a file, the stringbuffer will contain 'null'
        if (buffer.length() != 4)
        {
            try {
                savedArray = new JSONArray(buffer.toString());
                System.out.println("FILE FOUND/SET");
                return savedArray;
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("FILE NOT FOUND");
            }
        }
        return null;
    }
}
