//Matthew Lewis
//Term 1405
//Week 2-4 Json Controller Class

package com.mattlewis.weatherguide.app.dataHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import com.mattlewis.weatherguide.app.MainActivity;

import java.io.InputStream;

//this class will be in charge of building a json object and reading it.
public class JsonControl {

    //create our debugging tag
    static String TAG = "JSONMANAGEMENT - JsonControl";

    //create a JSON Array object to be set to NetworkManager's received data
    public static JSONArray weatherArray;

    //create another JSON Array to eventually hold the correctly formatted data for saving and retrieving
    public static JSONArray formattedWeather;


    //this function gets called when the async task is finished from MainActivity class, which passes the returned string
    public static void createJSON(String returned) {
        try {
            //create our json object from the passed string
            JSONObject days = new JSONObject(returned);

            //get JSON object txt_forecast
            JSONObject forecast = days.getJSONObject("forecast");

            //get next level object
            JSONObject txtForecast = forecast.getJSONObject("txt_forecast");

            //capture the JSONArray object that contains the data we want
             weatherArray = txtForecast.getJSONArray("forecastday");

            //call a function to take this JSONArray and separate out the parts we need
            buildDays(weatherArray);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    public static void buildDays(JSONArray allWeather){

        System.out.println("Returned JSON was:  " + allWeather);
        formattedWeather = new JSONArray();
        String[] days = MainActivity._week;
        String title;
        String imageDataString;
        byte[] byteArray;
        //since we only want every other result (don't need nighttime weather), add 2 when creating each day for our final JSONArray
        for (int i=0; i < 14; i+=2)
        {
            JSONObject dayObject = new JSONObject();
            String urlString;
            //create another int to dynamically assign each to the proper spot in our array (keeps our names and places in sync for us)
            int spot = i/2;

            //do a switch statement to properly assign names for each day (since they need to be in a specific order)
            try {
                switch(i) {
                    case 0:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        //get the returned json object url and pass to the 'buildBitmap' function to get our image for storing
                        urlString = allWeather.getJSONObject(i).getString("icon_url");
                        byteArray = buildImage(urlString);
                        imageDataString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        dayObject.put("image", imageDataString);
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 2:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        //get the returned json object url and pass to the 'buildBitmap' function to get our image for storing
                        urlString = allWeather.getJSONObject(i).getString("icon_url");
                        byteArray = buildImage(urlString);
                        imageDataString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        dayObject.put("image", imageDataString);
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 4:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        //get the returned json object url and pass to the 'buildBitmap' function to get our image for storing
                        urlString = allWeather.getJSONObject(i).getString("icon_url");
                        byteArray = buildImage(urlString);
                        imageDataString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        dayObject.put("image", imageDataString);
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 6:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        //get the returned json object url and pass to the 'buildBitmap' function to get our image for storing
                        urlString = allWeather.getJSONObject(i).getString("icon_url");
                        byteArray = buildImage(urlString);
                        imageDataString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        dayObject.put("image", imageDataString);
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 8:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        //get the returned json object url and pass to the 'buildBitmap' function to get our image for storing
                        urlString = allWeather.getJSONObject(i).getString("icon_url");
                        byteArray = buildImage(urlString);
                        imageDataString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        dayObject.put("image", imageDataString);
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 10:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        //get the returned json object url and pass to the 'buildBitmap' function to get our image for storing
                        urlString = allWeather.getJSONObject(i).getString("icon_url");
                        byteArray = buildImage(urlString);
                        imageDataString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        dayObject.put("image", imageDataString);
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 12:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        //get the returned json object url and pass to the 'buildBitmap' function to get our image for storing
                        urlString = allWeather.getJSONObject(i).getString("icon_url");
                        byteArray = buildImage(urlString);
                        imageDataString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        dayObject.put("image", imageDataString);
                        formattedWeather.put(spot, dayObject);
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
        }
        //we also need to put in the current date to make sure we aren't using old data everytime the app loads
        try {
            formattedWeather.put(7, MainActivity.date);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        //now that we have our finalized data for display, pass it to the FileManager class for saving
        FileManager.SaveData(formattedWeather, MainActivity.context);
    }

    //this function converts the url that is returned to a bitmap that we store for displaying to the user
    private static byte[] buildImage(String urlString) {
        URL imageLink;
        Bitmap bitmap;
        byte[] byteArray = new byte[0];
        try {
            try {
                imageLink = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                imageLink = null;
            }
            assert imageLink != null;
            bitmap = BitmapFactory.decodeStream((InputStream) imageLink.getContent());
            if (bitmap == null)
            {
                System.out.println("NO pic!");
                return null;
            } else {
                System.out.println("Valid pic!");

                //now that we know we have a valid bitmap image, convert to byteArray for storage
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
                return byteArray;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return byteArray;
        }
    }
}
