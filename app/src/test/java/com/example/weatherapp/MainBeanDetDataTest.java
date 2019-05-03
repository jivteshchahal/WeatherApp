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

public class MainBeanDetDataTest {
    MainBean mainBean = new MainBean();
    @Test
    public void beanCity() {
        
        mainBean.setCity("melbourne");
        assertEquals((mainBean.getCity()),("melbourne"));

    }
    @Test
    public void beanCity1() {
        
        mainBean.setCity("mel");
        assertNotEquals((mainBean.getCity()),("melbourne"));
    }
    @Test
    public void beanSpeed() {
        
        mainBean.setSpeed("2");
        assertEquals((mainBean.getSpeed()),("2"));
    }
    @Test
    public void beanSpeed1() {
        
        mainBean.setSpeed("89");
        assertNotEquals((mainBean.getSpeed()),("2"));
    }
    @Test
    public void beanWeather() {
        
        mainBean.setWeather("cloudy");
        assertEquals((mainBean.getWeather()),("cloudy"));
    }
    @Test
    public void beanWeather1() {
        
        mainBean.setWeather("sunny");
        assertNotEquals((mainBean.getWeather()),("cloudy"));
    }
    @Test
    public void beanTime() {
        
        mainBean.setTime("Wednesday 12:00PM");
        assertEquals((mainBean.getTime()),("Wednesday 12:00PM"));
    }
    @Test
    public void beanTime1() {
        
        mainBean.setTime("Wednesday 01:00PM");
        assertNotEquals((mainBean.getTime()),("Wednesday 12:00PM"));
    }
    @Test
    public void beanTemp() {
        
        mainBean.setTemp("20");
        assertEquals((mainBean.getTemp()),("20"));
    }
    @Test
    public void beanTemp1() {
        
        mainBean.setTemp("50");
        assertNotEquals((mainBean.getTemp()),("20"));
    }
}
