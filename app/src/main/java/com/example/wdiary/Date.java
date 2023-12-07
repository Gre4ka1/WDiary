package com.example.wdiary;


import static com.example.wdiary.R.string.*;

import java.util.HashMap;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public int dayOfWeek(){
        HashMap<Integer,Integer> a = new HashMap<>();
        a.put(0,1);a.put(9,1);a.put(7,3);a.put(1,4);a.put(2,4);a.put(9,4);
        a.put(5,5);a.put(11,6);a.put(8,6);a.put(3,0);a.put(6,0);
        int kY = (6+getYear()%100+(int)((getYear()%100)/4))%7;

        int[] week = {R.string.thu,R.string.fri,R.string.sat,R.string.sunday,
                      R.string.mon,R.string.tue,R.string.wed};
        return week[(a.get(getMonth()) + getDay() + kY)%7];
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }
    public int getMonthName(){
        final int[] year = {R.string.jan,R.string.feb,R.string.mar,R.string.apr,
                            R.string.may,R.string.jun,R.string.jul,R.string.aug,
                            R.string.sep, R.string.oct,R.string.nov,R.string.dec};
        return year[month-1];
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
