//Matthew Lewis
//Term 1405
//Week 2-4 Json Controller Class

package com.mattlewis.weatherguide.app.dataHandler;

import org.json.JSONException;
import org.json.JSONObject;

//this class will be in charge of building a json object and reading it.
public class JsonControl {

    public static JSONObject createJson(){
        //create a base JSON object to hold individual objects for days of the week
        JSONObject weatherDays = new JSONObject();

        //create an inner object to hold each day of the week object
        JSONObject weekDays = new JSONObject();

        for (int i = 1; i <= 7; i++)
        {
            JSONObject dayObject = new JSONObject();
            try {
                switch (i) {
                    case 1:
                        //Sunday enum
                        dayObject.put("high", Days.days.SUNDAY.highTemp);
                        dayObject.put("low", Days.days.SUNDAY.lowTemp);
                        dayObject.put("forecast", Days.days.SUNDAY.forecast);
                        weekDays.put(Days.days.SUNDAY.name, dayObject);
                        break;

                    case 2:
                        //Monday enum
                        dayObject.put("high", Days.days.MONDAY.highTemp);
                        dayObject.put("low", Days.days.MONDAY.lowTemp);
                        dayObject.put("forecast", Days.days.MONDAY.forecast);
                        weekDays.put(Days.days.MONDAY.name, dayObject);
                        break;

                    case 3:
                        //Tuesday enum
                        dayObject.put("high", Days.days.TUESDAY.highTemp);
                        dayObject.put("low", Days.days.TUESDAY.lowTemp);
                        dayObject.put("forecast", Days.days.TUESDAY.forecast);
                        weekDays.put(Days.days.TUESDAY.name, dayObject);
                        break;

                    case 4:
                        //Wednesday enum
                        dayObject.put("high", Days.days.WEDNESDAY.highTemp);
                        dayObject.put("low", Days.days.WEDNESDAY.lowTemp);
                        dayObject.put("forecast", Days.days.WEDNESDAY.forecast);
                        weekDays.put(Days.days.WEDNESDAY.name, dayObject);
                        break;

                    case 5:
                        //Thursday enum
                        dayObject.put("high", Days.days.THURSDAY.highTemp);
                        dayObject.put("low", Days.days.THURSDAY.lowTemp);
                        dayObject.put("forecast", Days.days.THURSDAY.forecast);
                        weekDays.put(Days.days.THURSDAY.name, dayObject);
                        break;

                    case 6:
                        //Friday enum
                        dayObject.put("high", Days.days.FRIDAY.highTemp);
                        dayObject.put("low", Days.days.FRIDAY.lowTemp);
                        dayObject.put("forecast", Days.days.FRIDAY.forecast);
                        weekDays.put(Days.days.FRIDAY.name, dayObject);
                        break;

                    case 7:
                        //Saturday enum
                        dayObject.put("high", Days.days.SATURDAY.highTemp);
                        dayObject.put("low", Days.days.SATURDAY.lowTemp);
                        dayObject.put("forecast", Days.days.SATURDAY.forecast);
                        weekDays.put(Days.days.SATURDAY.name, dayObject);
                        break;

                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            weatherDays.put("WeekDays", weekDays);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherDays;
    }
    //the below function returns single day's weather values
    public static String readJson(String selected, Boolean title){
        //set up our strings to contain our data
        String requested, high, low, forecast;

        //use our above method to give us the completely built JSON object to pull data from
        JSONObject builtJSON = createJson();

        //try/catch, because Java is picky and crazy
        try {
            //pull each bit of data out for whatever day is passed
            high = builtJSON.getJSONObject("WeekDays").getJSONObject(selected).getString("high");
            low = builtJSON.getJSONObject("WeekDays").getJSONObject(selected).getString("low");
            forecast = builtJSON.getJSONObject("WeekDays").getJSONObject(selected).getString("forecast");
            //check if we want the title of the day returned as part of our string
            if(title)
            {
                //put all of our information into one string to return to caller
                requested = selected + "\r\n" + "\r\n" + "High:  " + high + "  Low:  " + low + "\r\n"
                        + "Forecast:  " + forecast + "\r\n";
            } else {
                //put all of our information into one string to return to caller
                requested = "High:  " + high + "  Low:  " + low + "\r\n"
                        + "Forecast:  " + forecast;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            requested = e.toString();
        }

        return requested;
    }
}
