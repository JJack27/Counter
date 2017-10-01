/*
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by yizho on 2017/9/28.
 */

public class CustomAdapter extends ArrayAdapter<Counters> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Counters> counters = new ArrayList<Counters>();

    public CustomAdapter(Context context, int resourceId, ArrayList<Counters> objects){
        super(context, resourceId, objects);
        this.context = context;
        this.layoutResourceId = resourceId;
        this.counters = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = new ViewHolder();

        if(convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder.text_initial_value = (TextView) convertView.
                    findViewById(R.id.grid_view_current_value);
            holder.text_name = (TextView) convertView.
                    findViewById(R.id.grid_view_name);
            holder.text_date = (TextView) convertView.
                    findViewById(R.id.grid_view_date);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Counters counter = counters.get(position);
        holder.text_initial_value.setText(String.valueOf(counters.get(position).getCurrentValue()));
        holder.text_name.setText(String.valueOf(counters.get(position).getName()));
        holder.text_date.setText(String.valueOf(counters.get(position).getDateString()));
        return convertView;
    }

    public void updateAdapter(ArrayList<Counters> newList){
        counters.clear();
        counters.addAll(newList);
        this.notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView text_initial_value;
        TextView text_name;
        TextView text_date;
    }
}
