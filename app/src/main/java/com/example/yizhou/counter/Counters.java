/*
 * Copyright 2017 Yizhou Zhao, CMPUT 301, University of Alberta. All rights reserved.
 * You may use, distribute, or modify this code under terms and conditirions of the Code of Student Beaviour at University of Alberta.
 * You may find a copy of the license in this project. Otherwise, please contact yizhou4@ualberta.ca.
 *
 */

package com.example.yizhou.counter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a counter
 * @author Yizhou Zhao
 * @see CounterList
 * @since 1.0
 */
public class Counters implements Serializable{
    // Declaring variables
    private String name;
    private Date date;
    private int currentValue;
    private int initialValue;
    private String comment;

    // Define methods

    /**
     * Construct a Counter object
     * @param name name of the counter
     * @param date time of the counter that be created
     * @param initial_value the initial value of the counter
     */
    public Counters(String name, Date date, int initial_value){
        this.name = name;
        this.date = date;
        this.initialValue = initial_value;
        this.currentValue = initial_value;
        this.comment = "";
    }

    /**
     * Construct a Counter object
     * @param name name of the counter
     * @param date time of the counter that be created
     * @param initial_value the initial value of the counter
     * @param comment the comments of the counter
     */
    public Counters(String name, Date date, int initial_value, String comment){
        this.name = name;
        this.date = date;
        this.initialValue = initial_value;
        this.currentValue = initial_value;
        this.comment = comment;
    }

    /**
     * Get name of the Counter
     * @return a String object contains the name of the counter
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the Counter object
     * @param name the new name of the counter
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the date of the counter
     * @return a Date object represents the date of the counter
     */
    public Date getDate() {
        return this.date;
    }

    public String getDateString(){
        Date d = this.date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
        return sdf.format(d);
    }

    /**
     * Set the date of the counter
     * @param date the new date of Counter
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get the current value of the counter
     * @return int: the current value
     */
    public int getCurrentValue() {
        return currentValue;
    }

    /**
     * Set the current value of the counter, will update the date of the counter as well
     * @param current_value int: new current value of the counter
     */
    public void setCurrentValue(int current_value) {
        this.currentValue = current_value;
    }

    /**
     * Get the initial_value of the counter
     * @return int: the initial value of the counter
     */
    public int getInitialValue() {
        return initialValue;
    }

    /**
     * Set the initial_value of the counter
     * @param initial_value the new initial value of the counter
     */
    public void setInitialValue(int initial_value) {
        this.initialValue = initial_value;
    }

    /**
     * Get the comment from counter
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment of the counter
     * @param comment new comment message of the counter
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Increment the current value of the counter by 1, will update the date of the counter as well
     */
    public void increaseCounter(){
        this.currentValue += 1;
    }

    /**
     * Decrease the current value of the counter by 1, will update the date of the counter as well
     */
    public void decreaseCounter(){
        this.currentValue -= 1;
    }
}
