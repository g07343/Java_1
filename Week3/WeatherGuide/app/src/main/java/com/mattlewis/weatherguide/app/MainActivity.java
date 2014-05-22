//Matthew Lewis
//Term 1405
//Week 2-4 Project MainActivity


package com.mattlewis.weatherguide.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.content.Context;

import com.mattlewis.weatherguide.app.dataHandler.FileManager;
import com.mattlewis.weatherguide.app.dataHandler.JsonControl;
import com.mattlewis.weatherguide.app.dataHandler.NetworkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity {

public static Context context;

//set up a public string to communicate which day of the week is current
public static String _current;

//set up a public array to hold the days in a specific order (for correct viewing in widgets)
//**Want to create this manually using individual strings in strings.xml so that we can cater it to today's day of the week (not predefined array)
public static String[] _week;

//create an array to contain strings with each day of the week's title as well as their weather conditions for gridview
public static String[] _allWeather;

public static JSONArray _weatherJSON;

//global public date for use when saving data
public static String date;

    //we can use this to determine the day of the week, which is displayed at the top of the interface
    public String getToday() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        //need to get current date using formatter so we can ensure we aren't using old data (in case a user uses the app only on tuesdays or something)
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM-dd-yyyy");

        //set our global date to reference against save data, and to supply a date to save as well
        date = dateFormatter.format(calendar.getTime());


        //depending on the day int value, set the current day String
        switch (currentDay){
            case 1:
                _current = getString(R.string.sunday_text);
                break;
            case 2:
                _current = getString(R.string.monday_text);
                break;
            case 3:
                _current = getString(R.string.tuesday_text);
                break;
            case 4:
                _current = getString(R.string.wednesday_text);
                break;
            case 5:
                _current = getString(R.string.thursday_text);
                break;
            case 6:
                _current = getString(R.string.friday_text);
                break;
            case 7:
                _current = getString(R.string.saturday_text);
                break;
            default:
                return "Day not found!";
        }


        return _current;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        //set our default content view
        setContentView(R.layout.activity_main);

        //set our refresh button to be hidden by default
        final Button refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setVisibility(View.GONE);

        Boolean connectionTest = NetworkManager.connectionStatus(context);
        if (connectionTest == true)
        {
            //get today's current day of the week as a string
            String today = getToday();
            createWeek(_current);



            //create our JSONArray from saved data and check if it's null (which tells us if there IS any saved data)
            _weatherJSON =  FileManager.ReadData(context);
            if (_weatherJSON != null)
            {
                try {
                    //get the saved day from the device to compare
                    String savedDate = (String) _weatherJSON.get(7);
                    if (savedDate.equals(date))
                    {
                        System.out.println("Today's date is the same as the saved one!");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //begin the process of getting our remote data from API, since no saved data is found
                NetworkManager.getData data = new NetworkManager.getData();
                data.execute();
            }
            setUp(today);

        } else {
            final TextView weatherView = (TextView) findViewById(R.id.weather_holder);
            weatherView.setTextColor(Color.RED);
            weatherView.setText("You don't appear to have an active internet connection.  Please connect to the internet to continue.");
            refreshButton.setVisibility(View.VISIBLE);

            //set onClick to basically 'recheck' if we have internet
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean connectionTest = NetworkManager.connectionStatus(context);

                    if (connectionTest == true)
                    {

                        refreshButton.setVisibility(View.GONE);
                        weatherView.setTextColor(Color.BLACK);
                        //begin the process of getting our remote data from API
                        //begin the process of getting our remote data from API
                        NetworkManager.getData data = new NetworkManager.getData();
                        data.execute();

                        //get today's current day of the week as a string
                        String today = getToday();
                        //create week array depending on day of the week
                        createWeek(_current);
                        //run our full set up function passing the current day
                        setUp(today);
                    }
                }
            });
        }






    }


    //this method simply creates our dynamic 7 day week array
    private void createWeek(String today) {
        //since we can't use a switch statement with a string (not Java 7+), use if/else (sigh..)

        if (today.equals("Monday"))
        {
            _week = new String[]{getString(R.string.monday_text), getString(R.string.tuesday_text), getString(R.string.wednesday_text), getString(R.string.thursday_text), getString(R.string.friday_text), getString(R.string.saturday_text), getString(R.string.sunday_text)};
        } else if(today.equals("Tuesday"))
        {
            _week = new String[]{getString(R.string.tuesday_text), getString(R.string.wednesday_text), getString(R.string.thursday_text), getString(R.string.friday_text), getString(R.string.saturday_text), getString(R.string.sunday_text), getString(R.string.monday_text)};
        } else if (today.equals("Wednesday"))
        {
            _week = new String[]{getString(R.string.wednesday_text), getString(R.string.thursday_text), getString(R.string.friday_text), getString(R.string.saturday_text), getString(R.string.sunday_text), getString(R.string.monday_text), getString(R.string.tuesday_text)};
        } else if (today.equals("Thursday"))
        {
            _week = new String[]{getString(R.string.thursday_text), getString(R.string.friday_text), getString(R.string.saturday_text), getString(R.string.sunday_text), getString(R.string.monday_text), getString(R.string.tuesday_text), getString(R.string.wednesday_text)};
        } else if (today.equals("Friday"))
        {
            _week = new String[]{getString(R.string.friday_text), getString(R.string.saturday_text), getString(R.string.sunday_text), getString(R.string.monday_text), getString(R.string.tuesday_text), getString(R.string.wednesday_text), getString(R.string.thursday_text)};
        } else if (today.equals("Saturday"))
        {
            _week = new String[]{getString(R.string.saturday_text), getString(R.string.sunday_text), getString(R.string.monday_text), getString(R.string.tuesday_text), getString(R.string.wednesday_text), getString(R.string.thursday_text), getString(R.string.friday_text)};
        } else if (today.equals("Sunday"))
        {
            _week = new String[]{getString(R.string.sunday_text), getString(R.string.monday_text), getString(R.string.tuesday_text), getString(R.string.wednesday_text), getString(R.string.thursday_text), getString(R.string.friday_text), getString(R.string.saturday_text)};
        }
    }

    //this function gets called whenever the user selects a new day from the spinner.  Updates the UI accordingly
    public void setDay(String day) {
        //set our label to something that makes more sense
        TextView dayLabel = (TextView) findViewById(R.id.selected_label);
        dayLabel.setText("Selected day is:");

        //set our newly selected day
        TextView selectedDay = (TextView) findViewById(R.id.selected_day);
        selectedDay.setText(day);

        //update our weather information to the correct day
        String weather = JsonControl.readJson(day, false);
        TextView selectedWeather = (TextView) findViewById(R.id.weather_holder);
        selectedWeather.setText(weather);
    }

    //this function sets up our allWeather array to contain all of our information for display in the gridview (based on current day)
    public void getAllWeather() {

        int currentPosition = java.util.Arrays.asList(_week).indexOf(_current);

        //manually create our allWeather array the hard way since you cannot 'add' items to an array in java
        _allWeather = new String[]{JsonControl.readJson(_week[currentPosition], true), JsonControl.readJson(_week[currentPosition +1], true), JsonControl.readJson(_week[currentPosition +2], true), JsonControl.readJson(_week[currentPosition +3], true), JsonControl.readJson(_week[currentPosition +4], true), JsonControl.readJson(_week[currentPosition +5], true), JsonControl.readJson(_week[currentPosition +6], true),};

        //create our day strings manually using yet another for loop/switch statement combo
        String one, two, three, four, five, six, seven;

        //create a resusable JSONObject to be overwritten for each string
        JSONObject day;
        for (int i=0; i<7; i++)
        {
            try {
                switch (i) {
                    case 0:
                        day = _weatherJSON.getJSONObject(i);
                        one = day.getString("weekday") + "\r\n" + "\r\n" + day.getString("weather");
                        break;
                    case 1:
                        day = _weatherJSON.getJSONObject(i);
                        break;
                    case 2:
                        day = _weatherJSON.getJSONObject(i);
                        break;
                    case 3:
                        day = _weatherJSON.getJSONObject(i);
                        break;
                    case 4:
                        day = _weatherJSON.getJSONObject(i);
                        break;
                    case 5:
                        day = _weatherJSON.getJSONObject(i);
                        break;
                    case 6:
                        day = _weatherJSON.getJSONObject(i);
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //this function is for setting up nearly all of the data in the program.  It used to live in the onCreate method, but it got too messy up there
    public void setUp(String today) {
        //create our JSON object now to pull from
        JsonControl.createJson();
        //find our selected day and set the current day as our default (can be changed later)
        TextView textView = (TextView) findViewById(R.id.selected_day);
        textView.setText(today);


        //now that we know what today is, get it's weather and set to text view
        String todaysWeather = JsonControl.readJson(today, false);
        TextView weatherView = (TextView) findViewById(R.id.weather_holder);
        weatherView.setText(todaysWeather);
        getAllWeather();

        //create our arrayAdapter for the spinner

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _week);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //create our spinner for users to pick a day
        Spinner spinner = (Spinner) findViewById(R.id.day_selector);
        spinner.setAdapter(spinnerAdapter);

        //create our onItemSelected method
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get the selected day from our array
                String selected = _week[position];
                //only update the UI if the user picks a different day
                if (!(selected.equals(_current))) {
                    _current = selected;
                    setDay(selected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //create our gridView and required adapter/logic
        ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _allWeather);
        //get our already created grid view
        GridView gridView = (GridView) findViewById(R.id.weather_grid);
        //set our adapter
        gridView.setAdapter(gridAdapter);

        //create onClick method
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = _week[position];
                System.out.println("Selected day was:  " + selected);
                if (!(selected.equals(_current))) {
                    _current = selected;
                    setDay(selected);
                    //also this time we need to keep our spinner in sync as well
                    Spinner spinner = (Spinner) findViewById(R.id.day_selector);
                    spinner.setSelection(position, true);
                }
            }
        });
    }
}



