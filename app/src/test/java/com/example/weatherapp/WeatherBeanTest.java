package com.example.weatherapp;

import com.example.weatherapp.bean.WeatherBean;
import com.example.weatherapp.bean.WeatherDetailBean;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WeatherBeanTest {
    WeatherDetailBean weatherBean = new WeatherDetailBean();
    @Test
    public void beanDescription() {
        weatherBean.setDescription("clear sky");
        assertEquals((weatherBean.getDescription()),("clear sky"));
    }
    @Test
    public void beanDescription1() {
        weatherBean.setDescription("cloudy");
        assertNotEquals((weatherBean.getDescription()),("clear sky"));
    }
}
