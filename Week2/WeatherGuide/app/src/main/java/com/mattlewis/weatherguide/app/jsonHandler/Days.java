//Matthew Lewis
//Term 1405
//Week 2-4 Days enum class


package com.mattlewis.weatherguide.app.jsonHandler;

import java.util.Calendar;

//this is basically just our enum class to provide data to the JsonControl class.  As such, it is part of the "jsonHandler" package until we start utilizing live data later
public class Days {


    public enum days {
        SUNDAY(84, 72, "Sunny", "Sunday"),
        MONDAY(79, 74, "Overcast", "Monday"),
        TUESDAY(89, 80, "Rainy", "Tuesday"),
        WEDNESDAY(94, 86, "Rainy", "Wednesday"),
        THURSDAY(88, 85, "Sunny", "Thursday"),
        FRIDAY(74, 71, "overcast", "Friday"),
        SATURDAY(90, 84, "Sunny", "Saturday");


        public int highTemp;
        public int lowTemp;
        public String forecast;
        public String name;

        days(int high, int low, String forecast, String name) {
            this.highTemp = high;
            this.lowTemp = low;
            this.forecast = forecast;
            this.name = name;
        }

        public final int setHighTemp(){
            return highTemp;
        }

        public int setLowTemp(){
            return lowTemp;
        }

        public String setForecast(){
            return forecast;
        }


    }

}
