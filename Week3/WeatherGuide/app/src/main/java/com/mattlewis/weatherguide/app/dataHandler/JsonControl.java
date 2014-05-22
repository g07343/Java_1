//Matthew Lewis
//Term 1405
//Week 2-4 Json Controller Class

package com.mattlewis.weatherguide.app.dataHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.mattlewis.weatherguide.app.MainActivity;

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

        formattedWeather = new JSONArray();
        String[] days = MainActivity._week;
        String title;
        //since we only want every other result (don't need nighttime weather), add 2 when creating each day for our final JSONArray
        for (int i=0; i < 14; i+=2)
        {
            JSONObject dayObject = new JSONObject();

            //create another int to dynamically assign each to the proper spot in our array (keeps our names and places in sync for us)
            int spot = i/2;

            //do a switch statement to properly assign names for each day (since they need to be in a specific order)
            try {
                switch(i) {
                    case 0:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 2:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 4:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 6:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 8:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 10:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
                        formattedWeather.put(spot, dayObject);
                        break;
                    case 12:
                        title = days[spot];
                        dayObject.put("weekday", title);
                        dayObject.put("weather", allWeather.getJSONObject(i).getString("fcttext"));
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
}
