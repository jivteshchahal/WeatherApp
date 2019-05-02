package com.example.weatherapp;

import android.content.Context;
import android.util.Log;

import com.example.weatherapp.activity.MainActivity;
import com.example.weatherapp.bean.MainBean;
import com.example.weatherapp.database.SqlLiteServices;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SqlDataTest {
    Context context;
    @Test
    public void sqlDataTesting(){

       List<MainBean> mainBeanList = new SqlLiteServices(context.getApplicationContext()).getAllWeatherData();
        for (int i = 0; i < mainBeanList.size(); i++) {
            if (mainBeanList.get(i).getCity().equalsIgnoreCase("melbourne")) {
                mainBeanList.get(i).getCity();
                mainBeanList.get(i).getTemp();
                mainBeanList.get(i).getTime();
                mainBeanList.get(i).getWeather();
                mainBeanList.get(i).getSpeed();
            }
        }
        List<MainBean> beanList = new ArrayList<>();
        MainBean mainBean =new MainBean();
        mainBean.setSpeed("2");
        mainBean.setWeather("cloudy");
        mainBean.setTime("Wednesday 12:00PM");
        mainBean.setTemp("20");
        beanList.add(mainBean);
        assertThat(mainBeanList, hasItems(mainBean));

    }
}
