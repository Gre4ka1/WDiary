package com.example.wdiary;


import static com.google.android.material.internal.ContextUtils.getActivity;
//import static com.google.android.material.snackbar.BaseTransientBottomBar.handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdiary.databinding.ActivityMainBinding;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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
    private HashMap<String, Integer> icons = new HashMap<>();

    {
        icons.put("01d", R.drawable.i01d);
        icons.put("01n", R.drawable.i01n);
        icons.put("02d", R.drawable.i02d);
        icons.put("02n", R.drawable.i02n);
        icons.put("03d", R.drawable.i03d);
        icons.put("03n", R.drawable.i03d);
        icons.put("04d", R.drawable.i04d);
        icons.put("04n", R.drawable.i04d);
        icons.put("09d", R.drawable.i09d);
        icons.put("09n", R.drawable.i09d);
        icons.put("10d", R.drawable.i10d);
        icons.put("10n", R.drawable.i10n);
        icons.put("13d", R.drawable.i13d);
        icons.put("13n", R.drawable.i13d);
        icons.put("50d", R.drawable.i50d);
        icons.put("50n", R.drawable.i50d);
    }

    private HashMap<String, String> dayOfWeek = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //updateWeatherData("Novosibirsk");

        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);

        dayOfWeek.put("пн", getString(R.string.mon));
        dayOfWeek.put("вт", getString(R.string.tue));
        dayOfWeek.put("ср", getString(R.string.wed));
        dayOfWeek.put("чт", getString(R.string.thu));
        dayOfWeek.put("пт", getString(R.string.fri));
        dayOfWeek.put("сб", getString(R.string.sunday));
        dayOfWeek.put("вс", getString(R.string.sat));

    }

    public void getWeather(View v) throws IOException {
        String units = "metric";
        String lang = "ru";
        String key = WeatherAPI.KEY;

        Log.d(TAG, "getWeather");



        FileOutputStream fos = null;
        try {
            String text = "Novosibirsk";
            fos = openFileOutput("city.txt", MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch(IOException ex) {
            Log.e(TAG,ex.getMessage());
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                Log.e(TAG,ex.getMessage());}
        }


        FileInputStream fin = null;
        String city = "Novosibirsk";
        try {
            fin = openFileInput("city.txt");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            city = new String(bytes);
            Log.i(TAG, city);
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Log.e(TAG, ex.getMessage());
            }
        }

        // get weather for today

        Call<WeatherDay> callToday = api.getToday(city, units, lang, key);
        callToday.enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                Log.i(TAG, "onResponse");
                WeatherDay data = response.body();

                if (response.isSuccessful()) {
                    binding.placeView.setText(data.getCity() + " ");
                    binding.tempView.setText(data.getTempWithDegree() + " ");
                    binding.windView.setText(data.getWVel() + " м/с");
                    Log.i(TAG, data.getIcon().toString());
                    try {
                        binding.weatherImage.setImageResource(R.drawable.i02d);
                    } catch (NullPointerException e) {
                        Log.e(TAG, data.getIcon().toString() + " " + e);
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });

        // get weather forecast
        Call<WeatherForecast> callForecast = api.getForecast("Novosibirsk", units, lang, key);
        callForecast.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.i(TAG, "onResponse");
                WeatherForecast data = response.body();

                if (response.isSuccessful()) {
                    Log.i(TAG, "Forecast Success");
                    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("E");

                    int i = 0;
                    int minTemp=100;
                    for (WeatherDay day : data.getItems()) {
                        if (i == 0) {
                            i++;
                            continue;
                        }
                        String dayOfWeek_ = formatDayOfWeek.format(day.getDate().getTime());
                        TextView[] vList = new TextView[]{binding.date1, binding.date2, binding.date3,
                                binding.temperature1, binding.temperature2, binding.temperature3};

                        if (i >= 4) break;

                        switch (day.getDate().get(Calendar.HOUR_OF_DAY)){
                            case 1:
                            case 4:
                            case 7:
                            case 10:
                                if (Integer.parseInt(day.getTempInteger())<minTemp) minTemp= Integer.parseInt(day.getTempInteger());
                        }


                        if (day.getDate().get(Calendar.HOUR_OF_DAY) == 16) {

                            /*String date = String.format("%d.%d.%d %d:%d",
                                    day.getDate().get(Calendar.DAY_OF_MONTH),
                                    day.getDate().get(Calendar.MONTH),   //0-12 0-jan
                                    day.getDate().get(Calendar.YEAR),
                                    day.getDate().get(Calendar.HOUR_OF_DAY),
                                    day.getDate().get(Calendar.MINUTE)
                            );
                            */

                            vList[i - 1].setText(day.getDate().get(Calendar.DAY_OF_MONTH) + ", " + dayOfWeek.get(dayOfWeek_));
                            vList[i + 2].setText((minTemp+"\u00B0"+" / " + day.getTempWithDegree()));
                            minTemp=100;
                            //vList[i+5].setText(day.getWVel()+" м/с");
                            i++;
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

    }

    public void goToForecast(View view) {
        Intent intent = new Intent(this, ForecastActivity.class);
        startActivity(intent);
    }
    public void goToSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}