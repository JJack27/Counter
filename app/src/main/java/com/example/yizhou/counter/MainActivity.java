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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class that runs the MainActivity
 */
public class MainActivity extends AppCompatActivity {
    public GridView gridView;
    private int number_of_counters = 0;
    private String summary_message;
    protected ArrayList<Counters> list_of_counter = new ArrayList<Counters>();
    private CustomAdapter adapter = null;
    private IntentFilter intentFilter;
    private CounterBroadcastReceiver counterBroadcastReceiver;
    private String FILENAME = new String("file.sav");
    private boolean loadFile = false;

    /**
     * Called when this activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.d("Initializing","Before Create counters");

        // initializing broadcast receiver
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.yizhou.counter.DATA_CHANGED");
        counterBroadcastReceiver = new CounterBroadcastReceiver();
        registerReceiver(counterBroadcastReceiver, intentFilter);

        // initialize the toolbar
        setSupportActionBar(toolbar);
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

    /**
     * Called when another activity is finished and returned to this activity
     * @param requestCode Request code returned by the last activity
     * @param resultCode Unique code returned initialed in this activity
     * @param data The date returned from the intent sent from last activity
     */
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
                    Log.d("CreatingCounter","Info got.");
                    Log.d("CreatingCounter",newValue);
                    Log.d("CreatingCounter",newName);
                    Log.d("CreatingCounter",newComments);

                    // creating new Counter object
                    Counters counter_created = new Counters(newName,new Date(), Integer.parseInt(newValue), newComments);

                    // add the new counter object to the list
                    list_of_counter.add(counter_created);
                    ArrayList<Counters> newList = new ArrayList<Counters>();
                    newList.addAll(list_of_counter);

                    // update the adapter
                    adapter.updateAdapter(newList);
                    Log.d("CreatingCounter","Added to the list");

                    // update the title of toolbar
                    updateTitle();
                    saveInFile();
                    loadFromFile();
                }
        }
    }

    /**
     * Called when the activity started
     */
    @Override
    public void onStart(){
        super.onStart();

        // load file, only once
        if(!loadFile) {
            loadFromFile();
        }

        // update the summary in tool bar
        summary_message = number_of_counters + " Counters in total";
        CollapsingToolbarLayout collapsed_toolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsed_toolbar.setTitle(summary_message);

        // add adapter to grid view
        Log.d("Initializing","Created list");
        adapter = new CustomAdapter(this, R.layout.grids, list_of_counter);
        gridView = (GridView) findViewById(R.id.list_counter);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // set item onClickListener, when item in the grid view is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("Display","preparing intent");
                Counters counter_send = list_of_counter.get(position);
                Intent intent = new Intent(MainActivity.this, CounterDisplay.class);
                Log.d("Display", new Integer(position).toString());
                intent.putExtra("position",position);
                intent.putExtra("initial",counter_send.getInitialValue());
                intent.putExtra("current",counter_send.getCurrentValue());
                intent.putExtra("date",counter_send.getDateString());
                intent.putExtra("comment",counter_send.getComment());
                intent.putExtra("name",counter_send.getName());
                startActivity(intent);
                Log.d("Display", "Intent sent");
            }
        });
    }

    /**
     * Update the title of the Collapsing title.
     */
    public void updateTitle(){
        summary_message = number_of_counters + " Counters in total";
        CollapsingToolbarLayout collapsed_toolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsed_toolbar.setTitle(summary_message);
    }

    /*
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
    */

    // Function for Broadcast Receiver
    public class CounterBroadcastReceiver extends BroadcastReceiver{
        // For Function: 1 for increase, 2 for decrease, 3 for delete, 4 for reset, 5 for editCurrentvalue
        // 6 for edit initial value, 7 for edit name, 8 for edit comment
        @Override
        public void onReceive(Context context, Intent intent){
            Log.d("Display", "Broadcast received");
            int positionReceived = intent.getIntExtra("position",0);
            int functionReceived= intent.getIntExtra("function",0);
            if(functionReceived == 1){
                // if increased button is clicked...
                list_of_counter.get(positionReceived).increaseCounter();
            }else if(functionReceived == 2){
                // if decreased button is clicked...
                list_of_counter.get(positionReceived).decreaseCounter();
            }else if(functionReceived == 3){
                // delete the counter
                list_of_counter.remove(positionReceived);
                number_of_counters -= 1;
                updateTitle();
            }else if(functionReceived == 4){
                // reset the current value to initial value
                int initial = list_of_counter.get(positionReceived).getInitialValue();
                list_of_counter.get(positionReceived).setCurrentValue(initial);
            }else if(functionReceived == 5){
                // set the current value to new_value
                int new_value = intent.getIntExtra("current_value",0);
                list_of_counter.get(positionReceived).setCurrentValue(new_value);
            }else if(functionReceived == 6){
                // set the initial value to a new_value
                int new_value = intent.getIntExtra("initial_value",0);
                list_of_counter.get(positionReceived).setInitialValue(new_value);
            }else if(functionReceived == 7){
                // set the name to the new name
                String new_name = intent.getStringExtra("name");
                list_of_counter.get(positionReceived).setName(new_name);
            }else if(functionReceived == 8){
                // set the comment to new comment
                String new_comment = intent.getStringExtra("comment");
                list_of_counter.get(positionReceived).setComment(new_comment);
            }

            // update the adapter
            ArrayList<Counters> newList = new ArrayList<Counters>();
            newList.addAll(list_of_counter);
            adapter.updateAdapter(newList);
            saveInFile();
        }
    }

    /**
     * Method to save the data to file.
     */
    public void saveInFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter out = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(list_of_counter, out);
            out.flush();
        }catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to load file
     */
    public void loadFromFile(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counters>>(){}.getType();
            list_of_counter = gson.fromJson(in,listType);
            number_of_counters = list_of_counter.size();
            loadFile = true;
            // read data from gson file

        }catch (FileNotFoundException e) {
            list_of_counter = new ArrayList<Counters>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
