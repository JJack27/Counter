/*
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A subclass of BaseAdapter, in order to show the grids correctly
 */
public class CounterAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Counters> counters;

    public CounterAdapter(Context context, ArrayList<Counters> counters){
        Log.d("CounterAdapter","Creating CounterAdapter");
        this.context = context;
        this.counters = counters;
        Log.d("CounterAdapter","Finishing Creating CounterAdapter");
    }

    public View getView(int position, View convertView, ViewGroup parent){
        //LayoutInflater inflater = (LayoutInflater) context
        //        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //View gridView;

        if(convertView == null){
            //gridView = new View(context);
            LayoutInflater inflater = LayoutInflater.from(context);

            // get layout from grids.xml
            convertView = inflater.inflate(R.layout.grids, null);

            // set value into Textview grid_view_current_value
            Log.d("CounterAdapter","Adding to the first textview");
            TextView text_current_vlaue = (TextView) convertView.
                    findViewById(R.id.grid_view_current_value);
            text_current_vlaue.setText(String.valueOf(counters.get(position).getCurrent_value()));

            // set value into Textview grid_view_name
            Log.d("CounterAdapter","Adding to the 2nd textview");
            TextView text_name = (TextView) convertView.
                    findViewById(R.id.grid_view_name);
            Log.d("CounterAdapter",counters.get(position).getName());
            text_name.setText(counters.get(position).getName());

            // set value into Textview grid_view)date
            Log.d("CounterAdapter","Adding to the 3rd textview");
            TextView text_date = (TextView) convertView.
                    findViewById(R.id.grid_view_date);
            text_date.setText(counters.get(position).getDateString());

        }else{
            convertView = (View) convertView;
        }
        return convertView;
    }

    public void updateAdapter(ArrayList<Counters> newList){
        counters.clear();
        counters.addAll(newList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return counters.size();
    }

    @Override
    public Object getItem(int position){
        return counters.indexOf(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
}
