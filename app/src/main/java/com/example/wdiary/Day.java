package com.example.wdiary;



public class Day {
    private Date date;
    private int t;
    private int wingV;
    private int wetness;
    private String rain;

    public Day(Date date, int t, int wingV, int wetness, String rain) {
        this.date = date;
        this.t = t;
        this.wingV = wingV;
        this.wetness = wetness;
        this.rain = rain;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
