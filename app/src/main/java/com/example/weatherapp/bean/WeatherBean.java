package com.example.weatherapp.bean;

import com.google.gson.JsonObject;

import java.util.List;

public class WeatherBean {
    List<WeatherDetailBean> weather;
    JsonObject main;
    JsonObject wind;
    String name;


    public List<WeatherDetailBean> getWeather() {

        return weather;
    }

    public void setWeather(List<WeatherDetailBean> weather) {

        this.weather = weather;
    }

    public JsonObject getMain() {

        return main;
    }

    public void setMain(JsonObject main)
    {
        this.main = main;
    }

    public JsonObject getWind() {
        return wind;
    }

    public void setWind(JsonObject wind) {
        this.wind = wind;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
