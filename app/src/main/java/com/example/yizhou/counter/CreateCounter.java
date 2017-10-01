/*
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity to create a new counter
 */
public class CreateCounter extends AppCompatActivity {

    /**
     * Called when activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_counter);

        // Initiate the button "finish"
        Button finish_create = (Button) findViewById(R.id.create_finish);
        finish_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get text from EditText
                EditText edit_initial_value = (EditText) findViewById(R.id.initial_value_created);
                EditText edit_name = (EditText) findViewById(R.id.name_created);
                EditText edit_comment = (EditText) findViewById(R.id.comments_created);
                String initial_value = edit_initial_value.getText().toString();
                String name = edit_name.getText().toString();
                String comment = edit_comment.getText().toString();
                // validating the input for initial value and name
                if( initial_value.length() == 0 ) {             // if there is no input
                    edit_initial_value.setError("Initial value is required!");
                    return;
                }
                if(Integer.parseInt(initial_value) > 99999){    // if the initial value is too large
                    edit_initial_value.setError("Please enter a number less than 100000");
                    return;
                }else if(Integer.parseInt(initial_value) < 0){  // if the initial value is too small
                    edit_initial_value.setError("Please enter a non-negative number");
                    return;
                }
                if(name.length() == 0){                         // if the name is empty
                    edit_name.setError("Name is required!");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("initial_value",initial_value);
                intent.putExtra("name",name);
                intent.putExtra("comment",comment);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }
}
