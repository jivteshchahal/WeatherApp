package com.example.weatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.weatherapp.R;
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
        SQLiteDatabase database = context.openOrCreateDatabase(context.getString(R.string.dbName), SQLiteDatabase.CREATE_IF_NECESSARY, null);

        database.execSQL("create table if not exists "+context.getString(R.string.dbTable)+"(id integer primary key autoincrement, city text, time text, weather text, temperature text, speed text)");
        Log.e("Database", "Table created successfully");
        ContentValues values = new ContentValues();
        values.put("city", "Melbourne");
        values.put("time", "12:00 pm");
        values.put("weather", "Clear Sky");
        values.put("temperature", "22");
        values.put("speed", "2");

        long l=database.insert(context.getString(R.string.dbTable),null,values);
        if(l>-1){
            Log.e("Database","Inserted Successfully");
        }else {
            Log.e("Database","didn't Inserted Successfully");
        }
    }

    public List<MainBean> getAllWeatherData() {
        SQLiteDatabase database = context.openOrCreateDatabase(context.getString(R.string.dbName), SQLiteDatabase.CREATE_IF_NECESSARY, null);
        database.execSQL("create table if not exists " + context.getString(R.string.dbTable)+"(id integer primary key autoincrement, city text, time text, weather text, temperature text, speed text)");
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
        SQLiteDatabase database = context.openOrCreateDatabase(context.getString(R.string.dbName), SQLiteDatabase.CREATE_IF_NECESSARY, null);

        ContentValues values = new ContentValues();
        values.put("city", main.getCity());
        values.put("time", main.getTime());
        values.put("weather", main.getWeather());
        values.put("temperature", main.getTemp());
        values.put("speed", main.getSpeed());

        long l=database.insert(context.getString(R.string.dbTable),null,values);
        if(l>-1){
            Log.e("Database","Inserted Successfully");
        }else {
            Log.e("Database","didn't Inserted Successfully");
        }
    }
//    private int getCity(MainBean mainBean){
//        SQLiteDatabase database = context.openOrCreateDatabase("WeatherApp.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
//        Cursor c = database.query("tblWeatherReport",new String[]{"id"}, "city =? AND weather=?",new String[]{mainBean.getTime(),mainBean.getWeather()},null,null,null,null);
//        if (c.moveToFirst()) //if the row exist then return the id
//            return c.getInt(c.getColumnIndex("city"));
//        return -1;
//    }
    public void insertOrUpdate(MainBean main){
        SQLiteDatabase database = context.openOrCreateDatabase(context.getString(R.string.dbName), SQLiteDatabase.CREATE_IF_NECESSARY, null);

        ContentValues values = new ContentValues();
        values.put("city", main.getCity());
        values.put("time", main.getTime());
        values.put("weather", main.getWeather());
        values.put("temperature", main.getTemp());
        values.put("speed", main.getSpeed());
        Log.e("city from sql",main.getCity());
        int u = database.update(context.getString(R.string.dbTable), values, "city=?", new String[]{main.getCity()});
        Log.e("update from sql",u+"");

        if (u == 0) {
            database.insertWithOnConflict(context.getString(R.string.dbTable), null, values, SQLiteDatabase.CONFLICT_REPLACE);
            Log.e("Database","inserted Successfully");
        }
        else{
            Log.e("Database","updated Successfully");
        }
//        int id = getCity(main);
//        if(id==-1) {
//            database.insert("tblWeatherReport", null, values);
//            Log.e("Database","Inserted Successfully");
//        }
//        else {
//            database.update("tblWeatherReport", values, "city=?", new String[]{main.getCity()});
//            Log.e("Database","Updated Successfully");
//        }
    }

    public void delete() {
        SQLiteDatabase database = context.openOrCreateDatabase(context.getString(R.string.dbName), SQLiteDatabase.CREATE_IF_NECESSARY, null);
        database.execSQL("delete from "+context.getString(R.string.dbTable));
        Log.e("Tables", "Truncated");
    }
}