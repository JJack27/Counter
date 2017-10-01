/*
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;

public class CounterDisplay extends AppCompatActivity implements View.OnClickListener{

    private Counters counter_to_display = new Counters();
    private int position;
    EditText editCurrentValue;
    EditText editInitialValue;
    EditText editName;
    EditText editComment;
    TextView textDate;
    private boolean finish_edit_current = true;
    private boolean finish_edit_name= true;
    private boolean finish_edit_comment= true;
    private boolean finish_edit_initial= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Display","Start CounterDisplay activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_display);
        editCurrentValue = (EditText) findViewById(R.id.show_current_value);
        editInitialValue = (EditText) findViewById(R.id.show_initial_value);
        editName = (EditText) findViewById(R.id.show_name);
        editComment = (EditText) findViewById(R.id.show_comment);
        textDate = (TextView) findViewById(R.id.show_date);

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

        // Initializing editText
        editCurrentValue.setText(new Integer(counter_to_display.getCurrentValue()).toString());
        editName.setText(counter_to_display.getName());
        editInitialValue.setText(new Integer(counter_to_display.getInitialValue()).toString());
        editComment.setText(counter_to_display.getComment());
        Log.d("Display", "Data printed");

        // let user to edit current value
        editCurrentValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    finish_edit_current = false;
                }else if(!finish_edit_current){
                    if(editCurrentValue.getText().length() == 0){
                        // reset current value to initial value
                        resetCurrentValue(counter_to_display.getInitialValue());
                        finish_edit_current = true;
                    }else if(Integer.parseInt(editCurrentValue.getText().toString()) < 0){
                        resetCurrentValue(0);
                        finish_edit_current = true;
                    }else if(Integer.parseInt(editCurrentValue.getText().toString()) >99999){
                        resetCurrentValue(99999);
                        finish_edit_current = true;
                    }else {
                        counter_to_display.setCurrentValue(Integer.
                                valueOf(editCurrentValue.getText().toString()));
                        Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
                        intent.putExtra("position", position);
                        intent.putExtra("function", 5);
                        intent.putExtra("current_value", counter_to_display.getCurrentValue());
                        sendBroadcast(intent);
                        updateDate();
                        finish_edit_current = true;
                    }
                }
            }
        });


        // let user to change the initial value
        editInitialValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    finish_edit_initial = false;
                }else if(!finish_edit_initial){
                    // sent intent to the main activity to change the vaule
                    if(editInitialValue.getText().length() == 0){
                        // reset current value to initial value
                        resetInitialValue(counter_to_display.getInitialValue());
                        finish_edit_initial = true;
                    }else if(Integer.parseInt(editInitialValue.getText().toString()) < 0){
                        resetInitialValue(0);
                        finish_edit_initial = true;
                    }else if(Integer.parseInt(editInitialValue.getText().toString()) >99999){
                        resetInitialValue(99999);
                        finish_edit_initial = true;
                    }else {
                        counter_to_display.setInitialValue(Integer.
                                valueOf(editInitialValue.getText().toString()));
                        Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
                        intent.putExtra("position", position);
                        intent.putExtra("function", 6);
                        intent.putExtra("initial_value", counter_to_display.getInitialValue());
                        sendBroadcast(intent);

                        // set current value to initial value
                        counter_to_display.setCurrentValue(Integer.
                                valueOf(editInitialValue.getText().toString()));
                        Intent intent1 = new Intent("com.example.yizhou.counter.DATA_CHANGED");
                        intent1.putExtra("position", position);
                        intent1.putExtra("function", 5);
                        intent1.putExtra("current_value", counter_to_display.getInitialValue());
                        editCurrentValue.setText(Integer.valueOf(counter_to_display.getCurrentValue()).toString());
                        updateDate();
                        finish_edit_initial = true;
                    }
                }
            }
        });

        // let user can edit name
        editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    finish_edit_name = false;
                }else if(!finish_edit_name){
                    if(editName.getText().length() == 0){
                        // reset current value to initial value
                        editName.setText(counter_to_display.getName());
                        Toast.makeText(CounterDisplay.this, "Invalid input, value has been reset",
                                Toast.LENGTH_SHORT).show();
                        finish_edit_initial = true;
                    }else if(editName.getText().length() >15){
                        // reset current value to initial value
                        editName.setText(counter_to_display.getName());
                        Toast.makeText(CounterDisplay.this, "Name should less than 15 characters",
                                Toast.LENGTH_SHORT).show();
                        finish_edit_initial = true;
                    }else{
                        counter_to_display.setName(editName.getText().toString());
                        Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
                        intent.putExtra("position", position);
                        intent.putExtra("function", 7);
                        intent.putExtra("name", counter_to_display.getName());
                        sendBroadcast(intent);
                        finish_edit_name = true;
                    }
                }
            }
        });

        // let user can edit comment
        editComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    finish_edit_comment = false;
                }else if(!finish_edit_comment){
                    // sent intent to the main activity to change the vaule
                    counter_to_display.setName(editComment.getText().toString());
                    Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
                    intent.putExtra("position", position);
                    intent.putExtra("function",8);
                    intent.putExtra("comment",counter_to_display.getComment());
                    sendBroadcast(intent);
                    finish_edit_comment = true;
                }
            }
        });

    }

    // For function: 1 for increase, 2 for decrease, 3 for delete, 4 for reset
    @Override
    public void onClick(View v){
        int id = v.getId();
        // clear the focuses
        editCurrentValue.clearFocus();
        editInitialValue.clearFocus();
        editComment.clearFocus();
        editName.clearFocus();
        if(id == R.id.button_increase){
            // if Increase button is clicked
            Log.d("Display","Increase button clicked");
            // check if the number is valid to increase
            if(Integer.parseInt(editCurrentValue.getText().toString()) == 99999){
                Toast.makeText(CounterDisplay.this, "Maximum number is reached",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            updateDate();
            counter_to_display.increaseCounter();
            editCurrentValue.setText(Integer.valueOf(counter_to_display.getCurrentValue()).toString());
            // send a boardcast to the main activity to notify the chage
            // and save the file.
            Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
            Log.d("Display","Broadcaster initiated.");
            intent.putExtra("position", position);
            intent.putExtra("function",1);
            Log.d("Display","Broadcaster data added.");
            sendBroadcast(intent);
            Log.d("Display","Broadcaster sent");
        }else if(id == R.id.button_decrease){
            // if Decrease button is clicked
            Log.d("Display","Decrease button clicked");
            if(Integer.parseInt(editCurrentValue.getText().toString()) == 0){
                Toast.makeText(CounterDisplay.this, "Minimum number is reached",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            updateDate();
            counter_to_display.decreaseCounter();
            editCurrentValue.setText(Integer.valueOf(counter_to_display.getCurrentValue()).toString());
            // send a boardcast to the main activity to notify the chage
            // and save the file.
            Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
            intent.putExtra("position", position);
            intent.putExtra("function",2);
            sendBroadcast(intent);
        }else if(id == R.id.button_delete){
            // if Delete button is clicked
            // ask user if they really want to delete
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            alertDialog.setMessage(R.string.alert_message);
            alertDialog.setPositiveButton(R.string.alert_position, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
                    intent.putExtra("function",3);
                    intent.putExtra("position",position);
                    sendBroadcast(intent);
                    finish();
                }
            });
            alertDialog.setNegativeButton(R.string.alert_negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }else if(id == R.id.button_reset){
            // if Reset button is clicked
            updateDate();
            counter_to_display.setCurrentValue(counter_to_display.getInitialValue());
            editCurrentValue.setText(Integer.valueOf(counter_to_display.getCurrentValue()).toString());
            Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
            intent.putExtra("position", position);
            intent.putExtra("function",4);
            sendBroadcast(intent);
        }
    }

    public void updateDate(){
        counter_to_display.setDate(new Date());
        textDate.setText(counter_to_display.getDateString());
    }

    @Override
    public void onBackPressed(){
        editCurrentValue.clearFocus();
        editInitialValue.clearFocus();
        editComment.clearFocus();
        editName.clearFocus();
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    public void resetCurrentValue(int valueToSet){
        counter_to_display.setCurrentValue(valueToSet);
        editCurrentValue.setText(Integer.valueOf(valueToSet).toString());
        Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
        intent.putExtra("position", position);
        intent.putExtra("function",4);
        sendBroadcast(intent);
        updateDate();
        Toast.makeText(CounterDisplay.this, "Invalid input, value has been reset",
                Toast.LENGTH_SHORT).show();
    }

    public void resetInitialValue(int valueToSet){
        counter_to_display.setInitialValue(valueToSet);
        editInitialValue.setText(Integer.valueOf(valueToSet).toString());
        Intent intent = new Intent("com.example.yizhou.counter.DATA_CHANGED");
        intent.putExtra("position", position);
        intent.putExtra("function",5);
        sendBroadcast(intent);
        Toast.makeText(CounterDisplay.this, "Invalid input, value has been reset",
                Toast.LENGTH_SHORT).show();

        // set current value to initial value
        counter_to_display.setCurrentValue(Integer.
                valueOf(editInitialValue.getText().toString()));
        Intent intent1 = new Intent("com.example.yizhou.counter.DATA_CHANGED");
        intent1.putExtra("position", position);
        intent1.putExtra("function", 4);
        intent1.putExtra("current_value", counter_to_display.getInitialValue());
        editCurrentValue.setText(Integer.valueOf(counter_to_display.getCurrentValue()).toString());
        finish_edit_initial = true;
    }

}
