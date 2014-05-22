//Matthew Lewis
//Term 1405
//Week 2-4 Project MainActivity


package com.mattlewis.weatherguide.app;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

//create another array to contain only the data we want for the main weather display
public static String[] _formattedWeather;

public static JSONArray _weatherJSON;

static String TAG = "NETWORK DATA - MainActivity";

//global public date for use when saving data
public static String date;

//global public string for our day (so when our async task is done we can use it to do setup)
public String today;

public static String _urlString = "http://api.wunderground.com/api/a57ee1fa24cc205a/forecast10day/q/63104.json";

public static Boolean doneLoading = false;




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

        Boolean connectionTest = connectionStatus();
        if (connectionTest)
        {
            //get today's current day of the week as a string
            today = getToday();
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
                doneLoading = true;
                getAllWeather();
                setUp(today);
            } else {
                //begin the process of getting our remote data from API, since no saved data is found
                MainActivity.getData data = new getData();
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
                    Boolean connectionTest = connectionStatus();

                    if (connectionTest)
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
    public void setDay(String day, int position) {
        //set our label to something that makes more sense
        TextView dayLabel = (TextView) findViewById(R.id.selected_label);
        dayLabel.setText("Selected day is:");
        //set our newly selected day
        TextView selectedDay = (TextView) findViewById(R.id.selected_day);
        selectedDay.setText(day);

        //update our weather information to the correct day
        String weather = _allWeather[position];
        TextView selectedWeather = (TextView) findViewById(R.id.weather_holder);
        selectedWeather.setText(weather);
        System.out.println("Weather displayed should be:  " + selectedWeather.getText());
    }

    //this function sets up our allWeather array to contain all of our information for display in the gridview (based on current day)
    public void getAllWeather() {

        //create our day strings manually using yet another for loop/switch statement combo
        String one = "", two = "", three = "", four = "", five = "", six = "", seven = "";
        String uOne = "", uTwo = "", uThree = "", uFour = "", uFive = "", uSix = "", uSeven = "";
        StringBuilder builder = new StringBuilder();

        _weatherJSON =  FileManager.ReadData(context);

        //create a resusable JSONObject to be overwritten for each string
        JSONObject day;
        for (int i=0; i<7; i++)
        {   //absolutely need to manually set the length of each string as this determines the rowheight for the items in grid view.  Different string lengths
            //result in different heights for each grid view item, which causes crazy errors.
            try {
                switch (i) {
                    case 0:
                        //for each, assign/reassign our JSONObject to a single new day object
                        day = _weatherJSON.getJSONObject(i);
                        //use our builder to capture each
                        builder.append("\r\n").append(day.getString("weather")).append("\r\n");
                        uOne = builder.toString();
                        builder.setLength(0);
                        builder.append(day.getString("weekday")).append("\r\n").append("\r\n").append(day.getString("weather")).append("\r\n");
                        if (builder.length() > 50)
                        {
                            builder.setLength(50);
                            builder.append("  ..."+ "\r\n");
                            one = builder.toString();
                            builder.setLength(0);
                        }
                        break;
                    case 1:
                        day = _weatherJSON.getJSONObject(i);
                        builder.append("\r\n").append(day.getString("weather")).append("\r\n");
                        uTwo = builder.toString();
                        builder.setLength(0);
                        builder.append(day.getString("weekday")).append("\r\n").append("\r\n").append(day.getString("weather")).append("\r\n");
                        if (builder.length() > 50)
                        {
                            builder.setLength(50);
                            builder.append("  ..."+ "\r\n");
                            two = builder.toString();
                            builder.setLength(0);
                        }
                        break;
                    case 2:
                        day = _weatherJSON.getJSONObject(i);
                        builder.append("\r\n").append(day.getString("weather")).append("\r\n");
                        uThree = builder.toString();
                        builder.setLength(0);
                        builder.append(day.getString("weekday")).append("\r\n").append("\r\n").append(day.getString("weather")).append("\r\n");
                        if (builder.length() > 50)
                        {
                            builder.setLength(50);
                            builder.append("  ..."+ "\r\n");
                            three = builder.toString();
                            builder.setLength(0);
                        }
                        break;
                    case 3:
                        day = _weatherJSON.getJSONObject(i);
                        builder.append("\r\n").append(day.getString("weather")).append("\r\n");
                        uFour = builder.toString();
                        builder.setLength(0);
                        builder.append(day.getString("weekday")).append("\r\n").append("\r\n").append(day.getString("weather")).append("\r\n");
                        if (builder.length() > 50)
                        {
                            builder.setLength(50);
                            builder.append("  ..."+ "\r\n");
                            four = builder.toString();
                            builder.setLength(0);
                        }
                        break;
                    case 4:
                        day = _weatherJSON.getJSONObject(i);
                        builder.append("\r\n").append(day.getString("weather")).append("\r\n");
                        uFive = builder.toString();
                        builder.setLength(0);
                        builder.append(day.getString("weekday")).append("\r\n").append("\r\n").append(day.getString("weather")).append("\r\n");
                        if (builder.length() > 50)
                        {
                            builder.setLength(50);
                            builder.append("  ..."+ "\r\n");
                            five = builder.toString();
                            builder.setLength(0);
                        }
                        break;
                    case 5:
                        day = _weatherJSON.getJSONObject(i);
                        builder.append("\r\n").append(day.getString("weather")).append("\r\n");
                        uSix = builder.toString();
                        builder.setLength(0);
                        builder.append(day.getString("weekday")).append("\r\n").append("\r\n").append(day.getString("weather")).append("\r\n");
                        if (builder.length() > 50)
                        {
                            builder.setLength(50);
                            builder.append("  ..."+ "\r\n");
                            six = builder.toString();
                            builder.setLength(0);
                        }
                        break;
                    case 6:
                        day = _weatherJSON.getJSONObject(i);
                        builder.append("\r\n").append(day.getString("weather")).append("\r\n");
                        uSeven = builder.toString();
                        builder.setLength(0);
                        builder.append(day.getString("weekday")).append("\r\n").append("\r\n").append(day.getString("weather")).append("\r\n");
                        if (builder.length() > 50)
                        {
                            builder.setLength(50);
                            builder.append("  ..."+ "\r\n");
                            seven = builder.toString();
                            builder.setLength(0);
                        }
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        _allWeather = new String[]{uOne, uTwo, uThree, uFour, uFive, uSix, uSeven};
        _formattedWeather = new String[]{one, two, three, four, five, six, seven};
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //this function is for setting up nearly all of the data in the program.  It used to live in the onCreate method, but it got too messy up there
    public void setUp(String today) {
        if (!doneLoading)
        {
            System.out.println("BOOLEAN WAS FALSE...");


            //find our selected day and set the current day as our default (can be changed later)
            TextView textView = (TextView) findViewById(R.id.selected_day);
            textView.setText(today);


            //now that we know what today is, get it's weather and set to text view

            String todaysWeather = "Loading...";
            TextView weatherView = (TextView) findViewById(R.id.weather_holder);
            weatherView.setText(todaysWeather);


            //create our arrayAdapter for the spinner
            String[] loadingArray = new String[]{"Loading," ,"Loading," ,"Loading," ,"Loading," ,"Loading," ,"Loading," ,"Loading,"};
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, loadingArray);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //create our spinner for users to pick a day
            Spinner spinner = (Spinner) findViewById(R.id.day_selector);
            spinner.setAdapter(spinnerAdapter);


            //create our gridView and required adapter/logic
            ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, loadingArray);
            //get our already created grid view
            GridView gridView = (GridView) findViewById(R.id.weather_grid);
            //set our adapter
            gridView.setAdapter(gridAdapter);

        } else {
            System.out.println("BOOLEAN WAS TRUE...");
            //find our selected day and set the current day as our default (can be changed later)
            TextView textView = (TextView) findViewById(R.id.selected_day);

            textView.setText(today);


            //now that we know what today is, get it's weather and set to text view

            String todaysWeather = _allWeather[0];
            TextView weatherView = (TextView) findViewById(R.id.weather_holder);
            weatherView.setText(todaysWeather);


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
                        setDay(selected, position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //create our gridView and required adapter/logic
            ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _formattedWeather);
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
                        setDay(selected, position);
                        //also this time we need to keep our spinner in sync as well
                        Spinner spinner = (Spinner) findViewById(R.id.day_selector);
                        spinner.setSelection(position, true);
                    }
                }
            });
        }
    }

    public static Boolean connectionStatus() {
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
            StringBuilder responseBuffer = new StringBuilder();

            while((byteRead = buffer.read(contextByte)) != -1)
            {
                response = new String(contextByte, 0, byteRead);
                responseBuffer.append(response);
            }
            response = responseBuffer.toString();
            JsonControl.createJSON(response);
        } catch (IOException e) {
            e.printStackTrace();
            response = "Error retrieving remote data";
            System.out.println(response);
        }
        return response;
    }

    public class getData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String responseString;
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
            getAllWeather();
            doneLoading = true;

            System.out.println("BOOLEAN WAS TRUE...");
            //find our selected day and set the current day as our default (can be changed later)
            TextView textView = (TextView) findViewById(R.id.selected_day);
            textView.setText(today);

            String todaysWeather = _allWeather[0];
            TextView weatherView = (TextView) findViewById(R.id.weather_holder);
            weatherView.setText(todaysWeather);

            //create our spinner for users to pick a day
            Spinner spinner = (Spinner) findViewById(R.id.day_selector);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, _week);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                        setDay(selected, position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, _formattedWeather);
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
                        setDay(selected, position);
                        //also this time we need to keep our spinner in sync as well
                        Spinner spinner = (Spinner) findViewById(R.id.day_selector);
                        spinner.setSelection(position, true);
                    }
                }
            });
        }
    }
}



