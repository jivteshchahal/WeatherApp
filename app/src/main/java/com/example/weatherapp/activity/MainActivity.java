package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.bean.MainBean;
import com.example.weatherapp.bean.WeatherBean;
import com.example.weatherapp.database.SqlLiteServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //Global reference variables
    Spinner spinner;
    Button refreshButton;
    TextView txtCity, txtUpdatedTime, txtWeather, txtTemp, txtWind;
    Gson gson;
    String city="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Fetch elements from XML Layout */
        spinner = findViewById(R.id.spinnerCitySelect);
        refreshButton = findViewById(R.id.btnRefresh);
        txtCity = findViewById(R.id.txtcity);
        txtUpdatedTime = findViewById(R.id.txtDayAndTime);
        txtWeather = findViewById(R.id.txtWeather);
        txtTemp = findViewById(R.id.txtTemp);
        txtWind = findViewById(R.id.txtWindSpeed);

        /* Spinner Setup passing a list which can be increased when required */
        List<String> cityList =  new ArrayList<>();
        cityList.add("Select City");
        cityList.add("Sydney");
        cityList.add("Melbourne");
        cityList.add("Wollongong");
        /* Array adapter to set up spinner */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set Adapter to spinner
        spinner.setAdapter(adapter);
        /* Getting selected items from the spinner(Dropdown) and passing them to get weather data */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* selected spinner item */
                city =parent.getSelectedItem().toString();
                /*Checking if internet is connected. If yes then live data is shown. If not then data which was last received from database is displayed*/
                if(isInternetOn()){
                    //getting data from Server
                    volley(city);
                }else {
                    /* getting data from database */
                    List<MainBean> mainBeanList;
                    try{
                        mainBeanList = new SqlLiteServices(MainActivity.this).getAllWeatherData();
                    for (int i = 0; i < mainBeanList.size(); i++) {
                        if (mainBeanList.get(i).getCity().equalsIgnoreCase(city)) {
                            Log.e("Namemnm", mainBeanList.size() + "");
                            txtCity.setText(city);
                            txtTemp.setText(mainBeanList.get(i).getTemp()+"° C");
                            txtUpdatedTime.setText(mainBeanList.get(i).getTime());
                            txtWeather.setText(mainBeanList.get(i).getWeather());
                            txtWind.setText(mainBeanList.get(i).getSpeed()+"km/h");
                        }
                    }
                }catch (Exception e){
                        Log.e("Exception in database", e.getLocalizedMessage());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetOn()){
                    volley(city);
                }
                else {
                    Log.e("Snack bar","else");
                    // Snack bar, the message is displayed.
                    Snackbar snackbar = Snackbar.make(v, "No internet Connection to get live data", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }
    /* Using volley library to get data from server */
    void volley(final String place) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+place+"&APPID=172c74216a3ffe8dfbbdec6565a9dac7&units=metric";
        //progress bar 
        final ProgressDialog loading = ProgressDialog.show(this, "", "Loading", false, false);
        //String request
        //response
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                loading.cancel();
                /* Gson library to get data from Json API */
                gson = new Gson();
                Type listType = new TypeToken<WeatherBean>() {
                }.getType();
                /* date in desired format */
                DateFormat df = new SimpleDateFormat("EEEE h:mm a");
                String date = df.format(Calendar.getInstance().getTime());
                /* retrieving data from Json */
                WeatherBean weatherBean = gson.fromJson(response, listType);
                MainBean mainBean = new MainBean();
                if (weatherBean.getWeather().size() != 0) {
                    for (int k = 0; k < weatherBean.getWeather().size(); k++) {
                        /* Getting data out of the array and object */
                        mainBean.setCity(city);
                        mainBean.setTime(date);
                        mainBean.setWeather(weatherBean.getWeather().get(k).getDescription());
                        mainBean.setTemp(weatherBean.getMain().get("temp").toString());
                        mainBean.setSpeed(weatherBean.getWind().get("speed").toString()+"km/h");
                        /* live data display */
                        txtCity.setText(city);
                        txtTemp.setText(weatherBean.getMain().get("temp").toString());
                        txtUpdatedTime.setText(date);
                        txtWeather.setText(weatherBean.getWeather().get(k).getDescription());
                        txtWind.setText(weatherBean.getWind().get("speed").toString()+"° C");
                    }
                    /* Storing data for offline display in SqlLite Database */
                    new SqlLiteServices(MainActivity.this).insertOrUpdate(mainBean);
                }
                else{
                    Log.e("Error in response", "Error in format");
                }
            }
        }, /* error */
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.cancel();
                Log.e("error in volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /* Checking the Internet Connection */
    private boolean isInternetOn() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}