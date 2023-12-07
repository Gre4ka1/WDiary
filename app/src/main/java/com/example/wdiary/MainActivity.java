package com.example.wdiary;


import static com.google.android.material.internal.ContextUtils.getActivity;
//import static com.google.android.material.snackbar.BaseTransientBottomBar.handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wdiary.databinding.ActivityMainBinding;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String TAG = "WEATHER";

    WeatherAPI.ApiInterface api;
    //Handler handler = new Handler();
    public ActivityMainBinding binding;
    /*private static final String API_KEY = "72miGN270wipSIcf0XF6tYvcU6ANuyOI";
    private static final String LOCATION_KEY = "YOUR_LOCATION_KEY";*/

    ArrayList<Day> days = new ArrayList<>();
    public DayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        adapter = new DayAdapter(this, days);
        setContentView(binding.getRoot());

        /*setInitialData();
        RecyclerView recyclerView = binding.list;
        recyclerView.setAdapter(adapter);*/
        //-----
        //updateWeatherData("Novosibirsk");
        //Call<WeatherResponse> ca = service.
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        //Here del MA

    }
    public void getWeather(View v) {
        Double lat = 55.01;
        Double lng = 82.01;
        String units = "metric";
        String lang = "ru";
        String key = WeatherAPI.KEY;

        Log.d(TAG, "OK");

        // get weather for today
        Call<WeatherDay> callToday = api.getToday(lat, lng, units,lang, key);
        callToday.enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                Log.e(TAG, "onResponse");
                WeatherDay data = response.body();
                //Log.d(TAG,response.toString());

                if (response.isSuccessful()) {
                    binding.tvTemp.setText(data.getCity() + " " + data.getTempWithDegree()+" "+data.getWVel());
                    //Glide.with(MainActivity.this).load(data.getIconUrl()).into(binding.ivImage);
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });

        // get weather forecast
//        Call<WeatherForecast> callForecast = api.getForecast(lat, lng, units, key);
//        callForecast.enqueue(new Callback<WeatherForecast>() {
//            @Override
//            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
//                Log.e(TAG, "onResponse");
//                WeatherForecast data = response.body();
//                //Log.d(TAG,response.toString());
//
//                if (response.isSuccessful()) {
//                    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("E");
//                    LayoutParams paramsTextView = new LayoutParams(
//                            LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//                    LayoutParams paramsImageView = new LayoutParams(convertDPtoPX(40, MainActivity.this),
//                            convertDPtoPX(40, MainActivity.this));
//
//                    int marginRight = convertDPtoPX(15, MainActivity.this);
//                    LayoutParams paramsLinearLayout = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                            LayoutParams.WRAP_CONTENT);
//                    paramsLinearLayout.setMargins(0, 0, marginRight, 0);
//
//                    llForecast.removeAllViews();
//
//                    for (WeatherDay day : data.getItems()) {
//                        if (day.getDate().get(Calendar.HOUR_OF_DAY) == 15) {
//                            String date = String.format("%d.%d.%d %d:%d",
//                                    day.getDate().get(Calendar.DAY_OF_MONTH),
//                                    day.getDate().get(Calendar.WEEK_OF_MONTH),
//                                    day.getDate().get(Calendar.YEAR),
//                                    day.getDate().get(Calendar.HOUR_OF_DAY),
//                                    day.getDate().get(Calendar.MINUTE)
//                            );
//                            Log.d(TAG, date);
//                            Log.d(TAG, day.getTempInteger());
//                            Log.d(TAG, "---");
//
//                            // child view wrapper
//                            LinearLayout childLayout = new LinearLayout(MainActivity.this);
//                            childLayout.setLayoutParams(paramsLinearLayout);
//                            childLayout.setOrientation(LinearLayout.VERTICAL);
//
//                            // show day of week
//                            TextView tvDay = new TextView(MainActivity.this);
//                            String dayOfWeek = formatDayOfWeek.format(day.getDate().getTime());
//                            tvDay.setText(dayOfWeek);
//                            tvDay.setLayoutParams(paramsTextView);
//                            childLayout.addView(tvDay);
//
//                            // show image
//                            ImageView ivIcon = new ImageView(MainActivity.this);
//                            ivIcon.setLayoutParams(paramsImageView);
//                            Glide.with(MainActivity.this).load(day.getIconUrl()).into(ivIcon);
//                            childLayout.addView(ivIcon);
//
//                            // show temp
//                            TextView tvTemp = new TextView(MainActivity.this);
//                            tvTemp.setText(day.getTempWithDegree());
//                            tvTemp.setLayoutParams(paramsTextView);
//                            childLayout.addView(tvTemp);
//
//                            llForecast.addView(childLayout);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WeatherForecast> call, Throwable t) {
//                Log.e(TAG, "onFailure");
//                Log.e(TAG, t.toString());
//            }
//        });

    }
    /*private void setInitialData(){

        days.add(new Day (new Date(15,11,2023), 15,10,6,"no"));
        days.add(new Day (new Date(16,11,2023), 10,170,6,"no"));
        days.add(new Day (new Date(17,11,2023), 1,0,6,"no"));
        days.add(new Day (new Date(18,11,2023), 189,90,6,"no"));
    }*/
    /*private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                @SuppressLint("RestrictedApi") final JSONObject json = RemoteFetch.getJSON(getActivity(getApplicationContext()), city);
                if(json == null){
                    handler.post(new Runnable(){
                        @SuppressLint("RestrictedApi")
                        public void run(){
                            Toast.makeText(getActivity(getApplicationContext()),
                                    getActivity(getApplicationContext()).getString(Integer.parseInt("place not found")),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            adapter.renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }*/
    /*public void renderWeather(JSONObject json){
        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            days.clear();
            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new com.example.wdiary.Date(json.getLong("dt")*1000));
            for (int i = 0; i < 5; i++) {

                days.add(new Day(new Date()))
            }

            .setText(
                    details.getString("description").toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + main.getString("humidity") + "%" +
                            "\n" + "Pressure: " + main.getString("pressure") + " hPa");

            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp"))+ " ℃");
            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new com.example.wdiary.Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }*/
}