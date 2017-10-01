/*
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CounterDisplay extends AppCompatActivity implements View.OnClickListener{

    private Counters counter_to_display;
    private int position;
    EditText editCurrentValue = (EditText) findViewById(R.id.show_current_vaule);
    EditText editInitialValue = (EditText) findViewById(R.id.show_initial_value);
    EditText editName = (EditText) findViewById(R.id.show_name);
    EditText editComment = (EditText) findViewById(R.id.show_comment);
    TextView textDate = (TextView) findViewById(R.id.show_date);
    // still require a textview to represent DATE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_display);

        // initializing buttons
        ImageButton button_increase = (ImageButton) findViewById(R.id.button_increase);
        ImageButton button_decrease = (ImageButton) findViewById(R.id.button_decrease);
        ImageButton button_delete = (ImageButton) findViewById(R.id.button_delete);
        ImageButton button_reset = (ImageButton) findViewById(R.id.button_reset);
        button_decrease.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        button_increase.setOnClickListener(this);
        button_reset.setOnClickListener(this);
        Log.d("Display","Initialized buttons");

        // getting data from intent
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        counter_to_display.setInitialValue(intent.getIntExtra("initial",0));
        counter_to_display.setCurrentValue(intent.getIntExtra("current",0));
        counter_to_display.setName(intent.getStringExtra("name"));
        counter_to_display.setComment(intent.getStringExtra("comment"));
        String dateToDisplay = intent.getStringExtra("date");

        // Initializing the data text view
        textDate.setText(dateToDisplay);
        Log.d("Display","Intent data got");
        Log.d("Display",new Integer(counter_to_display.getCurrentValue()).toString());
        Log.d("Display",counter_to_display.getName());
        position = intent.getExtra("position",0);
        counter_to_display = intent.getExtra().getSerializableExtra("counter");

        // Initializing editText
        editCurrentValue.setText(counter_to_display.getCurrentValue());
        editName.setText(counter_to_display.getName());
        editInitialValue.setText(counter_to_display.getInitialValue());
        editComment.setText(counter_to_display.getComment());
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.button_increase){
            // if Increase button is clicked
            updateDate();
            counter_to_display.increaseCounter();
            editCurrentValue.setText(counter_to_display.getCurrentValue);
            // send a boardcast to the main activity to notify the chage
            // and save the file.
            Intent intent = new Intent("com.example.counter.DATA_CHANGED");
            intent.putExtra("position", position);
            intent.putExtra("function","increase");
            sendBroadcast(intent);
        }else if(id == R.id.button_decrease){
            // if Decrease button is clicked
            updateDate();
            counter_to_display.decreaseCounter();
            editCurrentValue.setText(counter_to_display.getCurrentValue);
            // send a boardcast to the main activity to notify the chage
            // and save the file.
            Intent intent = new Intent("com.example.counter.DATA_CHANGED");
            intent.putExtra("position", position);
            intent.putExtra("function","decrease");
            sendBroadcast(intent);
        }else if(id == R.id.button_delete){
            // if Delete button is clicked
            Intent intent = new Intent("com.example.counter.DATA_CHANGED");
            intent.putExtra("function","delete");
            intent.putExtra("position",position);
            sendBroadcast(intent);
            finish();

        }else if(id == R.id.button_reset){
            // if Reset button is clicked
            updateDate();
            counter_to_display.setCurrentValue(counter_to_display.getInitialValue());
            editCurrentValue.setText(counter_to_display.getCurrentValue);
            Intent intent = new Intent("com.example.counter.DATA_CHANGED");
            intent.putExtra("position", position);
            sendBroadcast(intent);
        }

    }

    public void updateDate(){
        counter_to_display.setDate(new Date());
        textDate.setText(counter_to_display.getStringDate());
    }
}
