/*
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CounterDisplay extends AppCompatActivity implements View.OnClickListener{

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

    }

    @Override
    public void onClick(View v){

    }
}
