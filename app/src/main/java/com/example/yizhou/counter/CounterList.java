/*
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import java.util.ArrayList;

/**
 * A class that represents a list of counters
 * @author Yizhou
 * @since 1.0
 * @see Counters
 */
public class CounterList {
    private ArrayList<Counters> list_of_counter;

    public void add_counter(Counters counter){
        this.list_of_counter.add(counter);
    }

    public void remove_counter(){

    }
}
