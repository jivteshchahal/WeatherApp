package com.example.weatherapp;

import android.content.Context;
import android.util.Log;

import com.example.weatherapp.activity.MainActivity;
import com.example.weatherapp.bean.MainBean;
import com.example.weatherapp.bean.WeatherBean;
import com.example.weatherapp.database.SqlLiteServices;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SqlDataTest {
    Context context;

    @Test
    public void beanTesting() {
//        MainBean mainBeanList = new MainBean();
//               SqlLiteServices(context.getApplicationContext()).getAllWeatherData();
//        for (int i = 0; i < mainBeanList.size(); i++) {
//        mainBeanList.getCity();
//        mainBeanList.getTemp();
//        mainBeanList.getTime();
//        mainBeanList.getWeather();
//        mainBeanList.getSpeed();
//            }
//        MainBean beanList = new ArrayList<>();
        MainBean mainBean = new MainBean();
        mainBean.setCity("melbourne");
        mainBean.setSpeed("2");
        mainBean.setWeather("cloudy");
        mainBean.setTime("Wednesday 12:00PM");
        mainBean.setTemp("20");
        assertEquals((mainBean.getCity()),("melbourne"));
        assertEquals((mainBean.getSpeed()),("2"));
        assertEquals((mainBean.getTemp()),("20"));
        assertEquals((mainBean.getWeather()),("cloudy"));
        assertEquals((mainBean.getTime()),("Wednesday 12:00PM"));
    }
    @Test
    public void beanTesting1() {
//        MainBean mainBeanList = new MainBean();
//               SqlLiteServices(context.getApplicationContext()).getAllWeatherData();
//        for (int i = 0; i < mainBeanList.size(); i++) {
//        mainBeanList.getCity();
//        mainBeanList.getTemp();
//        mainBeanList.getTime();
//        mainBeanList.getWeather();
//        mainBeanList.getSpeed();
//            }
//        MainBean beanList = new ArrayList<>();
        MainBean mainBean = new MainBean();
        mainBean.setCity("melbourne");
        mainBean.setSpeed("2");
        mainBean.setWeather("cloudy");
        mainBean.setTime("Wednesday 12:00PM");
        mainBean.setTemp("");
        assertEquals((mainBean.getCity()),("melbourne"));
        assertEquals((mainBean.getSpeed()),("2"));
        assertNotEquals((mainBean.getTemp()),("20"));
        assertEquals((mainBean.getWeather()),("cloudy"));
        assertEquals((mainBean.getTime()),("Wednesday 12:00PM"));
    }
//
//    @Test
//    public void weatherBeanTest() throws JSONException {
//        JsonObject obj = new JsonObject();
//        JsonElement obj1 = new JsonObject();
//        JsonElement obj2 = new JsonObject();
//        JsonObject obj3 = new JsonObject();
//        ((JsonObject) obj1).addProperty("temp",15.48);
//        ((JsonObject) obj1).addProperty("pressure",new Integer(1016));
//        ((JsonObject) obj1).addProperty("humidity",new Integer(67));
//        ((JsonObject) obj1).addProperty("temp_min",new Float(12.78));
//        ((JsonObject) obj1).addProperty("temp_max",new Float(17.22));
//        obj.add("main",obj1);
//        ((JsonObject) obj2).addProperty("speed",4.6);
//        ((JsonObject) obj2).addProperty("deg",360);
//        obj3.add("wind",obj2);
//        WeatherBean weatherBean = new WeatherBean();
//        weatherBean.setMain(obj);
//        weatherBean.setName("melbourne");
//        weatherBean.setWind(obj3);
//        MainActivity mainActivity = new MainActivity();
//        mainActivity.volley("Melbourne");
//        WeatherBean weatherBean1=mainActivity.weatherBean;
//        assertThat(weatherBean1, (Matcher<? super WeatherBean>) weatherBean);
////        assertEquals((mainBean.getSpeed()),("2"));
////        assertEquals((mainBean.getTemp()),("20"));
////        assertEquals((mainBean.getWeather()),("cloudy"));
////        assertEquals((mainBean.getTime()),("Wednesday 12:00PM"));
//    }
}
