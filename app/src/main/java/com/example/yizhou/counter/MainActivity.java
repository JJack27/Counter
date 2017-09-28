/* MainActivity
 *
 * Version 1.0
 *
 * September 27, 2017
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    private int number_of_counters = 0;
    private String summary_message;
    private ArrayList<Counters> list_of_counter = new ArrayList<Counters>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.d("Initializing","Before Create counters");
        // testing data
        Counters counter1 = new Counters("counter1", new Date(), 0);
        Counters counter2 = new Counters("counter2", new Date(), 10);
        Log.d("Initializing","Created counters");
        list_of_counter.add(counter1);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter1);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter2);
        list_of_counter.add(counter1);
        list_of_counter.add(counter1);
        list_of_counter.add(counter1);
        list_of_counter.add(counter1);
        list_of_counter.add(counter1);
        list_of_counter.add(counter1);




        // add adapter to grid view
        Log.d("Initializing","Created list");
        gridView = (GridView) findViewById(R.id.list_counter);
        gridView.setAdapter(new CounterAdapter(this, list_of_counter));

        setSupportActionBar(toolbar);
        // hide the menu
        //View menu = findViewById(R.id.action_settings);
        //menu.setVisibility(View.INVISIBLE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the activity to add a counter
                Intent intent = new Intent(MainActivity.this, CreateCounter.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    number_of_counters += 1;

                    // get data from the createCounter activity
                    String newValue = data.getStringExtra("initial_value");
                    String newName = data.getStringExtra("name");
                    String newComments = data.getStringExtra("comment");

                    // creating new Counter object

                    // update the title of toolbar
                    updateTitle();
                    Log.d("Toolbar",summary_message);
                }
        }
    }

    /**
     * Update the title of the Collapsing title.
     */
    public void updateTitle(){
        summary_message = number_of_counters + " Counters in total";
        CollapsingToolbarLayout collapsed_toolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsed_toolbar.setTitle(summary_message);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
