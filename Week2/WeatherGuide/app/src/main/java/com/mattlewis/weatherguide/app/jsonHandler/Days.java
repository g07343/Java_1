//Matthew Lewis
//Term 1405
//Week 2-4 Days enum class


package com.mattlewis.weatherguide.app.jsonHandler;

import java.util.Calendar;

/**
 * Created by Matt on 5/14/14.
 */
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


        //we can use this to determine the day of the week, which is displayed at the top of the interface
        public static String getToday() {
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
            String current;
            switch (currentDay){
                case 1:
                    current = "Sunday";
                    break;
                case 2:
                    current = "Monday";
                    break;
                case 3:
                    current = "Tuesday";
                    break;
                case 4:
                    current = "Wednesday";
                    break;
                case 5:
                    current = "Thursday";
                    break;
                case 6:
                    current = "Friday";
                    break;
                case 7:
                    current = "Saturday";
                    break;
                default:
                    return "Day not found!";
            }
            return current;
        }

    }

    public Days(int day) {
        switch (day){
            case 1:
               int high =  days.SUNDAY.setHighTemp();
                days.SUNDAY.setLowTemp();
                days.SUNDAY.setForecast();
                break;
            case 2:
//                current = "Monday";
                break;
            case 3:
//                current = "Tuesday";
                break;
            case 4:
//                current = "Wednesday";
                break;
            case 5:
//                current = "Thursday";
                break;
            case 6:
//                current = "Friday";
                break;
            case 7:
//                current = "Saturday";
                break;
            default:
//                return "Day not found!";
        }
    }

}
