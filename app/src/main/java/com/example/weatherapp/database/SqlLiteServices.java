package com.example.weatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.weatherapp.bean.MainBean;
import com.example.weatherapp.bean.WeatherBean;
import com.example.weatherapp.bean.WeatherDetailBean;

import java.util.ArrayList;
import java.util.List;

public class SqlLiteServices {
    Context context;
    MainBean mainBean;
    public SqlLiteServices(Context context) {

        this.context = context;
    }

    public void createTables() {
        SQLiteDatabase database = context.openOrCreateDatabase("WeatherApp.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        database.execSQL("create table if not exists tblWeatherReport(id integer primary key autoincrement, city text, time text, weather text, temperature text, speed text)");
        Log.e("Database", "Table created successfully");
        ContentValues values = new ContentValues();
        values.put("city", "Melbourne");
        values.put("time", "12:00 pm");
        values.put("weather", "Clear Sky");
        values.put("temperature", "22");
        values.put("speed", "2");

        long l=database.insert("tblWeatherReport",null,values);
        if(l>-1){
            Log.e("Database","Inserted Successfully");
        }else {
            Log.e("Database","didn't Inserted Successfully");
        }
    }

    public List<MainBean> getAllWeatherData() {
        SQLiteDatabase database = context.openOrCreateDatabase("WeatherApp.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        database.execSQL("create table if not exists tblWeatherReport(id integer primary key autoincrement, city text, time text, weather text, temperature text, speed text)");
        Cursor cursor = database.rawQuery("select * from tblWeatherReport", null);
        List<MainBean> allDataList = new ArrayList<>();
        if (cursor.moveToLast()) {
            do {
                mainBean = new MainBean();
                mainBean.setCity(cursor.getString(cursor.getColumnIndex("city")));
                mainBean.setSpeed(cursor.getString(cursor.getColumnIndex("speed")));
                mainBean.setTemp(cursor.getString(cursor.getColumnIndex("temperature")));
                mainBean.setTime(cursor.getString(cursor.getColumnIndex("time")));
                mainBean.setWeather(cursor.getString(cursor.getColumnIndex("weather")));

                allDataList.add(mainBean);
            } while (cursor.moveToPrevious());
        }
        return allDataList;
    }


    public void addUpdatedData(MainBean main) {
        SQLiteDatabase database = context.openOrCreateDatabase("WeatherApp.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        ContentValues values = new ContentValues();
        values.put("city", main.getCity());
        values.put("time", main.getTime());
        values.put("weather", main.getWeather());
        values.put("temperature", main.getTemp());
        values.put("speed", main.getSpeed());

        long l=database.insert("tblWeatherReport",null,values);
        if(l>-1){
            Log.e("Database","Inserted Successfully");
        }else {
            Log.e("Database","didn't Inserted Successfully");
        }
    }

    public void delete() {
        SQLiteDatabase database = context.openOrCreateDatabase("WeatherApp.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        database.execSQL("delete from tblWeatherReport");
        Log.e("Tables", "Truncated");
    }
}