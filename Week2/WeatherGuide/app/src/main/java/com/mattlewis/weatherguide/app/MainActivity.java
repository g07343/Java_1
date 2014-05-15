//Matthew Lewis
//Term 1405
//Week 2-4 Project MainActivity


package com.mattlewis.weatherguide.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.mattlewis.weatherguide.app.jsonHandler.JsonControl;
import java.util.Calendar;

public class MainActivity extends Activity {

    //create our enum days of the week, and for now assign static parameters to them
    //these will need to be dynamic later when pulling in remote JSON data
   public enum days {
        SUNDAY(84, 72, "Sunny"),
        MONDAY(79, 74, "Overcast"),
        TUESDAY(89, 80, "Rainy"),
        WEDNESDAY(94, 86, "Rainy"),
        THURSDAY(88, 85, "Sunny"),
        FRIDAY(74, 71, "overcast"),
        SATURDAY(90, 84, "Sunny");

        private final int highTemp;
        private final int lowTemp;
        private final String forecast;

        days(int high, int low, String forecast) {
            this.highTemp = high;
            this.lowTemp = low;
            this.forecast = forecast;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String today = days.getToday();
        JsonControl.createJson();
        System.out.println("Today is:  " + today);
        TextView textView = (TextView) findViewById(R.id.default_textview);
        textView.setText(today);
        String saturdayWeather = JsonControl.readJson("Saturday");
        System.out.println("Saturday's weather is:  " + saturdayWeather);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
