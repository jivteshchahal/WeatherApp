package com.example.weatherapp.bean;

import java.util.List;

public class WeatherBean {
    List<WeatherDetailBean> weather;
    List<MainBean> mainBeanList;
    List<WindBean> windBeanList;
    String name;


    public List<WeatherDetailBean> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDetailBean> weather) {
        this.weather = weather;
    }

    public List<MainBean> getMainBeanList() {
        return mainBeanList;
    }

    public void setMainBeanList(List<MainBean> mainBeanList) {
        this.mainBeanList = mainBeanList;
    }

    public List<WindBean> getWindBeanList() {
        return windBeanList;
    }

    public void setWindBeanList(List<WindBean> windBeanList) {
        this.windBeanList = windBeanList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
