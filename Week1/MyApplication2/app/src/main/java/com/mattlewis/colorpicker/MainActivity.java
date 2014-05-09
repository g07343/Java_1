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

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        //create a linear layout and set that instead
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setBackgroundColor(Color.parseColor("#D6D6D6"));
        //set our content view that we created
        setContentView(baseLayout);

        //create another linear layout and add to first
        LinearLayout labelLayout = new LinearLayout(this);


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
                System.out.println("USER TAPPED RED!!!");
            }
        });


        blue.setLayoutParams(colorParams);
        blue.setTextColor(Color.WHITE);
        blue.setBackgroundColor(Color.BLUE);
        blue.setGravity(Gravity.CENTER);
        blue.setText(R.string.blue);

        yellow.setLayoutParams(colorParams);
        yellow.setTextColor(Color.BLACK);
        yellow.setBackgroundColor(Color.YELLOW);
        yellow.setGravity(Gravity.CENTER);
        yellow.setText(R.string.yellow);


        green.setLayoutParams(colorParams);
        green.setTextColor(Color.BLACK);
        green.setBackgroundColor(Color.GREEN);
        green.setGravity(Gravity.CENTER);
        green.setText(R.string.green);

        purple.setLayoutParams(colorParams);
        purple.setTextColor(Color.WHITE);
        purple.setBackgroundColor(Color.parseColor("#7B238D"));
        purple.setGravity(Gravity.CENTER);
        purple.setText(R.string.purple);

        orange.setLayoutParams(colorParams);
        orange.setTextColor(Color.BLACK);
        orange.setBackgroundColor(Color.parseColor("#FF6500"));
        orange.setGravity(Gravity.CENTER);
        orange.setText(R.string.orange);

        //create an EditText view we can allow the user to type into and populate
        final EditText colorField = new EditText(this);

        //create our button
        Button pickButton = new Button(this);
        pickButton.setText(R.string.button_title);
        pickButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor(colorField.getText().toString());
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
    //set our color when the user taps the button according to the string passed
    public void setColor(String color) {
        System.out.println(color + " was chosen!");

    }

}

