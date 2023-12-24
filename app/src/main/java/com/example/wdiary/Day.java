package com.example.wdiary;



public class Day {
    private int t;
    private String date;
    private String dayOfWeek;
    private int wingV;
    private int wetness;
    private String rain;

    public Day() {
    }

    public Day(String date,String dayOfWeek, int t, int wingV, int wetness, String rain) {
        this.t = t;
        this.wingV = wingV;
        this.wetness = wetness;
        this.rain = rain;
        this.date=date;
        this.dayOfWeek=dayOfWeek;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getWingV() {
        return wingV;
    }

    public void setWingV(int wingV) {
        this.wingV = wingV;
    }

    public int getWetness() {
        return wetness;
    }

    public void setWetness(int wetness) {
        this.wetness = wetness;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}