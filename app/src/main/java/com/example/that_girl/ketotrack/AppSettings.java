package com.example.that_girl.ketotrack;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by that_girl on 12/3/2015.
 */
public class AppSettings implements Serializable{
    Date currentDate;
    int index;

    public AppSettings() {
        this.currentDate = new Date();
        index=0;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void incIndex(){
        index++;
    }
    @Override
    public String toString() {
        return "Current Date: " + currentDate + "\n"
                    + "Day Index: " + getIndex() + "\n";
    }
}
