package com.example.wdiary;

import android.content.Context;
import android.content.res.Resources;
import android.icu.util.LocaleData;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private List<Day> days;
    public ViewHolder viewHolder;
    Context context;

    DayAdapter(Context context, List<Day> days) {
        this.days = days;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public DayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DayAdapter.ViewHolder holder, int position) {
        Day day = days.get(position);
        //holder.flagView.setImageResource(state.getFlagResource());

        holder.tempView.setText(context.getString(R.string.temp)+": "+day.getT()+"\u00B0");
        holder.wingView.setText(context.getString(R.string.WVeloc)+": "+day.getWingV()+" м/с");
        holder.wetView.setText(context.getString(R.string.hum)+": "+day.getWetness()+"%");

        holder.dayView.setText(""+day.getDayOfWeek());
        holder.dateView.setText(""+day.getDate());

        Calendar c = Calendar.getInstance();

        Date date = new Date(System.currentTimeMillis());
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);


        //holder.dayView.setText(""+day.getDate().getDay());
        //holder.dayView.setText(week[c.get(Calendar.DAY_OF_WEEK)-2]+", "+day.getDate().getDay()+", ");

        //holder.dayView.setText(day.getDate().dayOfWeek());
        //holder.dateView.setText(day.getDate().getMonthName());
        //holder.dateView.setText(day.getDate().getDay()+", "+holder.dateView.getText());
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //final ImageView flagView;
        TextView tempView, wingView,wetView,dayView,dateView;
        ViewHolder(View view){
            super(view);

            tempView = view.findViewById(R.id.temperature);
            wingView = view.findViewById(R.id.wing);
            wetView = view.findViewById(R.id.wetness);

            dayView = view.findViewById(R.id.day);
            dateView = view.findViewById(R.id.date);

        }
    }

    public void setData(ArrayList<Day> daystemp){
        if (daystemp.isEmpty()) Log.e("error","list of days is empty");
        days=daystemp;
        Log.d("info",days.toString());
    }
    public void addDay(Day d){
        days.add(d);
    }

    /*public String getWeekDay(Date date){
        String[] week = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        int y = date.getYear()-2018;

    }*/
}