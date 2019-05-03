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
    SQLiteDatabase database;

    public SqlLiteServices(Context context) {
        this.context = context;
        database = context.openOrCreateDatabase(context.getString(R.string.dbName), SQLiteDatabase.CREATE_IF_NECESSARY, null);
    }

    public void createTables() {

        database.execSQL("create table if not exists "+context.getString(R.string.dbTable)+"(id integer primary key autoincrement, city text, time text, weather text, temperature text, speed text)");
        Log.e("Database", "Table created successfully");
        ContentValues values = new ContentValues();
        values.put(context.getString(R.string.dbRefCity), "Melbourne");
        values.put(context.getString(R.string.dbRefTime), "12:00 pm");
        values.put(context.getString(R.string.dbRefWeather), "Clear Sky");
        values.put(context.getString(R.string.dbRefTemperature), "22");
        values.put(context.getString(R.string.dbRefSpeed), "2");

        long l=database.insert(context.getString(R.string.dbTable),null,values);
        if(l>-1){
            Log.e("Database","Inserted Successfully");
        }else {
            Log.e("Database","didn't Inserted Successfully");
        }
    }

    public List<MainBean> getAllWeatherData() {
        database.execSQL("create table if not exists " + context.getString(R.string.dbTable)+"(id integer primary key autoincrement, city text, time text, weather text, temperature text, speed text)");
        Cursor cursor = database.rawQuery("select * from "+context.getString(R.string.dbTable), null);
        List<MainBean> allDataList = new ArrayList<>();
        if (cursor.moveToLast()) {
            do {
                mainBean = new MainBean();
                mainBean.setCity(cursor.getString(cursor.getColumnIndex(context.getString(R.string.dbRefCity))));
                mainBean.setSpeed(cursor.getString(cursor.getColumnIndex(context.getString(R.string.dbRefSpeed))));
                mainBean.setTemp(cursor.getString(cursor.getColumnIndex(context.getString(R.string.dbRefTemperature))));
                mainBean.setTime(cursor.getString(cursor.getColumnIndex(context.getString(R.string.dbRefTime))));
                mainBean.setWeather(cursor.getString(cursor.getColumnIndex(context.getString(R.string.dbRefWeather))));

                allDataList.add(mainBean);
            } while (cursor.moveToPrevious());
        }
        return allDataList;
    }

    public void insertOrUpdate(MainBean main){
        ContentValues values = new ContentValues();
        values.put(context.getString(R.string.dbRefCity), main.getCity());
        values.put(context.getString(R.string.dbRefTime), main.getTime());
        values.put(context.getString(R.string.dbRefWeather), main.getWeather());
        values.put(context.getString(R.string.dbRefTemperature), main.getTemp());
        values.put(context.getString(R.string.dbRefSpeed), main.getSpeed());
        int u = database.update(context.getString(R.string.dbTable), values, context.getString(R.string.dbRefCity)+"=?", new String[]{main.getCity()});
        Log.e("update from sql",u+"");

        if (u == 0) {
            database.insertWithOnConflict(context.getString(R.string.dbTable), null, values, SQLiteDatabase.CONFLICT_REPLACE);
            Log.e("Database","inserted Successfully");
        }
        else{
            Log.e("Database","updated Successfully");
        }

    }

    public void delete() {
        database.execSQL("delete from "+context.getString(R.string.dbTable));
        Log.e("Tables", "Truncated");
    }
}