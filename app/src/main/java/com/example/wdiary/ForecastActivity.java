package com.example.wdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity {
    ArrayList<Day> days = new ArrayList<>();
    String TAG = "WEATHER";
    WeatherAPI.ApiInterface api;
    DayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);


        //setInitialData();
        RecyclerView recyclerView = findViewById(R.id.forecast_list);
        adapter = new DayAdapter(this, days);
        recyclerView.setAdapter(adapter);
    }
    /*private void setInitialData(){
        days.add(new Day(0,0,0,""));
        //days.add(new Day(0,0,0,""));
        //days.add(new Day(0,0,0,""));
        //days.add(new Day(0,0,0,""));
        //days.add(new Day(0,0,0,""));
    }*/
    public void getWeatherF(View v) {
        ArrayList<Day> dayArrayList = new ArrayList<>();

        String units = "metric";
        String lang = "ru";
        String key = WeatherAPI.KEY;

        Log.d(TAG, "OK");
// get weather forecast
        Call<WeatherForecast> callForecast = api.getForecast("Novosibirsk", units, lang, key);
        callForecast.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.i(TAG, "onResponse");
                WeatherForecast data = response.body();
                //Log.d(TAG,response.toString());

                if (response.isSuccessful()) {
                    Log.i(TAG, "Forecast Success");
                    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("E");



                    //int i = 0; //!!!!!!!!!!!!!!!!
                    Log.e(TAG,data.getItems().toString()+"    ---------------");
                    for (WeatherDay day : data.getItems()) {
                        /*if (i == 0) {
                            i++;
                            continue;
                        }
                        if (i >= 4) break;   //!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
                        if (day.getDate().get(Calendar.HOUR_OF_DAY) == 16) {

                            String date = String.format("%d.%d.%d %d:%d",
                                    day.getDate().get(Calendar.DAY_OF_MONTH),
                                    day.getDate().get(Calendar.MONTH),   //0-12 0-jan
                                    day.getDate().get(Calendar.YEAR),
                                    day.getDate().get(Calendar.HOUR_OF_DAY),
                                    day.getDate().get(Calendar.MINUTE)
                            );
                            String dayOfWeek_ = formatDayOfWeek.format(day.getDate().getTime());
                            Map<String, String> dayOfWeek = new HashMap<>();
                            dayOfWeek.put("пн", getString(R.string.mon));
                            dayOfWeek.put("вт", getString(R.string.tue));
                            dayOfWeek.put("ср", getString(R.string.wed));
                            dayOfWeek.put("чт", getString(R.string.thu));
                            dayOfWeek.put("пт", getString(R.string.fri));
                            dayOfWeek.put("сб", getString(R.string.sat));
                            dayOfWeek.put("вс", getString(R.string.sunday));

                            //Log.i("DATE",day.getDate().get(Calendar.DAY_OF_MONTH)+"");
                            Day tempDay = new Day(day.getDate().get(Calendar.DAY_OF_MONTH)+"."+(day.getDate().get(Calendar.MONTH)+1),dayOfWeek.get(dayOfWeek_),(int) Double.parseDouble(day.getTemp()),
                                    (int) Double.parseDouble(day.getWVel()),
                                    (int) Double.parseDouble(day.getHum()),
                                    "xz");
                            //days.clear();
                            //days.add(tempDay);
                            Log.e(TAG,"add 1 element");
                            dayArrayList.add(tempDay);
                            adapter.addDay(tempDay);
                            adapter.notifyDataSetChanged();







                            //i++; //!!!!!!!!!!!!!!!!!!!!!!!!
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });
        //adapter.setData(dayArrayList);
        //adapter.notifyDataSetChanged();
        /*Log.i(TAG,dayArrayList.toString());
        return dayArrayList;*/
    }
    public void gotoMA(View v){
        //finishActivity(228);
        finish();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }
}