//Matthew Lewis
//Term 1405
//Project 1



package com.mattlewis.colorpicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.view.View.OnClickListener;
import java.util.Timer;

public class MainActivity extends Activity {
    //declare our global variables
    LinearLayout baseLayoutGlobal;
    Boolean discoOn = false;
    int[] colorArray = getResources().getIntArray(R.array.allColors);
    Timer colorTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        //create a linear layout and set that instead
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayoutGlobal = baseLayout;
        baseLayout.setId(getResources().getInteger(R.integer.base_id));
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setBackgroundColor(Color.parseColor("#D6D6D6"));
        //set our content view that we created
        setContentView(baseLayout);

        //create another linear layout and add to first
        LinearLayout labelLayout = new LinearLayout(this);

        //create our edit text field
        final EditText colorField = new EditText(this);

        //create an instruction label
        TextView instructions = new TextView(this);
        instructions.setText(R.string.instructions);

        //create color labels that the user can choose from
        TextView red = new TextView(this);
        TextView blue = new TextView(this);
        TextView yellow = new TextView(this);
        TextView green = new TextView(this);
        TextView purple = new TextView(this);
        TextView orange = new TextView(this);



        //create layout params to apply to each color
        LinearLayout.LayoutParams colorParams = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.color_width), getResources().getInteger(R.integer.color_height));
        colorParams.setMargins(0,80,0,80);

        //set up each of our colors and the unique info that goes along with them
        red.setLayoutParams(colorParams);
        red.setTextColor(Color.WHITE);
        red.setBackgroundColor(Color.RED);
        red.setText(R.string.red);
        red.setGravity(Gravity.CENTER);
        red.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                colorField.setText(R.string.red);
            }
        });


        blue.setLayoutParams(colorParams);
        blue.setTextColor(Color.WHITE);
        blue.setBackgroundColor(Color.BLUE);
        blue.setGravity(Gravity.CENTER);
        blue.setText(R.string.blue);
        blue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                colorField.setText(R.string.blue);
            }
        });

        yellow.setLayoutParams(colorParams);
        yellow.setTextColor(Color.BLACK);
        yellow.setBackgroundColor(Color.YELLOW);
        yellow.setGravity(Gravity.CENTER);
        yellow.setText(R.string.yellow);
        yellow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                colorField.setText(R.string.yellow);
            }
        });


        green.setLayoutParams(colorParams);
        green.setTextColor(Color.BLACK);
        green.setBackgroundColor(Color.GREEN);
        green.setGravity(Gravity.CENTER);
        green.setText(R.string.green);
        green.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                colorField.setText(R.string.green);
            }
        });

        purple.setLayoutParams(colorParams);
        purple.setTextColor(Color.WHITE);
        purple.setBackgroundColor(Color.parseColor("#7B238D"));
        purple.setGravity(Gravity.CENTER);
        purple.setText(R.string.purple);
        purple.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                colorField.setText(R.string.purple);
            }
        });

        orange.setLayoutParams(colorParams);
        orange.setTextColor(Color.BLACK);
        orange.setBackgroundColor(Color.parseColor("#FF6500"));
        orange.setGravity(Gravity.CENTER);
        orange.setText(R.string.orange);
        orange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                colorField.setText(R.string.orange);
            }
        });

        //create an EditText view we can allow the user to type into and populate


        //create our button
        Button pickButton = new Button(this);
        pickButton.setText(R.string.button_title);
        pickButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(colorField.getText().toString());
            }
        });

        //create a 'crazy' button to go through all of the colors when tapped depending on Boolean
        final Button randomButton = new Button(this);
        randomButton.setText(R.string.crazyBtn_title);
        randomButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if we are already crazy or not!
                if (discoOn.equals(true))
                {
                    randomButton.setText(R.string.crazyBtn_title);
                    discoOn = false;
                    baseLayoutGlobal.setBackgroundColor(Color.parseColor("#D6D6D6"));
                } else {
                    randomButton.setText(R.string.stopBtn_title);
                    discoOn = true;
                    //fire the craziness function
                    getNuts();
                }
            }
        });

        LinearLayout colorLayout = new LinearLayout(this);
        
        colorLayout.addView(red);
        colorLayout.addView(blue);
        colorLayout.addView(yellow);
        colorLayout.addView(green);
        colorLayout.addView(purple);
        colorLayout.addView(orange);





        //add everything
        labelLayout.addView(instructions);
        baseLayout.addView(labelLayout);
        baseLayout.addView(colorLayout);
        baseLayout.addView(colorField);
        baseLayout.addView(pickButton);
        baseLayout.addView(randomButton);

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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
    //set our color when the user taps the button according to the string passed
    public void setColor(String color) {
        //get our base layout so we can dynamically shift its colors
        //LinearLayout background = colo;
        if (color.equals("red") || color.equals("Red"))
        {
            //use our global var we set earlier to change color
            baseLayoutGlobal.setBackgroundColor(Color.RED);
        } else if (color.equals("blue") || color.equals("Blue"))
        {
            baseLayoutGlobal.setBackgroundColor(Color.BLUE);
        } else if (color.equals("yellow") || color.equals("Yellow"))
        {
            baseLayoutGlobal.setBackgroundColor(Color.YELLOW);
        } else if (color.equals("green") || color.equals("Green"))
        {
            baseLayoutGlobal.setBackgroundColor(Color.GREEN);
        } else if (color.equals("purple") || color.equals("Purple"))
        {
            baseLayoutGlobal.setBackgroundColor(Color.parseColor("#7B238D"));
        } else if (color.equals("orange") || color.equals("Orange"))
        {
            baseLayoutGlobal.setBackgroundColor(Color.parseColor("#FF6500"));
        } else {
            System.out.println("Color not found: " + color);
        }

    }

    public void getNuts() {
        if(discoOn.equals(true))
        {
            System.out.println("Number of items in colors array is: " + colorArray.length);
           for(int i=0; i < colorArray.length; i++)
           {
               baseLayoutGlobal.setBackgroundColor(colorArray[i]);
               if (i == 5)
               {
                  i = 0;
               }
           }
        }
    }

}

