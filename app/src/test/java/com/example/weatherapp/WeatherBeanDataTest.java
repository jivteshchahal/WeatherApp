package com.example.weatherapp;

import com.example.weatherapp.bean.MainBean;
import com.example.weatherapp.bean.WeatherBean;
import com.google.gson.JsonObject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class WeatherBeanDataTest {
    WeatherBean weatherBean;
    @Test
    public void beanWind() {
        weatherBean = new WeatherBean();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject.addProperty("wind",5);
        jsonObject1.addProperty("wind",5);
        weatherBean.setWind(jsonObject1);
        assertEquals(weatherBean.getWind().get("wind").toString(), jsonObject.get("wind").toString());
    }

    @Test
    public void beanWind1() {
        weatherBean = new WeatherBean();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject.addProperty("wind",6);
        jsonObject1.addProperty("wind",5);
        weatherBean.setWind(jsonObject1);
        assertNotEquals(weatherBean.getWind().get("wind").toString(), jsonObject.get("wind").toString());
    }
    @Test
    public void beanWind2() {
        weatherBean = new WeatherBean();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject.addProperty("wind",6);
        jsonObject1.addProperty("wind",5);
        weatherBean.setWind(jsonObject1);
        assertNotEquals(weatherBean.getWind().get("wind").toString(), jsonObject.get("wind").toString());
    }
    @Test
    public void beanName() {
        weatherBean = new WeatherBean();
        weatherBean.setName("melbourne");
        assertEquals((weatherBean.getName()),("melbourne"));
    }
    @Test
    public void beanName1() {
        weatherBean = new WeatherBean();
        weatherBean.setName("melbourne");
        assertNotEquals((weatherBean.getName()),("sydney"));
    }
    @Test
    public void beanMain() {
        weatherBean = new WeatherBean();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject.addProperty("temp",5.9);
        jsonObject1.addProperty("temp",5.9);
        weatherBean.setMain(jsonObject1);
        assertEquals(weatherBean.getMain().get("temp").toString().toString(),jsonObject.get("temp").toString());
    }
    @Test
    public void beanMain1() {
        weatherBean = new WeatherBean();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject.addProperty("temp",20.5);
        jsonObject1.addProperty("temp",20.9);
        weatherBean.setMain(jsonObject1);
        assertNotEquals(weatherBean.getMain().get("temp").toString(),jsonObject.get("temp").toString());
    }
    @Test
    public void beanMain2() {
        weatherBean = new WeatherBean();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonObject();
        jsonObject.addProperty("temp",5);
        jsonObject1.addProperty("temp",8);
        weatherBean.setMain(jsonObject1);
        assertNotEquals(weatherBean.getMain().get("temp").toString(),jsonObject.get("temp").toString());
    }
}
